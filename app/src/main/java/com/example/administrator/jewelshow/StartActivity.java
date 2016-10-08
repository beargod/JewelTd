package com.example.administrator.jewelshow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.ExitApplication;


public class StartActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ExitApplication.getInstance().add(this);
    }
    public void onClick(View view){
        switch(view.getId()){
            case R.id.button_start:
                Intent intent = new Intent(StartActivity.this,GameActivity.class);
                startActivity(intent);
                break;
            case R.id.button_exit:finish();break;
        }
    }
}

