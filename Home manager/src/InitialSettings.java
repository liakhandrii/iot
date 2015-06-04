import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.json.JSONObject;

public class InitialSettings {

	public static JSONObject makeJson(String[] keys, String[] values) {
		JSONObject obj = new JSONObject();
		for (int i = 0; i < keys.length; i++) {
			obj.put(keys[i], values[i]);
		}
		return obj;
	}

	public static boolean writeJson(JSONObject obj, String drive) {
		boolean res = true;
		try (PrintWriter writer = new PrintWriter(drive + "settings.json",
				"UTF-8");) {

			writer.println(obj);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			res = false;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			res = false;
		}

		return res;
	}

//	public static void main(String[] args) {
//		String[] keys = { "server", "ssid", "passwifi", "homename", "passhome" };
//		String[] values = { "1", "2", "3", "4", "5" };
//		writeJson(makeJson(keys, values), "D:\\");
//
//	}
}
