/** *//**//*
���� �����������ַ����ֹ�ַ������ɼ�����ַҳ��ĳ�����(��http://blog.sina.com.cn/s /blog_48ea77a001008y8d.html)����������
���� ������������http://blog.csdn.net/zhuche110/
���� 2008.4.20
*/

 
import java.net.*;
import java.io.*;
import java.util.regex.*;

public class Urls2
{
    String sourceURL;//��Ҫ�ɼ�����ҳ��ַ
    String sourceContent;//��ҳҳ������
    //String URLs; //�ɼ����ĳ�����
    //String title;//�ɼ�������������
    String beginStr;//��ҳ����ƥ������ʼ�ַ���
    String endStr;//��ҳ����ƥ����������ַ���
    String matchContent;//��ҳ����ƥ������
    
    
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
    
    //���ݴ�������ҳ��ַ��ƥ��������ֹ�ַ�����ʼ��
    public Urls2(String sourceURL1,String beginStr1,String endStr1)
   {
         sourceURL=sourceURL1;
         beginStr=beginStr1;
         endStr=endStr1;
    }
    
    //��ȡ��ҳҳ������
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
    
    //��ȡƥ������
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
  //----- ------------------���濪ʼ���޸Ĳ��� begin-----------------
  
  //��ȡ��Ҫ����ӡ�Ĳ���:�����Ӻͱ���
  public void getString(String s)
  { 
      int counter=0;//������ ����ƥ��ĸ���
    
       
      String regexURL="<a href=\(\"http:\/\/blog.sina.com.cn/s/.*?\.html\"\).*?>(.*?)</a>";
      Pattern pt=Pattern.compile(regexURL);
      Matcher mt=pt.matcher(s);
      String notInclude=".*?����.*?";
      String mustInclude=".*?http://blog.sina.com.cn/s/.*?";
      String mustInclude2=null;
     // String mustInclude2=".*?ͼ.*?";
      while(mt.find())
      {
           String s2=mt.group();
              if(s2.matches(mustInclude)&&!s2.matches(notInclude))
              {
               counter++;//�����������ȡ�ĸ���
               
               //��ӡmt.group(0);
               System.out.println(mt.group());
               
               //��ȡ����ӡ����
                String title=mt.group(2);
                System.out.println("���⣺"+title);
               
                //��ȡ����ӡ��ַ
                String urls=mt.group(1);
                System.out.println("��ַ��"+urls);
 
                System.out.println();//����
               
          }   
      }
      
       //----- ------------------�������޸Ĳ��� end-----------------
      
       System.out.println("����"+counter+"�����Ͻ��");
  }
  
 
    
    
}
