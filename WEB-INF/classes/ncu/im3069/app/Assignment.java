package ncu.im3069.app;

import org.json.JSONArray;
import org.json.JSONObject;

public class Assignment {

	private AssignmentHelper ah =  AssignmentHelper.getHelper();
	
    private int id;
    private	int course_id;
    private String name;
	private String desc = "";

	// constructor for adding new assignment
    public Assignment(String name, int course_id, String desc) {
		this.name = name;
		this.course_id = course_id;
		this.desc = desc;
	}

	public Assignment(int id, String name, int course_id, int user_id, String desc, int score) {
		this.id = id;
		this.name = name;
		this.course_id = course_id;
		this.desc = desc;
	}

	public Assignment(int id, int course_id, String name) {
		this.id = id;
		this.name = name;
		this.course_id = course_id;
	}

	public Assignment(String name, int course_id) {
		this.name = name;
		this.course_id = course_id;
	}

	public Assignment(int id, int course_id, String name, String desc) {
		this.id = id;
		this.course_id = course_id;
		this.name = name;
		this.desc = desc;
	}

	public int getID() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}
	
	public int getCourseID() {
		return course_id;
	}

	public String getDesc() {
		return desc;
	}
	
	public JSONObject getData() {
        JSONObject jso = new JSONObject();
        jso.put("id", getID());
        jso.put("name", getName());
        jso.put("course_id", getCourseID());       
        jso.put("desc", getDesc());
        
        return jso;
    }


	
}
