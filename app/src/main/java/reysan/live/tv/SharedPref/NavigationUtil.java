package reysan.live.tv.SharedPref;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

import reysan.live.tv.Activity.ExoPlayerActivity;
import reysan.live.tv.Activity.PlayerActivity;
import reysan.live.tv.Activity.WebviewActivity;
import reysan.live.tv.Listltem.Listltem;


 

public class NavigationUtil {

    public static void Video(@NonNull Activity activity, String video_type, int position, ArrayList<Listltem> arrayList) {
        Intent intent = new Intent(activity, PlayerActivity.class);
        Settings.arrayList.clear();
        Settings.position = position;
        Settings.arrayList.addAll(arrayList);
        ActivityCompat.startActivity(activity, intent, null);
    }

    public static void VideoReportActivity(@NonNull Activity activity, String video_type) {
            Intent intent = null;
            if (video_type.equals("Webview")) {
                intent = new Intent(activity, WebviewActivity.class);
            }else {
                intent = new Intent(activity, ExoPlayerActivity.class);
            }
            ActivityCompat.startActivity(activity, intent, null);
    }
}
