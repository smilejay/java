import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 */

/**
 * @author Jay
 *
 */
public class FileToImgUrl {
	
	public static void main(String[] args) throws IOException {
	    StringBuffer temp = new StringBuffer();
	    AccessTextFile atf = new AccessTextFile();
	    InputStream   is   =   new   FileInputStream("D:\\workspace\\2010_04\\files\\sourcecode.txt");
	    atf.readToBuffer(temp, is);
	    is.close();
        FetchReg.parseUrl(null,temp);
	}

}
