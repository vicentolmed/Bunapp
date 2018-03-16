package com.hooki.bunapp.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hooki.bunapp.R;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void goDirectory(View v) {
        Intent intent = new Intent(this, DirectoryActivity.class);
        startActivity(intent);
    }

    public void goSocial(View v) {
        Intent intent = new Intent(this, SocialActivity.class);
        startActivity(intent);
    }
}
