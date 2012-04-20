/*
*
*   �򵥵Ķ�/д�ı��ļ���ʾ��
*   ����������������ӣ���
*   1.   ���ļ����뵽�ڴ棨������StringBuffer��������
*   2.   �������е��ı�д���ļ�
*   3.   ��һ���ļ������ݶ�����д����һ���ļ���
*         ͬʱҲչʾ��������������ж���������д��������У������ı�����
*         �������ӿ��Զ������ڣ����Ը�����Ҫֻ������һ�������ˡ�
*/

import   java.io.BufferedReader;
import   java.io.FileInputStream;
import   java.io.FileOutputStream;
import   java.io.IOException;
import   java.io.InputStream;
import   java.io.InputStreamReader;
import   java.io.OutputStream;
import   java.io.OutputStreamWriter;
import   java.io.PrintStream;
import   java.io.PrintWriter;

public   final   class   AccessTextFile   {

       /**
         *   1.   ��ʾ�����е��ı�����һ��   StringBuffer   ��
         *   @throws   IOException
         */
       public   void   readToBuffer(StringBuffer   buffer,   InputStream   is)
               throws   IOException   {
               String   line;                 //   ��������ÿ�ж�ȡ������
               BufferedReader   reader   =   new   BufferedReader(new   InputStreamReader(is));
               line   =   reader.readLine();               //   ��ȡ��һ��
               while   (line   !=   null)   {                     //   ���   line   Ϊ��˵��������
                       buffer.append(line);                 //   ��������������ӵ�   buffer   ��
                       buffer.append("\\n");                 //   ��ӻ��з�
                       line   =   reader.readLine();       //   ��ȡ��һ��
               }
       }

       /**
         *   2.   ��ʾ��   StringBuffer   �е����ݶ���������
         */
       public   void   writeFromBuffer(StringBuffer   buffer,   OutputStream   os)   {
               //   ��   PrintStream   ���Է���İ�����������������
               //   �������÷���   System.out   һ��
               //   ��System.out   �������   PrintStream   ����
               PrintStream   ps   =   new   PrintStream(os);      
               ps.print(buffer.toString());
       }

       /**
         *   3*.   ���������п������ݵ���������
         *   @throws   IOException
         */
       public   void   copyStream(InputStream   is,   OutputStream   os)   throws   IOException   {
               //   ����������̿��Բ���   readToBuffer   �е�ע��
               String   line;
               BufferedReader   reader   =   new   BufferedReader(new   InputStreamReader(is));
               PrintWriter   writer   =   new   PrintWriter(new   OutputStreamWriter(os));
               line   =   reader.readLine();
               while   (line   !=   null)   {
                       writer.println(line);
                       line   =   reader.readLine();
               }
               writer.flush();           //   ���ȷ��Ҫ��������еĶ�����д��ȥ��
                                                       //   ���ﲻ�ر�   writer   ����Ϊ   os   �Ǵ����洫������
                                                       //   ��Ȼ���Ǵ�����򿪵ģ�Ҳ�Ͳ�������ر�
                                                       //   ����رյ�   writer����װ�������   os   Ҳ�ͱ�����
       }

       /**
         *   3.   ����   copyStream(InputStream,   OutputStream)   ���������ı��ļ�
         */
       public   void   copyTextFile(String   inFilename,   String   outFilename)
               throws   IOException   {
               //   �ȸ�������/����ļ�������Ӧ������/�����
               InputStream   is   =   new   FileInputStream(inFilename);
               OutputStream   os   =   new   FileOutputStream(outFilename);
               copyStream(is,   os);           //   ��   copyStream   ��������
               is.close();   //   is   ��������򿪵ģ�������Ҫ�ر�
               os.close();   //   os   ��������򿪵ģ�������Ҫ�ر�
       }

       public   static   void   main(String[]   args)   throws   IOException   {
               int   sw   =   3;           //   ���ֲ��Ե�ѡ�񿪹�
               AccessTextFile   test   =   new   AccessTextFile();
              
               switch   (sw)   {
               case   1:   //   ���Զ�
               {
                       InputStream   is   =   new   FileInputStream("D:\\workspace\\2010_04\\files\\scrm_manage_products_approved.htm");
                       StringBuffer   buffer   =   new   StringBuffer();
                       test.readToBuffer(buffer,   is);
                       System.out.println(buffer);           //   ������   buffer   �е�����д����
                       is.close();
                       break;
               }
               case   2:   //   ����д
               {
                       StringBuffer   buffer   =   new   StringBuffer("Only   a   test\\n");
                       test.writeFromBuffer(buffer,   System.out);
                       break;
               }
               case   3:   //   ���Կ���
               {
                       test.copyTextFile("D:\\workspace\\2010_04\\files\\scrm_manage_products_approved.htm",   "D:\\workspace\\2010_04\\files\\scrm_manage_products_approved_bk.htm");
               }
                       break;
               }
       }

}
