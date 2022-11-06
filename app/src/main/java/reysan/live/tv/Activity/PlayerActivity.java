package reysan.live.tv.Activity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatActivity;

import com.nemosofts.library.BlurImage;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import reysan.live.tv.DBHelper.DBHelper;
import reysan.live.tv.JSONParser.JSONParser;
import reysan.live.tv.Methods.Methods;
import nemosofts.live.tv.R;
import reysan.live.tv.SharedPref.NavigationUtil;
import reysan.live.tv.SharedPref.Settings;

/**
 * Company : Nemosofts
 * Detailed : Software Development Company in Sri Lanka
 * Developer : Thivakaran
 * Contact : thivakaran829@gmail.com
 * Contact : nemosofts@gmail.com
 * Website : https://nemosofts.com
 */

public class PlayerActivity extends AppCompatActivity{

    private DBHelper dbHelper;
    Methods methods;
    TextView titel, view;
    private ImageView imageView_fav, imageView;

    private ImageView imageViewBackground;
    private int BLUR_PRECENTAGE = 55;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Settings.Dark_Mode) {
            setAppTheme(R.style.AppTheme2);
        } else {
            setAppTheme(R.style.AppTheme);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        dbHelper = new DBHelper(this);
        methods = new Methods(this);

        LinearLayout adView = findViewById(R.id.adView);
        methods.showBannerAd(adView);

        titel = findViewById(R.id.titel);
        view = findViewById(R.id.view);
        imageView_fav = findViewById(R.id.imageView_fav_home);
        imageView = findViewById(R.id.player_ima2);
        imageViewBackground = findViewById(R.id.player_ima);

        LoadDaxta();
    }


    private void LoadDaxta() {
        String imageurl = methods.getImageThumbSize2(Settings.arrayList.get(Settings.position).getImageUrl());
        if(imageurl.equals("")) {
            imageurl = "null";
        }

        Picasso.get()
                .load(imageurl)
                .placeholder(R.color.news1)
                .into(imageView);

        try {
            Target target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    imageViewBackground.setImageBitmap(BlurImage.fastblur(bitmap, 1f, BLUR_PRECENTAGE));
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    imageViewBackground.setImageResource(Integer.parseInt(Settings.arrayList.get(Settings.position).getImageUrl()));
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };

            imageViewBackground.setTag(target);
            Picasso.get()
                    .load(Settings.arrayList.get(Settings.position).getImageUrl())
                    .placeholder(R.color.news1)
                    .into(target);

        } catch (Exception e) {
            e.printStackTrace();
        }

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {

                String json = JSONParser.okhttpPost(Settings.SERVER_URL, methods.getAPIRequest(Settings.METHOD_TV, 0, "", Settings.arrayList.get(Settings.position).getId(), "", "", "", "", "", "","","","","","","","", null));
                return null;
            }
        }.execute();

        titel.setText(Settings.arrayList.get(Settings.position).getName());
        view.setText(methods.format(Double.parseDouble((String)Settings.arrayList.get(Settings.position).getTotal_views()))+" views");

        Boolean isFav = dbHelper.checkFav(Settings.arrayList.get(Settings.position).getId());
        dbHelper.addToRecent(Settings.arrayList.get(Settings.position));

        if (isFav) {
            imageView_fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_bookmark));
        } else {
            imageView_fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_bookmark2));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationUtil.VideoReportActivity(PlayerActivity.this, Settings.arrayList.get(Settings.position).getVideo_type());
            }
        });

        imageView_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dbHelper.addORremoveFav(Settings.arrayList.get(Settings.position))) {
                    imageView_fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_bookmark));
                    Toast.makeText(PlayerActivity.this, getString(R.string.add_to_fav), Toast.LENGTH_SHORT).show();
                } else {
                    imageView_fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_bookmark2));
                    Toast.makeText(PlayerActivity.this, getString(R.string.remove_from_fav), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    private void setAppTheme(@StyleRes int style) {
        setTheme(style);
    }
}
