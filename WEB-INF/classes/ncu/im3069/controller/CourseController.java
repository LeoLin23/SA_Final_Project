package ncu.im3069.controller;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

import ncu.im3069.app.Course;
import ncu.im3069.app.CourseHelper;
import ncu.im3069.app.User;
import ncu.im3069.tools.JsonReader;

@WebServlet("/api/course.do")
public class CourseController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CourseHelper ch =  CourseHelper.getHelper();

    public CourseController() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        /** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */
        String id = jsr.getParameter("id");
        System.out.println(id);
        
        // if id is empty, return all of the courses
        // else if id exists, return the course by its id
        if (id.isEmpty()) {
            /** 透過MemberHelper物件之getAll()方法取回所有會員之資料，回傳之資料為JSONObject物件 */
            JSONObject query = ch.getAll();
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "listing all courses...");
            resp.put("response", query);
    
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }
        else {
            /** 透過CourseHelper物件的getByID()方法自資料庫取回該課程之資料，回傳之資料為JSONObject物件 */
            JSONObject query = ch.getByID(id);
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "get course by id success");
            resp.put("response", query);
    
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */        
        String name = jso.getString("name");
        
        System.out.println(name);
        
        String price = jso.getString("price");
        String desc = jso.getString("desc");
        String cover = jso.getString("cover");
        String material = jso.getString("material");
        
        int int_price = Integer.parseInt(price);
        Course c = new Course(name, int_price, desc, cover, material);
        
        /** 後端檢查是否有欄位為空值，若有則回傳錯誤訊息 */
        if(name.isEmpty() || price.isEmpty() || desc.isEmpty() || cover.isEmpty() || material.isEmpty()) {
            /** 以字串組出JSON格式之資料 */
            String resp = "{\"status\": \'400\', \"message\": \'欄位不能有空值\', \'response\': \'\'}";
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.response(resp, response);
        } else {
            /** 透過MemberHelper物件的create()方法新建一個會員至資料庫 */
            JSONObject data = ch.create(c);
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "新增課程成功");
            resp.put("response", data);
            
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }
		
	}

}
