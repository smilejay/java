package qa;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ajf.K;

public class MyalibabaUtils2 {

    private static String col_name  = "standard_attr_desc";
    private static String space_str = " ";

    public static void main(String[] args) throws Exception {

        String sql = "select count(1) as num from ws_product_attribute_draft order by product_id";

        sql = "select * from ws_product_attribute_draft";
        List list = query(sql, null);
        int num = list.size();
        System.out.println("list size =" + num);
        Map row = null;
        String str = null;
        String product_id = null;
        boolean flag = false;
        int errorNum = 0;
        List<String> errorStrList = null;
        for (int i = 0; i < num; i++) {
            row = (Map) list.get(i);
            str = (String) row.get(col_name);
            product_id = (String) row.get("product_id");
            // flag = isOk(str);
            errorStrList = getErrorStrList(str);
            if (errorStrList.size() > 0) {
                errorNum++;
                System.out.println(product_id + "---" + list2str(errorStrList));
            }
            // System.out.println(row);
            // System.out.println(str);
        }
        System.out.println("errorNum=" + errorNum);
        // str = "158=IU#22 - 333:m";
        // System.out.println(str);
        // isOkSingle(str);
        // int spaceNum = getSpaceNum(str);
        // System.out.println(spaceNum);
    }

    public static void update(String sql, Object[] params) throws Exception {
        Connection cn = null;
        try {
            cn = getConn();
            K.update(cn, sql, params);

        } catch (Exception e) {
            throw e;
        } finally {
            K.close(cn);
        }
    }

    public static List query(String sql, Object[] params) throws Exception {
        Connection cn = null;
        try {
            cn = getConn();
            return K.query(cn, sql, params);

        } catch (Exception e) {
            throw e;
        } finally {
            K.close(cn);
        }
    }

    public static Connection getConn() throws Exception {
        return K.getConn("ma");
    }

    public static boolean isOk(String str) {
        if (StringUtils.isBlank(str)) {
            return true;
        }
        String[] arr = str.split(",");
        int num = arr.length;
        String subStr = null;
        boolean flag = false;
        for (int i = 0; i < num; i++) {
            subStr = arr[i];
            flag = isOkSingle(subStr);
            if (!flag) {
                return false;
            }
        }
        return true;
    }

    public static boolean isOkSingle(String str) {
        if (StringUtils.isBlank(str)) {
            return true;
        }
        // if(str.indexOf("IU#")<0){return true;}
        int pos = str.indexOf("I#");
        if (pos < 0) {
            return true;
        }
        String subStr = str.substring(pos + 2);
        // System.out.println(subStr);
        int spaceNum = getSpaceNum(subStr);
        if (spaceNum >= 2) {
            return true;
        }
        return false;
    }

    public static int getSpaceNum(String str) {
        if (StringUtils.isBlank(str)) {
            return 0;
        }
        int num = str.length();
        String chStr = "";
        int spaceNum = 0;
        for (int i = 0; i < num; i++) {
            chStr = str.charAt(i) + "";
            if (chStr.equals(space_str)) {
                spaceNum++;
            }
        }
        return spaceNum;
    }

    public static List<String> getErrorStrList(String str) {
        List<String> list = new ArrayList<String>();
        if (StringUtils.isBlank(str)) {
            return list;
        }
        String[] arr = str.split(",");
        int num = arr.length;
        String subStr = null;
        boolean flag = false;
        for (int i = 0; i < num; i++) {
            subStr = arr[i];
            flag = isOkSingle(subStr);
            if (!flag) {
                list.add(subStr);
            }
        }
        return list;
    }

    public static String list2str(List<String> list) {
        int num = list.size();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(list.get(i));
        }
        return sb.toString();
    }

}
