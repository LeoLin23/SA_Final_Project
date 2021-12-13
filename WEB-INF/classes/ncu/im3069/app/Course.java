package ncu.im3069.app;

import org.json.JSONArray;
import org.json.JSONObject;

public class Course {

    private int id;
    private String name;
    private int price;
    private String desc;
    private String cover_path; // 封面圖片
    private String material_path; // 教材路徑


	public Course(String name, int price, String desc, String cover_path, String material_path) {
		this.name = name;
		this.price = price;
		this.desc = desc;
		this.cover_path = cover_path;
		this.material_path = material_path;
	}

    /**
     * 實例化（Instantiates）一個新的（new）Product 物件<br>
     * 採用多載（overload）方法進行，此建構子用於修改課程時
     *
     * @param id 課程編號
     * @param name 課程名稱
     * @param price 課程價格
     * @param image 課程圖片
     * @param describe 課程敘述
     */
	public Course(int id, String name, int price, String desc, String cover_path, String material_path) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.desc = desc;
		this.cover_path = cover_path;
		this.material_path = material_path;
	}

	// getters
	public int getID() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public int getPrice() {
		return this.price;
	}

	public String getDesc() {
		return this.desc;
	}

	public String getCover() {
		return this.cover_path;
	}
	
	public String getMaterial() {
		return this.material_path;
	}
	
    /**
     * 取得課程資訊
     *
     * @return JSONObject 回傳課程資訊
     */
	public JSONObject getData() {
        /** 透過JSONObject將該項課程所需之資料全部進行封裝*/
        JSONObject jso = new JSONObject();
        jso.put("id", getID());
        jso.put("name", getName());
        jso.put("price", getPrice());
        jso.put("desc", getDesc());
        jso.put("cover", getCover());
        jso.put("material", getMaterial());

        return jso;
    }
}
