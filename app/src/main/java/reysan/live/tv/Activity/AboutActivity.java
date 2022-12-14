package reysan.live.tv.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import reysan.live.tv.R;
import reysan.live.tv.SharedPref.Settings;

 

public class AboutActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView  company, email, website, contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Settings.Dark_Mode) {
            setTheme(R.style.AppTheme2);
        } else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        company = (TextView) findViewById(R.id.company);
        email = (TextView) findViewById(R.id.email);
        website = (TextView) findViewById(R.id.website);
        contact = (TextView) findViewById(R.id.contact);

        company.setText(Settings.company);
        email.setText(Settings.email);
        website.setText(Settings.website);
        contact.setText(Settings.contact);


    }


}