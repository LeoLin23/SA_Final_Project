package ncu.im3069.app;

import org.json.*;

public class UserAssignment {

    private int id;
    private int user_id;
    private int assignment_id;
    private int score;
    
    private static AssignmentHelper ah;
    
    public static AssignmentHelper getHelper() {
        /** Singleton檢查是否已經有ProductHelper物件，若無則new一個，若有則直接回傳 */
        if(ah == null) ah = new AssignmentHelper();
        
        return ah;
    }
    
	public UserAssignment(int id, int assignment_id, int user_id, int score) {
		this.id = id;
		this.assignment_id = assignment_id;
		this.user_id = user_id;
		this.score = score;
	}
	

	public UserAssignment(int assignment_id, int user_id, int score) {
		this.assignment_id = assignment_id;
		this.user_id = user_id;
		this.score = score;
	}
	
	public UserAssignment(int assignment_id, int user_id) {
		this.assignment_id = assignment_id;
		this.user_id = user_id;
	}

	public int getId() {
		return id;
	}
	
	public int getAssignmentId() {
		return assignment_id;
	}
	
	public void calcScore() {
		this.score = (int) (Math.random()*40) + 60;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getUserId() {
		return user_id;
	}
	
	public JSONObject getData() {
		JSONObject jso = new JSONObject();
        jso.put("id", getId());
        jso.put("assignment_id", getAssignmentId());
        
        System.out.println("assignment_id" + getAssignmentId());
        
        jso.put("user_id", getUserId());
        jso.put("score", getScore());
        
        if(ah == null) ah = new AssignmentHelper();
        jso.put("name", ah.getNameById(getAssignmentId()));
        jso.put("desc", ah.getDescById(getAssignmentId()));
        
        return jso;
		
	}

}
