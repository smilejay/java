import java.io.*;
//import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
	
public class ProcessTransData {
	private static String path = "D:\\workshop\\2010_02\\files\\ProcessTransData.txt";
	private static InputStreamReader stdin=new InputStreamReader(System.in);
	private static BufferedReader br=new BufferedReader(stdin);
	
	public static void main(String[] args) throws IOException {
		boolean deleteFlag = deleteFile(path);  //先删除文件\
		File filename = new File(path);
		Integer start = 100035261;
		Integer provider_id=100000002;
		if (deleteFlag){
			transactTransTaskWrite(filename,start,provider_id);
		}
		
		else {
			System.out.println("Error..");
			return;
		}     
  } 	
	     /**   
	      * 删除单个文件   
	      * @param   fileName    被删除文件的文件名   
	      * @return 单个文件删除成功返回true,否则返回false   
	      **/    
	     public static boolean deleteFile(String fileName){     
	         File file = new File(fileName);     
	         if(file.isFile() && file.exists()){     
	             file.delete();     
	             System.out.println("已删除文件"+fileName+"成功！");     
	             return true;     
	         }else{     
	             System.out.println("删除文件"+fileName+"失败！");     
	             return false;     
	         }     
	     } 
	   
	  public static void creatTxtFile(File filename) throws IOException{
	         if (!filename.exists()) {
	             filename.createNewFile();
	             System.out.println(filename + "已创建！");
	         }
	     }
	  
	  public static int readInt(){
		  int s = 0;
		  try{
			  if(br.readLine()!=null)
				  s=Integer.parseInt(br.readLine());
			  else 
				  System.out.println("readLine() Error....");
		  }catch(Exception e){  
		   System.out.println(e.toString());
		   System.exit(0);
		  }
		  return s;
		 }
	  
		public static void transactTransTaskWrite(File filename,Integer start,Integer provider_id) throws IOException {
	        Map paramMap = new HashMap();
	        
	        BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
	        
	        String head="{requests:[{namespace:\"ali.intl.translational.translate\",operation:\"aliRemoteUpdateDev\",parameters:{inMap: ";
	        
	        final int COUNT1 = 10;  //一次传回的任务条数
	        final int COUNT2 = 1000;  //每个provider传的次数
	        for (int k=0;k<5;k++)
	        {
	        	for (int j=0;j<COUNT2;j++)
		        {
		        	 paramMap.put("method", "transactTransTask");
				        paramMap.put("providerId", provider_id+k);
				        List ls = new ArrayList();
				        paramMap.put("param", ls);
				        
//				        Integer end = start+COUNT1;
				        for(int i=0;i<COUNT1;i++)
				        {		       
					        Map m = new HashMap();
					        m.put("taskId", start+k*COUNT2*COUNT1+j*COUNT1+i);
					        m.put("briefDesc", "briefDesc,hello jay");
					        m.put("detailDesc", "Alibaba Group, through its subsidiaries, operates as a family of Internet-based businesses in the People’s Republic of China and internationally. It provides software, technology, and other services connecting small and medium-sized buyers and suppliers. The company offers platforms for international trade, domestic trade and retail trade; and provides business services related to SME financing, online marketing and management software. Its products and services also include online payment, instant messaging, online advertising exchange, classified listings and cloud computing."
+"Listings displayed on Alibaba.com’s online marketplaces cover a range of products and services in various industries, including agriculture, apparel, automobile, business services, chemicals, computer hardware and software, construction and real estate, electrical equipment supplies, electronic components and supplies, energy, environment, excess inventory, fashion accessories, food and beverage, furniture and furnishings, gifts and crafts, health and beauty, home appliances, home supplies, industrial supplies, lights and lighting, luggage, bags and cases, minerals, metals and materials, office supplies, packaging and paper, printing and publishing, security and protection, shoes and accessories, sports and entertainment, telecommunications, textiles and leather products, timepieces, jewelry and eyewear, toys, and transportation."
+"Alibaba Group, through its subsidiaries, operates as a family of Internet-based businesses in the People’s Republic of China and internationally. It provides software, technology, and other services connecting small and medium-sized buyers and suppliers. The company offers platforms for international trade, domestic trade and retail trade; and provides business services related to SME financing, online marketing and management software. Its products and services also include online payment, instant messaging, online advertising exchange, classified listings and cloud computing.");
					        m.put("isReject", "n");
					        m.put("comments", "");
					        ls.add(m);
				        }

//				        Map m2 = new HashMap();
//				        m2.put("taskId", 100000044);
//				        m2.put("isReject", "y");
//				        m2.put("comments", "退了啊!");
//				        ls.add(m2);
//				        System.out.println(JSONObject.fromObject(paramMap).toString());
				       bw.write(head+JSONObject.fromObject(paramMap).toString()+"}}]}\n");
		        }
	        }      
		       bw.close();
	        }

}
