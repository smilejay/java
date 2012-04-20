import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
/**
 * @author 笑遍世界
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestLength extends AbstractJavaSamplerClient{
     private SampleResult results;
     private String testStr;
 //初始化方法，实际运行时每个线程仅执行一次，在测试方法运行前执行，类似于LoadRunner中的init方法
     public void setupTest(JavaSamplerContext arg0) {
          results = new SampleResult();
          testStr = arg0.getParameter("testString", "");
          if (testStr != null && testStr.length() > 0) {
                 results.setSamplerData(testStr);
  }
 }
 //设置传入的参数，可以设置多个，已设置的参数会显示到Jmeter的参数列表中
     public Arguments getDefaultParameters() {
             Arguments params = new Arguments();
             params.addArgument("testStr", "abc");   //定义一个参数，显示到Jmeter的参数列表中，第一个参数为参数默认的显示名称，第二个参数为默认值
            return params;
 }
 //测试执行的循环体，根据线程数和循环次数的不同可执行多次，类似于LoadRunner中的Action方法
     @SuppressWarnings("deprecation")
	public SampleResult runTest(JavaSamplerContext context) {
          int len = 0;
          results.sampleStart();     //定义一个事务，表示这是事务的起始点，类似于LoadRunner的 lr.start_transaction
          testStr = context.getParameter("testStr");
          len = testStr.length();
          results.sampleEnd();     //定义一个事务，表示这是事务的结束点，类似于LoadRunner的 lr.end_transaction
          results.setDataEncoding("UTF-8");
          if(len < 5){
                  System.out.println(testStr);
                  results.setResponseCode("testStr:"+testStr);
                  results.setResponseData("testStr:"+testStr);
                  results.setSuccessful(false);   //用于设置运行结果的成功或失败，如果是"false"则表示结果失败，否则则表示成功
         }else{
        	 results.setResponseCode("testStr:"+testStr);
        	 results.setResponseData("testStr:"+testStr);
             results.setSuccessful(true);
             }
        return results;
 }
 //结束方法，实际运行时每个线程仅执行一次，在测试方法运行结束后执行，类似于LoadRunner中的end方法
     public void teardownTest(JavaSamplerContext arg0) {
     }
}