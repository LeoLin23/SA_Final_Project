package ncu.im3069.app;

import java.sql.*;

import org.json.JSONArray;
import org.json.JSONObject;

import ncu.im3069.util.DBMgr;

public class UserAssignmentHelper {
    UserAssignmentHelper() {
        
    }
    
    private static UserAssignmentHelper uah;
    private static AssignmentHelper ah;
    private static UserHelper uh;    
    private Connection conn = null;
    private PreparedStatement pres = null;
    
    public static UserAssignmentHelper getHelper() {
        /** Singleton檢查是否已經有ProductHelper物件，若無則new一個，若有則直接回傳 */
        if(uah == null) uah = new UserAssignmentHelper();
        
        return uah;
    }
    
    public static AssignmentHelper getAssignmentHelper() {
    	/** Singleton檢查是否已經有ProductHelper物件，若無則new一個，若有則直接回傳 */
    	if(ah == null) ah = new AssignmentHelper();
    	
    	return ah;
    }
    
    public static UserHelper getUserHelper() {
    	/** Singleton檢查是否已經有ProductHelper物件，若無則new一個，若有則直接回傳 */
    	if(uh == null) uh = new UserHelper();
    	
    	return uh;
    }
    
    public JSONObject submit(int input_assignment_id, int input_user_id) {
    	UserAssignment ua = new UserAssignment(input_assignment_id, input_user_id);
    	/** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "INSERT INTO `sa_project`.`userassignment`(`assignment_id`, `user_id`, `score`)"
                    + " VALUES(?, ?, ?)";
            
            /** 取得所需之參數 */
            int assignment_id = ua.getAssignmentId();
            int user_id = ua.getUserId();
            ua.calcScore();
            int score = ua.getScore();
            System.out.println("XXXXXXXXXXXXXX "+score);
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, assignment_id);
            pres.setInt(2, user_id);      
            pres.setInt(3, score);
            
            pres.execute();
            
            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
        	System.out.println("error from UAH.submit()");
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(pres, conn);
        }

        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);

        return response;
    }
    
    public boolean checkSub(String input_assignment_id, String input_user_id) {
    	boolean isSub = false;
    	
    	/** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `sa_project`.`userassignment` WHERE `assignment_id` = ? AND `user_id` = ?";
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, input_assignment_id);
            pres.setString(2, input_user_id);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println("From UA.checkSub:");
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            /** 正確來說資料庫只會有一筆該會員編號之資料，因此其實可以不用使用 while 迴圈 */
            isSub = rs.next();
            
            
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
        
        return isSub;
    }
    
    public JSONObject getJsoById(String input_assignment_id, int identity, String input_user_id) {
    	UserAssignment ua = null;
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `sa_project`.`userassignment` WHERE `assignment_id` = ? AND `user_id` = ? LIMIT 1";
            pres = conn.prepareStatement(sql);
            
            if (identity == 1) {
            	sql = "SELECT * FROM `sa_project`.`userassignment` WHERE `assignment_id` = ?";
            	/** 將參數回填至SQL指令當中 */
                pres.setString(1, input_assignment_id);
            } else {
            	pres.setString(1, input_assignment_id);
            	pres.setString(2, input_user_id);
            }
            
            
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println("From UA.getJsoByID:");
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            /** 正確來說資料庫只會有一筆該會員編號之資料，因此其實可以不用使用 while 迴圈 */         
            while(rs.next()) {                
                /** 將 ResultSet 之資料取出 */
            	int assignment_id = rs.getInt("assignment_id");
                int user_id = rs.getInt("user_id");
                int score = rs.getInt("score");

                ua = new UserAssignment(assignment_id, user_id, score);
                
                System.out.println("Score:     "+ua.getScore());
                
                /** 取出該名會員之資料並封裝至 JSONsonArray 內 */
                jsa.put(ua.getData());
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
        response.put("message", "from UAH.getJsoById");

        return response;
    }
    
    public Assignment getByAssignmentId(String id) {
        /** 新建一個 Product 物件之 m 變數，用於紀錄每一位查詢回之商品資料 */
        Assignment as = null;
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `sa_project`.`assignments` WHERE `assignments`.`assignment_id` = ? LIMIT 1";
            
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            pres.setString(1, id);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs.next()) {
                int assignment_id = rs.getInt("assignment_id");
                String name = rs.getString("assignment_name");
                int course_id = rs.getInt("course_id");
                int user_id = rs.getInt("user_id");
                String desc = rs.getString("assignment_desc");
            	int score = rs.getInt("assignment_score");
            	
                as = new Assignment(assignment_id, name, course_id, user_id, desc, score);
            }

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
        	System.out.println("error from getByID()");
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }

        return as;
    }   
    
}
