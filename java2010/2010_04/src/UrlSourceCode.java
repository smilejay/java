import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 
 */

/**
 * @author Jay
 *
 */
public class UrlSourceCode {
    public static void main(String[] args) throws MalformedURLException, IOException {
        URLConnection uc = new URL("http://unioncameraltd.en.alibaba.com/productlist.html").openConnection();
        uc.setConnectTimeout(10000);
        uc.setDoOutput(true);  
        InputStream in = new BufferedInputStream(uc.getInputStream());
        Reader rd = new InputStreamReader(in);
        int c = 0;
        StringBuffer temp = new StringBuffer();
        while ((c = rd.read()) != -1) {
            temp.append((char) c);
        }
        in.close();                 
        System.out.println(temp.toString());
        
        FetchReg reg = new FetchReg();
        reg.parseUrl(null,temp);
//        TestReg reg1 = new TestReg();
//        reg1.parseUrl(null, temp.toString());
        
    }
}


