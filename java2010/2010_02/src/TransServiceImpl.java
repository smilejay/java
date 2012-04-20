package com.alibaba.intl.bops.crm.translation.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

import com.alibaba.common.logging.Logger;
import com.alibaba.common.logging.LoggerFactory;
import com.alibaba.intl.biz.vas.translation.bo.RecordFieldInfoGroup;
import com.alibaba.intl.biz.vas.translation.commons.RecordFieldInfos;
import com.alibaba.intl.biz.vas.translation.constants.RecordFieldGroupName;
import com.alibaba.intl.biz.vas.translation.constants.TransProductField;
import com.alibaba.intl.biz.vas.translation.constants.TransTaskStatusConstant;
import com.alibaba.intl.biz.vas.translation.dataobject.TransProductContent;
import com.alibaba.intl.biz.vas.translation.dataobject.VasTransRecord;
import com.alibaba.intl.biz.vas.translation.dataobject.VasTransTask;
import com.alibaba.intl.biz.vas.translation.service.TransServicesLocator;
import com.alibaba.intl.biz.vas.translation.service.interfaces.VasTransService;
import com.alibaba.intl.bops.crm.translation.constants.ConstantsKey;
import com.alibaba.intl.bops.crm.translation.handle.TranslationHandle;
import com.alibaba.intl.bops.crm.translation.remote.TransRemoteService;
import com.alibaba.intl.bops.crm.translation.service.TransService;
import com.alibaba.intl.commons.framework.type.ChineseString;

public class TransServiceImpl implements TransService {

    public static final Random  transRandom        = new Random();
    public static final int     TASK_COUNT         = 5;
    public static final int     TASK_ID_COUNT      = 5;
    public static final int     BRIE_DESC_LENGTH   = 128;
    public static final int     DETAIL_DESC_LENGTH = 8000;

    VasTransService             vasTransService    = TransServicesLocator.getVasTransService();
    private static final Logger log                = LoggerFactory.getLogger(TransRemoteService.class);

    /**
     * �����Ƿ�æ
     * 
     *@param Map <br>
     **********|-- method=�� isBusy��<br>
     **********|-- providerId //��ʶ��ǰ����ͻ���id <br>
     **********|-- param= (y or n) //��ʶ�Լ���æ��״̬y =æn=��<br>
     *@return Map <br>
     ***********|--error=Map <br>
     **********************|--taskId =errorMessage //���� ����idΪkey errorMessageΪֵΪ�մ�����óɹ�
     */
    public Map isBusy(Map paramMap) {
        Map value = new HashMap();
        Map error = new HashMap();
        value.put(ConstantsKey.ERROR, error);
        if (!TranslationHandle.checkIsBusyParam(paramMap, value)) {
            return value;
        }
        vasTransService.isBusyTransProvider((Integer) paramMap.get(ConstantsKey.PROVIDER_ID),
                                            (String) paramMap.get(ConstantsKey.PARAM));
        return value;
    }

    /**
     *��ѯ����������Ϣ.�����û���ѯalibaba���ݿ�����ĵ�ǰ���빫˾���������.Ŀǰֻ�ṩ"����ȡ(������)"����Ĳ�ѯ.
     * 
     * @param Map<br>
     ************|--method=��queryTransTask�� //(����)<br>
     ************|--providerId //��ʶ��ǰ����ͻ���id(����)<br>
     ************|--taskType=�� product�� //(����)<br>
     ************|--transType //��ʶ��ȡ����������Ŀǰֻ��CTE<br>
     ************|--taskIdList=List <br>
     *****************************|-- taskId //����.����id��list���ϵ�����С��1000;�����ȫ������ȡ���� ��� taskId ��Ӧ������״̬�Ѿ�������ȡ״̬��Ϊ����״̬(���ѷ���)�򷵻ؿ�
     *@return Map <br>
     ************|--error=Map<br>
     ***********************|--errorId=errorMessage //����<br>
     ************|--value=List <br>
     ************************|-- taskId //��������id
     */
    public Map queryTransTask(Map paramMap) {
        Map value = new HashMap();
        Map error = new HashMap();
        value.put(ConstantsKey.ERROR, error);
        List ls = new ArrayList();
        value.put(ConstantsKey.VALUE, ls);
        // У�����
        if (!TranslationHandle.checkQueryTransTaskParam(paramMap, value)) {
            return value;
        }
        List<Integer> taskIdList = (List<Integer>) paramMap.get(ConstantsKey.TASK_ID_LIST);
        if (null != taskIdList && taskIdList.size() <= TASK_ID_COUNT && 0 < taskIdList.size()) {
            ls.addAll(vasTransService.selectProcessingIdTaskByIds(taskIdList));
        } else {
            ls.addAll(vasTransService.selectAllProcessingIdByProviderId((Integer) paramMap.get(ConstantsKey.PROVIDER_ID)));
        }
        return value;
    }

