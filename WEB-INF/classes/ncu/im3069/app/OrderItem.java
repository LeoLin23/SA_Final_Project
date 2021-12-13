package ncu.im3069.app;

import org.json.JSONObject;

public class OrderItem {
	
    private int id; // order item id
    private int order_id;
    private Course c;
    private int subtotal;

    /** ph，CourseHelper 之物件與 OrderItem 相關之資料庫方法（Sigleton） */
    private CourseHelper ch =  CourseHelper.getHelper();

    // constructor
    public OrderItem(Course c) {
        this.c = c;
        this.subtotal = this.c.getPrice();
    }

    //
    public OrderItem(int id, int order_id, int course_id, int subtotal) {
    	this.id = id;
    	this.order_id = order_id;
    	this.c = ch.getCourseByID(String.valueOf(course_id));
    	this.subtotal = subtotal;
    			
    
    }

    /**
     * 從 DB 中取得產品
     */
    private void getCourseFromDB(int course_id) {
        String id = String.valueOf(course_id);
        this.c = ch.getCourseByID(id);
    }

    public Course getCourse() {
        return this.c;
    }

    // set order_item id
    public void setId(int id) {
        this.id = id;
    }

    // get order_item id
    public int getId() {
        return this.id;
    }

    // subtotal == course.price
    public int getSubTotal() {
        return this.subtotal;
    }


    /**
     * 取得產品細項資料
     *
     * @return JSONObject 回傳產品細項資料
     */
    public JSONObject getData() {
        JSONObject data = new JSONObject();
        data.put("id", getId());
        data.put("course", getCourse().getData());
        data.put("subtotal", getSubTotal());

        return data;
    }
}
