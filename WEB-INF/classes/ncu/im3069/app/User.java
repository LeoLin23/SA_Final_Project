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
     * ??????????????????
     *
     * @return the JSON object ??????SQL???????????????????????????????????????
     */
    public JSONObject update() {
        /** ????????????JSONObject?????????????????????????????? */
        JSONObject data = new JSONObject();
        
        /** ?????????????????????????????????????????? */
        if(this.id != 0) {
            /** ??????MemberHelper??????????????????????????????????????????????????? */
            data = uh.update(this);
        }
        
        return data;
    }
    
    /**
     * ??????????????????????????????
     *
     * @return the data ?????????????????????????????????????????????JSONObject?????????
     */
    public JSONObject getData() {
        /** ??????JSONObject????????????????????????????????????????????????*/ 
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