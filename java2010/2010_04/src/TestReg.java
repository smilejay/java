import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class TestReg {

	
	 /**多次使用的使用不需要重新编译正则表达式了，对于频繁调用能提高效率
	  * 
	  *
	  * */
	  public static   final String patternString1="img\\s*src=\"(.*)\"";
	 
	 
	  public static    Pattern pattern1 =Pattern.compile(patternString1,Pattern.DOTALL);
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		 /**测试的数据*/
	    String ss="485948956 img src=\"http://img.alibaba.com/images/eng/no_photo.gif\"4599090";
     
	    parseUrl(null,ss);
	}
	
	public static void parseUrl(Set<String> set,String ss)
	{
		Matcher matcher=null;
		matcher=pattern1.matcher(ss);
				while(matcher!=null && matcher.find())
				{
					int a=matcher.groupCount();
					while(a>0)
					{
						System.out.println(matcher.group(a));
						a--;
					}
				}
	}

}

