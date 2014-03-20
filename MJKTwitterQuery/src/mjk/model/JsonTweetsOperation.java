package mjk.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JsonTweetsOperation {
	public String JsonGenerate(HashMap<String, Object> hashmap){
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create(); 
		String json = gson.toJson(hashmap);
		return json;
	}
	public void JsonToClass(String jsonstring){
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create(); 
		 Map<String, Object> retMap = gson.fromJson(jsonstring,new TypeToken<Map<String, List<Object>>>() {
			 }.getType()); 
		 int index=0;
		 for (String key : retMap.keySet()) {  
	            System.out.println("key:" + key + " values:" + retMap.get(key));  
	                List<TweeterModel> userList = (List<TweeterModel>) retMap.get(key);  
					userList.get(index);
	                System.out.println(userList);  
	                index++;
	        }  
	}
}
