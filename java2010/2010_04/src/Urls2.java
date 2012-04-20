/** *//**//*
功能 根据输入的网址、起止字符串，采集改网址页面的超链接(如http://blog.sina.com.cn/s /blog_48ea77a001008y8d.html)和链接文字
作者 程序人生博客http://blog.csdn.net/zhuche110/
日期 2008.4.20
*/

 
import java.net.*;
import java.io.*;
import java.util.regex.*;

public class Urls2
{
    String sourceURL;//需要采集的网页网址
    String sourceContent;//网页页面内容
    //String URLs; //采集到的超链接
    //String title;//采集到的链接文字
    String beginStr;//网页内容匹配区域开始字符串
    String endStr;//网页内容匹配区域结束字符串
    String matchContent;//网页内容匹配区域
    
    
    public static void main(String[] args)
    {
        Urls2 urls2=new Urls2("http://blog.sina.com.cn/","<body","</body>");
        urls2.getSourceContent(urls2.sourceURL);
        urls2.matchContent=urls2.getMatchContent(urls2.beginStr,urls2.endStr);
        urls2.getString(urls2.matchContent);
         
        
    }
    
    public Urls2()
    {
    }
    
    //根据传来的网页网址、匹配区域起止字符串初始化
    public Urls2(String sourceURL1,String beginStr1,String endStr1)
   {
         sourceURL=sourceURL1;
         beginStr=beginStr1;
         endStr=endStr1;
    }
    
    //获取网页页面内容
    public void getSourceContent(String  URLStr)
    {
        StringBuffer sb=new StringBuffer();
        try
        {
            URL newURL=new URL(URLStr);
            BufferedReader br=new BufferedReader(
                                                        new InputStreamReader(newURL.openStream()));
                                                                
                    
            String temp;
            while((temp=br.readLine())!=null)
            {
                sb.append(temp);
            }
            sourceContent=sb.toString();
            
        }
        catch(MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    //获取匹配区域
    public String getMatchContent(String beginStr,String endStr)
    {
        
        String regex=beginStr+".*?"+endStr;
        //System.out.println(regex);
        Pattern pt=Pattern.compile(regex);
        Matcher mt=pt.matcher(sourceContent);
        if(mt.find())
        {
            return matchContent=mt.group();
        }
        else return null;
        
  }
  //----- ------------------下面开始是修改部分 begin-----------------
  
  //获取需要并打印的部分:超链接和标题
  public void getString(String s)
  { 
      int counter=0;//计算器 计算匹配的个数
    
       
      String regexURL="<a href=\(\"http:\/\/blog.sina.com.cn/s/.*?\.html\"\).*?>(.*?)</a>";
      Pattern pt=Pattern.compile(regexURL);
      Matcher mt=pt.matcher(s);
      String notInclude=".*?高敏.*?";
      String mustInclude=".*?http://blog.sina.com.cn/s/.*?";
      String mustInclude2=null;
     // String mustInclude2=".*?图.*?";
      while(mt.find())
      {
           String s2=mt.group();
              if(s2.matches(mustInclude)&&!s2.matches(notInclude))
              {
               counter++;//计数器计算获取的个数
               
               //打印mt.group(0);
               System.out.println(mt.group());
               
               //获取并打印标题
                String title=mt.group(2);
                System.out.println("标题："+title);
               
                //获取并打印网址
                String urls=mt.group(1);
                System.out.println("网址："+urls);
 
                System.out.println();//空行
               
          }   
      }
      
       //----- ------------------上面是修改部分 end-----------------
      
       System.out.println("共有"+counter+"个符合结果");
  }
  
 
    
    
}
