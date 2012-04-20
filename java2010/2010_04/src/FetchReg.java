import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class FetchReg {

	
	 /**多次使用的使用不需要重新编译正则表达式了，对于频繁调用能提高效率
	  * 
	  *
	  * */
//	  public static   final String patternString1="img\\s*src=\"(.*[(gif)|(jpg)|(jpeg)|(png)|(bmp)]{1})\".*</[aA]>";
	  public static   final String patternString1="<img\\s*src=\"([^>|\"]+[(gif)|(jpg)|(jpeg)|(png)|(bmp)])\".*?</[aA]>";
	 
	 
	  public static    Pattern pattern1 =Pattern.compile(patternString1,Pattern.DOTALL);
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		 /**测试的数据*/
//	    String ss="485948956 img src=\"http://img.alibaba.com/images/eng/no_photo.gif\"4599090";
	    StringBuffer sb= new StringBuffer("<img src=\"http://img.alibaba.com/images/eng/style/icon/add_contact.gif\" </a>alt=\"Add to my Contacts\" border=\"0\"<img src=\"http://img.alibaba.com/images/eng/style/icon/add_contact.jpg\"></A>");
     
//	    parseUrl(null,ss);
	    parseUrl(null,sb);
	}
	
	public static void parseUrl(Set<String> set,StringBuffer temp)
	{
		StringBuffer sb = new StringBuffer();
		Matcher matcher=null;
		matcher=pattern1.matcher(temp);
				while(matcher!=null && matcher.find())
				{
					int a=matcher.groupCount();
					while(a>0)
					{
						System.out.println(matcher.group(a));
						sb.append(matcher.group(a));
						sb.append("\n");
						a--;
					}
				}
				try {
					FileWriter fw = new FileWriter("D:\\workspace\\2010_04\\files\\test.txt");
					fw.write(sb.toString());
					fw.flush();
					} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}

	}

}