    /**
     * ��ȡ��������ӿ� <br>
     * 1.У����� <br>
     * 2.ȡ��receiveTypeֵ.�����newִ�е�3,4��.oldִ��5��<br>
     * 3.�ж�taskCountֵ�����Ƿ�С�ڵ���100,����100.��Ĭ��100<br>
     * 4.����providerId,taskCount,transType��ȡ����,������.<br>
     * 5.�����old��ȡ��taskList�б�.<br>
     * 6.����taskList��id��ѯ����taskList���֮ȡ100������.
     * 
     * @param Map <br>
     * **********|--method=��receiveTransTask�� //(����) <br>
     * **********|--taskType=��product�� //(����) <br>
     * **********|--providerId //��ʶ��ǰ����ͻ���id(����) <br>
     * **********|--receiveType=(new or old) //(����)���������ͱ�ʶ�Ǹ���taskcount�� ��ȡ���Ǹ�������id��list������������ȡ(����id)һ��������ȡ��������,������ȡ.<br>
     * **********|--transType //��ʶ��ȡ����������Ŀǰֻ��CTE <br>
     * **********|--taskCount //������100 . ����Ĭ��100 ��receiveType = new��Ч<br>
     * **********|--taskList=List // ��receiveType = oldʱ���������Ч<br>
     * **************************|-- taskId //����.����id��list���ϵ�����С��100;
     * @return Map <br>
     * ********** |--error=Map<br>
     * ********************* |--errorId=errorMessage //����<br>
     * ********** |--value=List //�������100<br>
     * ********************** |--Map<br>
     * *************************** |-- taskId //��ʶһ�������¼ �ڽ�����������ʱ��Ҫ���ء�<br>
     * *************************** |-- briefDesc //��Ҫ������ϢΪ100������<br>
     * *************************** |-- detailDesc //��ϸ������Ϣ800������<br>
     * *************************** |-- createDate //�û��ύ�������ʱ��(����Ӽ��������Ժ�ҵ����ȷ�����ڲ�����Ӧ���ֶ�.)<br>
     * *************************** |-- categoryName //��ҵ����<br>
     * *************************** |-- customerNo //�ͻ���ʶ<br>
     * *************************** |-- freeNumber //�������<br>
     * *************************** |-- paidNumber //�շ�����<br>
     */

    public Map receiveTransTask(Map paramMap) {

        // �������ض���
        Map<String, Object> returnMap = new HashMap<String, Object>();
        List<Map> value = new ArrayList<Map>();
        Map<String, Object> error = new HashMap<String, Object>();
        returnMap.put(ConstantsKey.ERROR, error);
        returnMap.put(ConstantsKey.VALUE, value);
        // У�����
        if (!TranslationHandle.checkReceiveTransTaskParam(paramMap, returnMap)) {
            return returnMap;
        }
        if (RecordFieldGroupName.PRODUCT.getValue().equals((String) paramMap.get(ConstantsKey.TASK_TYPE))) {
            if (TransTaskStatusConstant.TASK_NEW.getValue().equals((String) paramMap.get(ConstantsKey.RECEIVE_TYPE))) {
                Integer taskCount = (Integer) paramMap.get(ConstantsKey.TASK_COUNT);
                if (null == taskCount || taskCount >= TASK_COUNT) {
                    taskCount = TASK_COUNT;
                }
                Integer token = transRandom.nextInt();
                // ��ȡ����
                vasTransService.receiveTransTask((Integer) paramMap.get(ConstantsKey.PROVIDER_ID),
                                                 (String) paramMap.get(ConstantsKey.TRANS_TYPE), taskCount, token);
                // ��ѯ����ȡ��������
                List<VasTransTask> transList = vasTransService.selectTransTaskByProviderIdAndToken(
                                                                                                   (Integer) paramMap.get(ConstantsKey.PROVIDER_ID),
                                                                                                   token);
                // ��װ���ط��빫˾����������
                for (VasTransTask trans : transList) {
                    Map<String, VasTransRecord> contentMap = vasTransService.getTransContent(trans);
                    value.add(TranslationHandle.buildQueryTransTaskValue(trans, contentMap));
                }

            } else {
                // ������빫˾��������id����ѯ����Ļ�.��ȡ������id�ļ���
                List ids = (List) paramMap.get(ConstantsKey.TASK_ID_LIST);
                if (null != ids) {
                    // ��������id���ϲ��Ҷ�Ӧ������
                    List<VasTransTask> transList = vasTransService.selectByIdsAndProviderId(
                                                                                            ids,
                                                                                            (Integer) paramMap.get(ConstantsKey.PROVIDER_ID));
                    // ��װ���ط��빫˾����������
                    for (VasTransTask trans : transList) {
                        Map<String, VasTransRecord> contentMap = vasTransService.getTransContent(trans);
                        value.add(TranslationHandle.buildQueryTransTaskValue(trans, contentMap));
                    }

                }

            }
        }
        return returnMap;
    }

