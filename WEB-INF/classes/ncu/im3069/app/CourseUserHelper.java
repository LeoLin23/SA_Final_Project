package ncu.im3069.app;

import java.sql.*;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import ncu.im3069.app.Course;
import ncu.im3069.util.DBMgr;

public class CourseUserHelper {
    private CourseUserHelper() {
        
    }
    
    private static UserHelper uh;
    private static CourseHelper ch;
    private static CourseUserHelper cuh;
    private Connection conn = null;
    private PreparedStatement pres = null;
    
    public static CourseUserHelper getHelper() {
        /** Singleton檢查是否已經有ProductHelper物件，若無則new一個，若有則直接回傳 */
        if(cuh == null) cuh = new CourseUserHelper();
        
        return cuh;
    }
    
    public static CourseHelper getCourseHelper() {
    	if(ch == null) ch = new CourseHelper();
        
        return ch;
    }
    
    public static UserHelper getUserHelper() {
		if(uh == null) uh = new UserHelper();
		        
        return uh;
    }
    
    public void enroll(int user_id, String course_id) {
    	String execute_sql;
    	
    	try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "INSERT INTO `sa_project`.`courseuser`(`course_id`, `user_id`)"
                    + " VALUES(?, ?)";
            
            //System.out.println(name);
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, course_id);
            pres.setInt(2, user_id);           
            
            /** 執行新增之SQL指令並記錄影響之行數 */
            pres.executeUpdate();
            
            /** 紀錄真實執行的SQL指令，並印出 **/
            execute_sql = pres.toString();
            System.out.println(execute_sql);

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(pres, conn);
        }
    }
    
    public JSONObject getCourseListByUserId(int user_id) {
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT `course_id` FROM `sa_project`.`courseuser` WHERE `user_id` = ?";
            
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, user_id);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs.next()) {                
                /** 將 ResultSet 之資料取出 */
                int course_id = rs.getInt("course_id");
                System.out.println("course found: " + course_id);
                /** 將每一筆商品資料產生一名新Product物件 */
                if (ch == null) {
                	ch = new CourseHelper();
                }
                Course c = ch.getCourseByID(String.valueOf(course_id));
                /** 取出該項商品之資料並封裝至 JSONsonArray 內 */
                jsa.put(c.getData());
            }

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }
        
        /** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("data", jsa);

        return response;
    }
    
    public JSONObject getEnrolledList(int course_id) {
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT `user_id` FROM `sa_project`.`courseuser` WHERE `course_id` = ?";
            
            
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, course_id);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs.next()) {                
                /** 將 ResultSet 之資料取出 */
                int user_id = rs.getInt("user_id");
                
                /** 將每一筆商品資料產生一名新Product物件 */
                if (uh == null) {
                	uh = new UserHelper();
                }
                User u = uh.getUserByID(String.valueOf(user_id));
                /** 取出該項商品之資料並封裝至 JSONsonArray 內 */
                jsa.put(u.getData());
            }

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }
        
        /** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("data", jsa);

        return response;
    }
    
    /*************Problem!!!!!****************/
    
    public ArrayList<Integer> getUserListByCourseId(int course_id) {
        ArrayList<Integer> user_array = new ArrayList<Integer>();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT `user_id` FROM `sa_project`.`courseuser` WHERE `course_id` = ?";
            
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, course_id);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println("[幹你娘機掰 我在這啦哈哈]" + exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs.next()) {                
                /** 將 ResultSet 之資料取出 */
                int user_id = rs.getInt("user_id");
                user_array.add(user_id);
            }

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
        	System.out.println("error from getUserListByCourseId()");
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }

        return user_array;
    }
    
}
