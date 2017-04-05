package com.github.diaglyonok.testsnippetcontacts;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


        boolean name_not_founded = true;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone._ID, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
            startManagingCursor(cursor);

            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    if ((cursor.getString(2).equals("89811206081") || cursor.getString(2).equals("+79811206081")) && name_not_founded) {
                        Toast.makeText(this, cursor.getString(1), Toast.LENGTH_LONG).show();
                        name_not_founded = false;
                    }
                }
            }
        }
    }

