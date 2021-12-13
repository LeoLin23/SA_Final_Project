package ncu.im3069.app;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import org.json.*;

public class Order {
    private int id;
    private String username;
    // a list of ordered item
    private ArrayList<OrderItem> list = new ArrayList<OrderItem>();

    /** create，訂單創建時間 */
    private Timestamp create;
    private OrderItemHelper oih = OrderItemHelper.getHelper();

    public Order(String username) {
        this.username = username;
        this.create = Timestamp.valueOf(LocalDateTime.now());
    }
    
    public Order(int id, String username, Timestamp create) {
        this.id = id;
    	this.username = username;
        this.create = create;
    }

    /**
     * add OrderItem by course
     */
    public void addOrderItem(Course c) {
        this.list.add(new OrderItem(c));
    }

    /**
     * add OrderItem by orderitem
     */
    public void addOrderItem(OrderItem oi) {
        this.list.add(oi);
    }

    /**
     * 設定訂單編號
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 取得訂單編號
     *
     * @return int 回傳訂單編號
     */
    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }
    
    public Timestamp getCreateTime() {
        return this.create;
    }

    // get the list of ordered courses
    public ArrayList<OrderItem> getOrderItem() {
        return this.list;
    }

    /**
     * 從 DB 中取得訂單產品
     */
    private void getOrderCourseFromDB() {
        ArrayList<OrderItem> data = oih.getOrderCourseByOrderId(this.id);
        this.list = data;
    }

    /**
     * 取得訂單基本資料
     *
     * @return JSONObject 取得訂單基本資料
     */
    public JSONObject getOrderData() {
        JSONObject jso = new JSONObject();
        jso.put("order_id", getId());
        jso.put("user_name", getUsername());
        jso.put("order_date", getCreateTime());

        return jso;
    }

    /**
     * 取得訂單產品資料
     *
     * @return JSONArray 取得訂單產品資料
     */
    public JSONArray getOrderItemData() {
        JSONArray result = new JSONArray();

        for(int i=0 ; i < this.list.size() ; i++) {
            result.put(this.list.get(i).getData());
        }

        return result;
    }

    /**
     * 取得訂單所有資訊
     *
     * @return JSONObject 取得訂單所有資訊
     */
    public JSONObject getOrderAllInfo() {
        JSONObject jso = new JSONObject();
        jso.put("order_info", getOrderData());
        jso.put("course_info", getOrderItemData());

        return jso;
    }

    /**
     * 設定訂單產品編號
     */
    public void setOrderItemId(JSONArray data) {
        for(int i=0 ; i < this.list.size() ; i++) {
            this.list.get(i).setId(data.getInt(i));
        }
    }

}
