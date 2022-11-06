package reysan.live.tv.asyncTask;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import reysan.live.tv.JSONParser.JSONParser;
import reysan.live.tv.interfaces.CategoryListener;
import reysan.live.tv.Listltem.Listltem_Category;
import reysan.live.tv.SharedPref.Settings;
import okhttp3.RequestBody;


 

public class LoadCategory extends AsyncTask<String, String, String> {

    private CategoryListener categoryListener;
    private ArrayList<Listltem_Category> arrayList;
    private RequestBody requestBody;

    public LoadCategory(CategoryListener categoryListener, RequestBody requestBody) {
        this.categoryListener = categoryListener;
        arrayList = new ArrayList<>();
        this.requestBody = requestBody;
    }

    @Override
    protected void onPreExecute() {
        categoryListener.onStart();
        super.onPreExecute();
    }

    @Override
    protected  String doInBackground(String... strings)  {
        String json = JSONParser.okhttpPost(Settings.SERVER_URL, requestBody);

        try {
            JSONObject jOb = new JSONObject(json);
            JSONArray jsonArray = jOb.getJSONArray(Settings.TAG_ROOT);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject objJson = jsonArray.getJSONObject(i);

                Listltem_Category item = new Listltem_Category(
                        objJson.getString("cid"),
                        objJson.getString("category_name"),
                        objJson.getString("category_image"),
                        objJson.getString("category_image_thumb")
                );
                arrayList.add(item);
            }
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }


    }

    @Override
    protected void onPostExecute(String s) {
        categoryListener.onEnd(String.valueOf(s),arrayList);
        super.onPostExecute(s);
    }

}

