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
     * 设置是否忙
     * 
     *@param Map <br>
     **********|-- method=” isBusy”<br>
     **********|-- providerId //标识当前翻译客户的id <br>
     **********|-- param= (y or n) //标识自己的忙闲状态y =忙n=闲<br>
     *@return Map <br>
     ***********|--error=Map <br>
     **********************|--taskId =errorMessage //多条 任务id为key errorMessage为值为空代表调用成功
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
     *查询翻译任务信息.用于用户查询alibaba数据库里面的当前翻译公司的任务情况.目前只提供"已领取(处理中)"任务的查询.
     * 
     * @param Map<br>
     ************|--method=”queryTransTask” //(必填)<br>
     ************|--providerId //标识当前翻译客户的id(必填)<br>
     ************|--taskType=” product” //(必填)<br>
     ************|--transType //标识领取任务翻译类型目前只有CTE<br>
     ************|--taskIdList=List <br>
     *****************************|-- taskId //多条.任务id的list集合的数量小于1000;不填返回全部已领取任务 如果 taskId 对应的任务状态已经从已领取状态变为其它状态(如已翻译)则返回空
     *@return Map <br>
     ************|--error=Map<br>
     ***********************|--errorId=errorMessage //多条<br>
     ************|--value=List <br>
     ************************|-- taskId //翻译任务id
     */
    public Map queryTransTask(Map paramMap) {
        Map value = new HashMap();
        Map error = new HashMap();
        value.put(ConstantsKey.ERROR, error);
        List ls = new ArrayList();
        value.put(ConstantsKey.VALUE, ls);
        // 校验参数
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
     * 领取翻译任务接口 <br>
     * 1.校验参数 <br>
     * 2.取到receiveType值.如果是new执行第3,4步.old执行5步<br>
     * 3.判断taskCount值大于是否小于等于100,大于100.则默认100<br>
     * 4.根据providerId,taskCount,transType领取任务,并返回.<br>
     * 5.如果是old则取得taskList列表.<br>
     * 6.根据taskList的id查询任务taskList最多之取100条返回.
     * 
     * @param Map <br>
     * **********|--method=”receiveTransTask” //(必填) <br>
     * **********|--taskType=”product” //(必填) <br>
     * **********|--providerId //标识当前翻译客户的id(必填) <br>
     * **********|--receiveType=(new or old) //(必填)参数的类型标识是根据taskcount新 领取还是根据任务id的list集合来从新领取(任务id)一般是以领取过的任务,从新领取.<br>
     * **********|--transType //标识领取任务翻译类型目前只有CTE <br>
     * **********|--taskCount //不超过100 . 不填默认100 当receiveType = new生效<br>
     * **********|--taskList=List // 当receiveType = old时这个参数生效<br>
     * **************************|-- taskId //多条.任务id的list集合的数量小于100;
     * @return Map <br>
     * ********** |--error=Map<br>
     * ********************* |--errorId=errorMessage //多条<br>
     * ********** |--value=List //多条最大100<br>
     * ********************** |--Map<br>
     * *************************** |-- taskId //标识一条翻译记录 在将翻译结果传回时需要带回。<br>
     * *************************** |-- briefDesc //简要描述信息为100个汉字<br>
     * *************************** |-- detailDesc //详细描述信息800个汉字<br>
     * *************************** |-- createDate //用户提交的任务的时间(具体加急等需求以后业务上确定后在补充相应的字段.)<br>
     * *************************** |-- categoryName //行业名称<br>
     * *************************** |-- customerNo //客户标识<br>
     * *************************** |-- freeNumber //免费字数<br>
     * *************************** |-- paidNumber //收费字数<br>
     */

    public Map receiveTransTask(Map paramMap) {

        // 构建返回对象
        Map<String, Object> returnMap = new HashMap<String, Object>();
        List<Map> value = new ArrayList<Map>();
        Map<String, Object> error = new HashMap<String, Object>();
        returnMap.put(ConstantsKey.ERROR, error);
        returnMap.put(ConstantsKey.VALUE, value);
        // 校验参数
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
                // 领取任务
                vasTransService.receiveTransTask((Integer) paramMap.get(ConstantsKey.PROVIDER_ID),
                                                 (String) paramMap.get(ConstantsKey.TRANS_TYPE), taskCount, token);
                // 查询刚领取到的任务
                List<VasTransTask> transList = vasTransService.selectTransTaskByProviderIdAndToken(
                                                                                                   (Integer) paramMap.get(ConstantsKey.PROVIDER_ID),
                                                                                                   token);
                // 组装返回翻译公司的任务数据
                for (VasTransTask trans : transList) {
                    Map<String, VasTransRecord> contentMap = vasTransService.getTransContent(trans);
                    value.add(TranslationHandle.buildQueryTransTaskValue(trans, contentMap));
                }

            } else {
                // 如果翻译公司根据任务id来查询任务的话.则取到任务id的集合
                List ids = (List) paramMap.get(ConstantsKey.TASK_ID_LIST);
                if (null != ids) {
                    // 根据任务id集合查找对应的任务
                    List<VasTransTask> transList = vasTransService.selectByIdsAndProviderId(
                                                                                            ids,
                                                                                            (Integer) paramMap.get(ConstantsKey.PROVIDER_ID));
                    // 组装返回翻译公司的任务数据
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
     * 处理翻译任务.目前只有product类型的翻译任务
     * 
     * @param Map <br>
     * **********|-- method=” transactTransTask” //(必填)<br>
     * **********|-- providerId //(必填)标识当前翻译客户的id <br>
     * **********|-- param=List //多条最大100 <br>
     * ************************|--Map <br>
     * ****************************|-- taskId //标识一条翻译记录 <br>
     * *****************************|-- briefDesc //简要描述信息译文256字符 <br>
     * *****************************|-- detailDesc //详细描述信息译文8000字符<br>
     * *****************************|-- isReject=(y or n) //退稿如果需要回则值设置为y不填默认为n <br>
     * *****************************|-- comments //备注(退回原因)
     * @return Map<br>
     * **********|--error=Map <br>
     * **********************|-- taskId = errorMessage //多条 为空代表调用成功
     */
    public Map transactTransTask(Map paramMap) {
        Map value = new HashMap();
        Map error = new HashMap();
        value.put(ConstantsKey.ERROR, error);
        // 校验参数
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
                // 判断任务是否存在
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
                // 判断任务是否被退回如被退回进行退回处理
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
                } else { // 如果没有被退回进行任务处理
                    // 任务为产品的翻译任务则调用产品类型的任务处理方法处理
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
     * ********************|-- taskId //标识一条翻译记录 <br>
     * ********************|-- briefDesc //简要描述信息译文256字符 <br>
     * ********************|-- detailDesc //详细描述信息译文8000字符<br>
     * ********************|-- isReject=(y or n) //退稿如果需要回则值设置为y不填默认为n <br>
     * ********************|-- comments //备注(退回原因)
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
        // 得到这条任务对应的任务字段
        Map<String, VasTransRecord> contentMap = vasTransService.getTransContent(vasTransTask);
        // 更新所有任务字段
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
                       && StringUtils.isNotBlank(transProduct.getTransDetail2())) {// 详情2有可能没有记录。当原文没值 但译文有值的情况在下插入一条
                RecordFieldInfos recordFieldInfos = RecordFieldInfos.getInstance();// 得到所有的字段信息。
                // 得到 PRODUCT的字段信息
                RecordFieldInfoGroup group = recordFieldInfos.getRecordFieldInfoGroup(RecordFieldGroupName.PRODUCT.getValue());
                VasTransRecord vtr = new VasTransRecord();
                vtr.setTransTaskId(transProduct.getTaskId());
                vtr.setTransRecordFieldId(group.getFieldInfo(TransProductField.DETAIL_DESC2.getValue()).getId());// 设置DETAIL_DESC2的id
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
