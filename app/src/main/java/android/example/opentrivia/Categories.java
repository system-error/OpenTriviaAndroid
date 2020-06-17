package android.example.opentrivia;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class Categories {
    private int id;
    private String name;
    private static ArrayList<Categories> categories = new ArrayList<>();

    public Categories(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static ArrayList<Categories> getTheCategories(String response){
        categories.add(new Categories(-1,"Choose Category"));
        categories.add(new Categories(0,"Random Questions"));
        try {
            String inputLine;
            if ((inputLine = response) != null) {
                JSONObject json = new JSONObject(inputLine);
                JSONArray jsonarray = json.getJSONArray("trivia_categories");
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                   categories.add(new Categories((int) jsonobject.get("id"),(String) jsonobject.get("name")));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return categories;
    }
}
