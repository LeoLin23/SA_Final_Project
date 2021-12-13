package ncu.im3069.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

import ncu.im3069.app.Assignment;
import ncu.im3069.app.AssignmentHelper;
import ncu.im3069.app.CourseUserHelper;
import ncu.im3069.app.User;
import ncu.im3069.app.UserAssignment;
import ncu.im3069.app.UserAssignmentHelper;
import ncu.im3069.tools.JsonReader;


@WebServlet("/api/assignment.do")
public class AssignmentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private AssignmentHelper ah =  AssignmentHelper.getHelper();
	private CourseUserHelper cuh = CourseUserHelper.getHelper();
	private UserAssignmentHelper uah = UserAssignmentHelper.getHelper();
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssignmentController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        /** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */
        String assignment_id = jsr.getParameter("assignment_id");
        String user_id = jsr.getParameter("user_id");        
        
        System.out.println("XXXXXXXXXXXXXX " + assignment_id + "     XXXXXXXXXXXXXXXX");
        
        String course_id = jsr.getParameter("course_id");
        
        System.out.println("XXXXXXXXXXXXXX    " + course_id + "    XXXXXXXXXXXXXXXX");
        
        // when would I need you?
        String identity = jsr.getParameter("identity");
        
        System.out.println("XXXXXXXXXXXXXX      " + identity + "     XXXXXXXXXXXXXXXX");
        
        
        JSONObject resp = new JSONObject();
        JSONObject query = new JSONObject();

    	if (assignment_id.isEmpty()) {
    		query = ah.getAllByCourseId(course_id);        	        		
    	} else if ("1".equals(identity)) {
    		query = uah.getJsoById(assignment_id, Integer.parseInt(identity), user_id);
    	} else {	
    		boolean submitted = uah.checkSub(assignment_id, user_id);
    		if (submitted) {
    			query = uah.getJsoById(assignment_id, Integer.parseInt(identity), user_id);    			
    		} else {
    			System.out.println("Im here");
    			query = ah.getJsoById(assignment_id);    			
    		}
    	}

        resp.put("status", "200");
        resp.put("message", "assignment取得成功");
        resp.put("response", query);

        jsr.response(resp, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        int course_id = jso.getInt("course_id");
        String name = jso.getString("name");
        String desc = jso.getString("desc");
        
        // insert into Assignment table
        Assignment a = new Assignment(name, course_id, desc);
        JSONObject o = ah.create(a);
        
        JSONObject resp = new JSONObject();
        
        resp.put("status", "200");

        jsr.response(resp, response);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        int assignment_id = jso.getInt("assignment_id");
        int user_id = jso.getInt("user_id");       
        
        /** 透過Member物件的update()方法至資料庫更新該名會員資料，回傳之資料為JSONObject物件 */
        JSONObject data = uah.submit(assignment_id, user_id);
        
        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "成功! 更新會員資料...");
        resp.put("response", data);
        
        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
        jsr.response(resp, response);
        
        
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        String id = jso.getString("assignment_id");
        
        /** 透過MemberHelper物件的deleteByID()方法至資料庫刪除該名會員，回傳之資料為JSONObject物件 */
        JSONObject query = ah.deleteById(id);
        
        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "作業刪除成功！");
        resp.put("response", query);

        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
        jsr.response(resp, response);
	}

}
