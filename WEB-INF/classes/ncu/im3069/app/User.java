package ncu.im3069.app;

import org.json.JSONObject;

import java.sql.Timestamp;
import java.time.LocalDateTime;



public class User {
    private int id;
    private String email;
    private String name;
    private String password;
    private Timestamp register_date;
    
//    we actually don't need this
//    private ArrayList<Course> course_list;
    
    private int identity = 0;

    private UserHelper uh =  UserHelper.getHelper();

    public User(String email, String name, String password, int identity) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.identity = identity;
        
        // record the register date and time
        this.register_date = Timestamp.valueOf(LocalDateTime.now());
        
        update();
    }

    // constructor for update
    public User(int id, String email, String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        
        update();
    }
    
    // constructor for update
    public User(int id, String email, String password, String name, int identity) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.identity = identity;

        update();
    }
   
    
    public User(int id, String email, String password, String name, Timestamp register_date) {
    	this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.identity = 0;
        this.register_date = register_date;
	}

	public User(int id, String email, String password, String name, int identity, Timestamp register_date) {
		this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.identity = identity;
        this.register_date = register_date;
	}

	// getters
    public int getID() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }
    
    public int getIdentity() {
        return this.identity;
    }
    
    public Timestamp getRegisterDate() {
        return this.register_date;
    }
    
    /**
     * 更新會員資料
     *
     * @return the JSON object 回傳SQL更新之結果與相關封裝之資料
     */
    public JSONObject update() {
        /** 新建一個JSONObject用以儲存更新後之資料 */
        JSONObject data = new JSONObject();
        
        /** 檢查該名會員是否已經在資料庫 */
        if(this.id != 0) {
            /** 透過MemberHelper物件，更新目前之會員資料置資料庫中 */
            data = uh.update(this);
        }
        
        return data;
    }
    
    /**
     * 取得該名會員所有資料
     *
     * @return the data 取得該名會員之所有資料並封裝於JSONObject物件內
     */
    public JSONObject getData() {
        /** 透過JSONObject將該名會員所需之資料全部進行封裝*/ 
        JSONObject jso = new JSONObject();
        jso.put("id", getID());
        jso.put("name", getName());
        jso.put("email", getEmail());
        jso.put("password", getPassword());
        jso.put("identity", getIdentity());
        jso.put("register_date", getRegisterDate());
        
        return jso;
    }
    
}