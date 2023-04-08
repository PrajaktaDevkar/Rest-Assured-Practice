
package Body;

import io.restassured.path.json.JsonPath;

public class ReusableMethod {
	
	public static JsonPath rawToJson(String response) {
		
		JsonPath js = new JsonPath(response);//It is class which take string as an input and produce Json as an output. It is parsing json
		return js;
	}

}
