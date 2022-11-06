package reysan.live.tv.Login;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import reysan.live.tv.JSONParser.JSONParser;
import reysan.live.tv.SharedPref.Settings;

import okhttp3.RequestBody;


 

public class LoadLogin extends AsyncTask<String, String, String> {

    private RequestBody requestBody;
    private LoginListener loginListener;
    private String user_id="", user_name="", success="0", message = "";

    public LoadLogin(LoginListener loginListener, RequestBody requestBody) {
        this.loginListener = loginListener;
        this.requestBody = requestBody;
    }

    @Override
    protected void onPreExecute() {
        loginListener.onStart();
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            String json = JSONParser.okhttpPost(Settings.SERVER_URL, requestBody);
            JSONObject mainJson = new JSONObject(json);
            JSONArray jsonArray = mainJson.getJSONArray(Settings.TAG_ROOT);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);

                success = c.getString(Settings.TAG_SUCCESS);
                if(success.equals("1")) {
                    user_id = c.getString("user_id");
                    user_name = c.getString("name");
                }
                message = c.getString(Settings.TAG_MSG);
            }
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        loginListener.onEnd(s, success, message, user_id, user_name);
        super.onPostExecute(s);
    }
}