import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class TestReg0 {

	
	 /**���ʹ�õ�ʹ�ò���Ҫ���±���������ʽ�ˣ�����Ƶ�����������Ч��
	  * 
	  *
	  * */
	  public static   final String patternString1="<[aA]\\s*href='([^>]+)'>(.*)</[aA]>";
	 
	 
	  public static    Pattern pattern1 =Pattern.compile(patternString1,Pattern.DOTALL);
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		 /**���Ե�����*/
	    String ss="���ǲ���<a href=http://www.ba*****idu.cn>www.goog[]e.cn</a>����ǲ���������<A href='http://www.google.cn'>www.google.cn</a>��";
     
	    parseUrl(null,ss);
	}
	
	public static void parseUrl(Set<String> set,String var)
	{
		Matcher matcher=null;
		
				matcher=pattern1.matcher(var);
			
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

