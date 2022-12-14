package reysan.live.tv.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import reysan.live.tv.Adapter.Adapter_tv_Favourite;
import reysan.live.tv.DBHelper.DBHelper;

import reysan.live.tv.Listltem.Listltem;
import reysan.live.tv.Methods.Methods;
import reysan.live.tv.R;
import reysan.live.tv.SharedPref.NavigationUtil;
import reysan.live.tv.SharedPref.Settings;
import reysan.live.tv.interfaces.InterAdListener;
import reysan.live.tv.interfaces.RecyclerViewClickListener;


 

public class FavouriteActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Adapter_tv_Favourite adapter;
    ArrayList<Listltem> arrayList;
    Methods methods;
    GridLayoutManager grid;
    FrameLayout progressBar;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Settings.Dark_Mode ) {
            setTheme(R.style.AppTheme2);
        } else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Favourite");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        dbHelper = new DBHelper(this);
        methods = new Methods(this);

        methods = new Methods(this, new InterAdListener() {
            @Override
            public void onClick(int position, String type) {
                NavigationUtil.Video(FavouriteActivity.this, arrayList.get(position).getVideo_type(), position, arrayList);
            }
        });


        arrayList = new ArrayList<>();
        arrayList.addAll(dbHelper.loadFavData());

        progressBar = findViewById(R.id.load_video);

        recyclerView = findViewById(R.id.tv);
        recyclerView.setHasFixedSize(true);
        switch (Settings.grid){
            case 0: grid = new GridLayoutManager(FavouriteActivity.this, 2);
                break;
            case 1: grid = new GridLayoutManager(FavouriteActivity.this, 3);
                break;
            case 2: grid = new GridLayoutManager(FavouriteActivity.this, 4);
                break;
            default: grid = new GridLayoutManager(FavouriteActivity.this, 3);
        }
        grid.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.isHeader(position) ? grid.getSpanCount() : 1;
            }
        });
        recyclerView.setLayoutManager(grid);

        adapter = new Adapter_tv_Favourite(FavouriteActivity.this, arrayList , new RecyclerViewClickListener(){
            @Override
            public void onClick(int position) {
                methods.showInter(position, "");
            }
        });
        recyclerView.setAdapter(adapter);

        progressBar.setVisibility(View.INVISIBLE);

        LinearLayout adView = findViewById(R.id.adView);
        methods.showBannerAd(adView);
    }


}