    /**
     * ����������.Ŀǰֻ��product���͵ķ�������
     * 
     * @param Map <br>
     * **********|-- method=�� transactTransTask�� //(����)<br>
     * **********|-- providerId //(����)��ʶ��ǰ����ͻ���id <br>
     * **********|-- param=List //�������100 <br>
     * ************************|--Map <br>
     * ****************************|-- taskId //��ʶһ�������¼ <br>
     * *****************************|-- briefDesc //��Ҫ������Ϣ����256�ַ� <br>
     * *****************************|-- detailDesc //��ϸ������Ϣ����8000�ַ�<br>
     * *****************************|-- isReject=(y or n) //�˸������Ҫ����ֵ����Ϊy����Ĭ��Ϊn <br>
     * *****************************|-- comments //��ע(�˻�ԭ��)
     * @return Map<br>
     * **********|--error=Map <br>
     * **********************|-- taskId = errorMessage //���� Ϊ�մ�����óɹ�
     */
    public Map transactTransTask(Map paramMap) {
        Map value = new HashMap();
        Map error = new HashMap();
        value.put(ConstantsKey.ERROR, error);
        // У�����
        if (!TranslationHandle.checkTransactTransTaskParam(paramMap, value)) {
            return value;
        }
        List<Map> paramList = (List<Map>) paramMap.get(ConstantsKey.PARAM);
        if (null == paramList) {
            return value;
        }
        if (TASK_COUNT < paramList.size()) {
            error.put("1401", "Task Count >" + TASK_COUNT);
            return value;
        }

        for (Map taskMap : paramList) {
            if (null != taskMap.get(ConstantsKey.TASK_ID)) {
                VasTransTask vasTransTask = vasTransService.selectTransTaskById((Integer) taskMap.get(ConstantsKey.TASK_ID));
                // �ж������Ƿ����
                if (null == vasTransTask
                    || !vasTransTask.getServiceProviderId().equals((Integer) paramMap.get(ConstantsKey.PROVIDER_ID))) {
                    error.put(taskMap.get(ConstantsKey.TASK_ID), "Task '" + taskMap.get(ConstantsKey.TASK_ID)
                                                                 + "' Not Found!!");
                    continue;
                }
                if (!TransTaskStatusConstant.TRANS_PROCESSING.getValue().equals(vasTransTask.getStatus())) {
                    error.put(taskMap.get(ConstantsKey.TASK_ID), "Task '" + taskMap.get(ConstantsKey.TASK_ID)
                                                                 + "' Processed!!");
                    continue;
                }
                // �ж������Ƿ��˻��类�˻ؽ����˻ش���
                if (null != taskMap.get(ConstantsKey.IS_REJECT)
                    && "y".equals((String) taskMap.get(ConstantsKey.IS_REJECT))) {
                    VasTransTask vtt = new VasTransTask();
                    vtt.setId(vasTransTask.getId());
                    vtt.setStatus(TransTaskStatusConstant.TASK_REJECT.getValue());
                    if (StringUtils.isBlank((String) taskMap.get(ConstantsKey.COMMENTS))) {
                        error.put(taskMap.get(ConstantsKey.TASK_ID), ConstantsKey.COMMENTS + " is NUll!!");
                        continue;
                    }
                    vtt.setNote(new ChineseString((String) taskMap.get(ConstantsKey.COMMENTS)));
                    vtt.setProcessedTime(new Date());
                    vasTransService.updateTransTaskSelective(vtt);
                } else { // ���û�б��˻ؽ���������
                    // ����Ϊ��Ʒ�ķ�����������ò�Ʒ���͵�������������
                    if ("product".equals(vasTransTask.getTaskSrc())) {
                        transactProductTransTask(taskMap, vasTransTask, error);
                    }
                }

            }
        }
        return value;
    }

