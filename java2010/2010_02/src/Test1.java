import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;


public class Test1 {

    /**
     *设置是否忙
     * 
     *@param Map <br>
     **********|-- method=” isBusy”<br>
     **********|-- providerId //标识当前翻译客户的id <br>
     **********|-- param= (y or n) //标识自己的忙闲状态y =忙n=闲<br>
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
     * **********|--method=”receiveTransTask” //(必填) <br>
     * **********|--taskType=”product” //(必填) <br>
     * **********|--providerId //标识当前翻译客户的id(必填) <br>
     * **********|--receiveType=(new or old) //(必填)参数的类型标识是根据taskcount新 领取还是根据任务id的list集合来从新领取(任务id)一般是以领取过的任务,从新领取.<br>
     * **********|--transType //标识领取任务翻译类型目前只有CTE <br>
     * **********|--taskCount //不超过100 . 不填默认100 当receiveType = new生效<br>
     * **********|--taskList=List // 当receiveType = old时这个参数生效<br>
     * **************************|-- taskId //多条.任务id的list集合的数量小于100;
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
        m2.put("comments", "退了啊!");
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
