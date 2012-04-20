import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;


public class Test1 {

    /**
     *�����Ƿ�æ
     * 
     *@param Map <br>
     **********|-- method=�� isBusy��<br>
     **********|-- providerId //��ʶ��ǰ����ͻ���id <br>
     **********|-- param= (y or n) //��ʶ�Լ���æ��״̬y =æn=��<br>
     * @throws IOException
     * @throws HttpException
     */

    public static void testisBusy() {
        Map paramMap = new HashMap();
        paramMap.put("method", "isBusy");
        paramMap.put("providerId", 100000001);
        paramMap.put("param", "n");
   System.out.println(JSONObject.fromObject(paramMap).toString());

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
    public  static void testqueryTransTask() {
        Map paramMap = new HashMap();
        paramMap.put("method", "queryTransTask");
        paramMap.put("providerId", 100000001);
        paramMap.put("taskType", "product");
        paramMap.put("transType", "CTE");
        
         List ls = new ArrayList();
        ls.add(100000047);
        ls.add(100000053);
        ls.add(100000056);
        ls.add(100000058);
        ls.add(100000059);
        ls.add(100000060);
        ls.add(100000061);
     paramMap.put("taskIdList", ls);
        System.out.println(JSONObject.fromObject(paramMap).toString());

    }

    /**
     * @param Map <br>
     * **********|--method=��receiveTransTask�� //(����) <br>
     * **********|--taskType=��product�� //(����) <br>
     * **********|--providerId //��ʶ��ǰ����ͻ���id(����) <br>
     * **********|--receiveType=(new or old) //(����)���������ͱ�ʶ�Ǹ���taskcount�� ��ȡ���Ǹ�������id��list������������ȡ(����id)һ��������ȡ��������,������ȡ.<br>
     * **********|--transType //��ʶ��ȡ����������Ŀǰֻ��CTE <br>
     * **********|--taskCount //������100 . ����Ĭ��100 ��receiveType = new��Ч<br>
     * **********|--taskList=List // ��receiveType = oldʱ���������Ч<br>
     * **************************|-- taskId //����.����id��list���ϵ�����С��100;
     * 
     * @throws IOException
     * @throws HttpException
     */

    public static void testreceiveTransTask() {

        Map paramMap = new HashMap();
        List ls = new ArrayList();
        ls.add(100000025);
        ls.add(100000038);
  //    paramMap.put("taskIdList", ls);
        paramMap.put("providerId", 100000001);
        paramMap.put("method", "receiveTransTask");
        paramMap.put("transType", "CTE");
        paramMap.put("taskCount", 10);
        paramMap.put("taskType", "product");
        paramMap.put("receiveType", "new");

        System.out.println(JSONObject.fromObject(paramMap).toString());
    }

    
    
    @SuppressWarnings("unchecked")
	public static void testtransactTransTask()  {
        Map paramMap = new HashMap();
        paramMap.put("method", "transactTransTask");
        paramMap.put("providerId", 100000001);
       List ls = new ArrayList();
       
       
       paramMap.put("param", ls);
        Map m = new HashMap();
        m.put("taskId", 100000043);
        m.put("briefDesc", "briefDesc,hello liyanfang");
        m.put("detailDesc", "detailDesc, hello liyanfang");
        m.put("isReject", "n");
        m.put("comments", "");
        ls.add(m);
        Map m2 = new HashMap();
        m2.put("taskId", 100000044);
        m2.put("isReject", "y");
        m2.put("comments", "���˰�!");
        ls.add(m2);
        System.out.println(JSONObject.fromObject(paramMap).toString());

    }
    public  static void main(String[] dd){
   //	Test1.testisBusy();
 	// Test1.testqueryTransTask();
   //  Test1.testreceiveTransTask();
       Test1.testtransactTransTask();
    }
}