    /**
     * @param taskMap **************|--Map <br>
     * ********************|-- taskId //��ʶһ�������¼ <br>
     * ********************|-- briefDesc //��Ҫ������Ϣ����256�ַ� <br>
     * ********************|-- detailDesc //��ϸ������Ϣ����8000�ַ�<br>
     * ********************|-- isReject=(y or n) //�˸������Ҫ����ֵ����Ϊy����Ĭ��Ϊn <br>
     * ********************|-- comments //��ע(�˻�ԭ��)
     * @param vasTransTask
     * @param error
     */
    public void transactProductTransTask(Map taskMap, VasTransTask vasTransTask, Map error) {

        String briefDesc = (String) taskMap.get(ConstantsKey.BRIE_DESC);
        if (StringUtils.isBlank(briefDesc)) {
            error.put(vasTransTask.getId(), "briefDesc is NUll");
            return;
        }

        if (briefDesc.length() > BRIE_DESC_LENGTH) {
            error.put(vasTransTask.getId(), "briefDesc.length > " + BRIE_DESC_LENGTH + " !!");
            return;
        }
        String detailDesc = (String) taskMap.get(ConstantsKey.DETAIL_DESC);

        if (StringUtils.isBlank(detailDesc)) {
            error.put(vasTransTask.getId(), "detailDesc is NUll");
            return;
        }
        if (detailDesc.length() > DETAIL_DESC_LENGTH) {
            error.put(vasTransTask.getId(), "detailDesc.length > " + DETAIL_DESC_LENGTH + " !!");
            return;
        }

        TransProductContent transProduct = new TransProductContent();
        transProduct.setTaskId(vasTransTask.getId());
        transProduct.setTransBrief(briefDesc);
        transProduct.setTransDetail(detailDesc);
        // �õ����������Ӧ�������ֶ�
        Map<String, VasTransRecord> contentMap = vasTransService.getTransContent(vasTransTask);
        // �������������ֶ�
        for (TransProductField t : TransProductField.values()) {
            VasTransRecord v = contentMap.get(t.getValue());
            if (null != v) {
                VasTransRecord vtr = new VasTransRecord();
                if (TransProductField.BRIEF_DESC.equals(t)) {
                    vtr.setId(v.getId());
                    vtr.setTranslatedResult(transProduct.getCSTransBrief());
                } else if (TransProductField.DETAIL_DESC1.equals(t)) {
                    vtr.setId(v.getId());
                    vtr.setTranslatedResult(transProduct.getCSTransDetail1());
                } else if (TransProductField.DETAIL_DESC2.equals(t)) {
                    vtr.setId(v.getId());
                    vtr.setTranslatedResult(transProduct.getCSTransDetail2());
                }
                if (null != vtr.getId()) {
                    vasTransService.updateTransRecordSelective(vtr);
                }
            } else if (null == v && TransProductField.DETAIL_DESC2.equals(t)
                       && StringUtils.isNotBlank(transProduct.getTransDetail2())) {// ����2�п���û�м�¼����ԭ��ûֵ ��������ֵ��������²���һ��
                RecordFieldInfos recordFieldInfos = RecordFieldInfos.getInstance();// �õ����е��ֶ���Ϣ��
                // �õ� PRODUCT���ֶ���Ϣ
                RecordFieldInfoGroup group = recordFieldInfos.getRecordFieldInfoGroup(RecordFieldGroupName.PRODUCT.getValue());
                VasTransRecord vtr = new VasTransRecord();
                vtr.setTransTaskId(transProduct.getTaskId());
                vtr.setTransRecordFieldId(group.getFieldInfo(TransProductField.DETAIL_DESC2.getValue()).getId());// ����DETAIL_DESC2��id
                vtr.setTranslatedResult(transProduct.getCSTransDetail2());
                vasTransService.insertVasTransRecord(vtr);
            }
        }

        VasTransTask vtt = new VasTransTask();
        vtt.setId(vasTransTask.getId());
        vtt.setStatus(TransTaskStatusConstant.TRANS_PROCESSED.getValue());
        vtt.setProcessedTime(new Date());
        vasTransService.updateTransTaskSelective(vtt);

    }
}
