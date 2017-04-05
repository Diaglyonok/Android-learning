package com.application.adaptersdb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Dialog extends AppCompatActivity implements View.OnClickListener {

    EditText editName, editEmail;
    Button btnGo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_dialog);
        } catch (Exception e) {
            e.printStackTrace();
        }

        editName = (EditText) findViewById(R.id.editName);
        editEmail = (EditText) findViewById(R.id.editEmail);

        btnGo = (Button) findViewById(R.id.btnGo);
        btnGo.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.putExtra("name", editName.getText().toString());
        intent.putExtra("email", editEmail.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}