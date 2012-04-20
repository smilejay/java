import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;


public class AddTwoStrings extends AbstractJavaSamplerClient {
	
	@Override
	public Arguments getDefaultParameters() {
		Arguments param = new Arguments();
		param.addArgument("string1", "");
		param.addArgument("string2", "");
		return param;
	}
	

	@Override
	public SampleResult runTest(JavaSamplerContext arg0) {
//		JMeterVariables   jMeterVariables =   JMeterContextService.getContext().getVariables();
		
		SampleResult sampleResult  = new SampleResult();
		sampleResult.setResponseCode("200");
		sampleResult.setResponseData("test1-test2".getBytes());
		sampleResult.setResponseMessage("test1-test2");
		sampleResult.setSuccessful(true);
		
		return sampleResult;
	}

}
