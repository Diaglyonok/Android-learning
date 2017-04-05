package com.github.diaglyonok.chat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.format.DateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    private static int SIGN_IN_REQUEST_CODE = 1;
    private FirebaseListAdapter<Message> adapter;
    private RelativeLayout layout;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSend = (Button)findViewById(R.id.sendMess);
        layout = (RelativeLayout)findViewById(R.id.activity_main);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editMessage = (EditText)findViewById(R.id.editMessageText);
                FirebaseDatabase.getInstance().getReference().push().setValue(new Message(editMessage.getText().toString(),
                        FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                editMessage.setText("");
            }
        });

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),
                    SIGN_IN_REQUEST_CODE);
        }
        else{
            displayChat();
            
        }

    }

    private void displayChat() {
        ListView listMessages = (ListView)findViewById(R.id.listMessages);
        adapter = new FirebaseListAdapter<Message>(this, Message.class, R.layout.message, FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, Message model, int position) {
                TextView tvUser, tvTime, tvMessage;

                tvUser = (TextView)v.findViewById(R.id.tvUser);
                tvTime = (TextView)v.findViewById(R.id.tvTime);
                tvMessage = (TextView)v.findViewById(R.id.tvMessage);

                tvMessage.setText(model.getTextMessage());
                tvUser.setText(model.getAuthor());
                tvTime.setText(DateFormat.format("dd-MM-yyy (HH:mm:ss)", model.getTimeMessage()));
            }
        };

        listMessages.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_REQUEST_CODE){
            if (requestCode == 1)
            {
                Snackbar.make(layout, "Вход выполнен", Snackbar.LENGTH_SHORT).show();
                displayChat();
            }
            else{
                Toast.makeText(this, "Вход не выполнен", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_signout)
        {
            AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Snackbar.make(layout, "Выход выполнен",Snackbar.LENGTH_SHORT).show();
                            finish();
                        }
                    });
        }
        return true;
    }
}
