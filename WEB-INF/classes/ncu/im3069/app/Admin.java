package ncu.im3069.app;

import org.json.JSONObject;


public class Admin extends User{
    
    private UserHelper uh =  UserHelper.getHelper();
	
    // constructor for create
    public Admin(String email, String name, String password) {
        super(email, name, password, 1);
        update();
    }

    // constructor for edit
    public Admin(int id, String email, String password, String name) {
    	super(id, email, name, password, 1);
        update();
    }
    
    
    public int getID() {
        return super.getID();
    }

    public String getEmail() {
    	return super.getEmail();
    }
    
    public String getName() {
    	return super.getName();
    }

    public String getPassword() {
    	return super.getPassword();
    }
    
    public int getIdentity() {
    	return super.getIdentity();
    }
 
    // update admin profile
    public JSONObject update() {
        /** 新建一個JSONObject用以儲存更新後之資料 */
        JSONObject data = new JSONObject();

        /** 檢查該名會員是否已經在資料庫 */
        if(this.getID() != 0) {
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
        
        return jso;
    }
}