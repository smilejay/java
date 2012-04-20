import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

   /**
    * 
    * @author master
    *
    */

public class TestBiggerThanFive extends AbstractJavaSamplerClient {
	
	 private SampleResult results;
     private String str;
 //初始化方法，实际运行时每个线程仅执行一次，在测试方法运行前执行，类似于LoadRunner中的init方法
     public void setupTest(JavaSamplerContext arg0) {
//          results = new SampleResult();
//          str = arg0.getParameter("number", "");
//          if (str != null && str.length() > 0) {
//                 results.setSamplerData(str);
//          }
     }
	
	@Override
	public Arguments getDefaultParameters() {
		Arguments param = new Arguments();
		param.addArgument("number", "0");
		return param;
	}
	
	@Override
	public SampleResult runTest(JavaSamplerContext context) {
		boolean result;
        str = context.getParameter("number");
        results = new SampleResult();
        results.sampleStart();     //定义一个事务，表示这是事务的起始点，类似于LoadRunner的 lr.start_transaction
        result = BiggerThanFive.isBiggerThanFive(str);
        results.sampleEnd();     //定义一个事务，表示这是事务的结束点，类似于LoadRunner的 lr.end_transaction
        results.setDataEncoding("UTF-8");
        if ( result == true){
        	results.setSuccessful(true);
        	results.setResponseCode("200");
        	results.setSampleLabel(str);
        }
        else {
        	results.setSuccessful(false);
        	results.setResponseCode("500");
        	results.setSampleLabel(str);
        }
		return results;
	}
	
	 //结束方法，实际运行时每个线程仅执行一次，在测试方法运行结束后执行，类似于LoadRunner中的end方法
    public void teardownTest(JavaSamplerContext arg0) {
    }

}
