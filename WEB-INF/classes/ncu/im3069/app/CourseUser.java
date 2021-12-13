package ncu.im3069.app;

import org.json.*;

public class CourseUser {

    private int id;
	private int course_id;
    private int user_id;
    
	public CourseUser(int id, int course_id, int user_id) {
		this.id = id;
		this.course_id = course_id;
		this.user_id = user_id;
	}

	public int getId() {
		return id;
	}
	
	public int getCourse_id() {
		return course_id;
	}
	
	public int getUser_id() {
		return user_id;
	}

}
