package com.application.intentbrowsedialmap;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnMap,btnDial, btnBrowse;
    EditText activityEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMap = (Button)findViewById(R.id.btnMap);
        btnMap.setOnClickListener(this);
        btnDial = (Button)findViewById(R.id.btnDial);
        btnDial.setOnClickListener(this);
        btnBrowse = (Button)findViewById(R.id.btnBrowse);
        btnBrowse.setOnClickListener(this);

        activityEdit = (EditText) findViewById(R.id.activityEdit);
    }

    @Override
    public void onClick(View view) {

        Intent intent;

        switch (view.getId()) {
            case R.id.btnMap:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + activityEdit.getText().toString()));
                startActivity(intent);
                break;
            case R.id.btnDial:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + activityEdit.getText().toString()));
                startActivity(intent);
                break;
            case R.id.btnBrowse:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(activityEdit.getText().toString()));
                startActivity(intent);
                break;
        }

    }
}