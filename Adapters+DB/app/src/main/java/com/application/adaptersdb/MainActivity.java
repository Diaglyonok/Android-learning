package com.application.adaptersdb;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Cursor cursor;
    DB myDB;
    SimpleCursorAdapter adapter;

    ListView myList;
    MenuItem menuItemClearDatabase;

    String tempName, tempEmail;

    Button btnAdd;

    private static final int MI_DELETE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DB(this);
        myDB.open();


        cursor = myDB.getAllData();
        startManagingCursor(cursor);

        String from[] = new String[] {DB.COLUMN_NAME, DB.COLUMN_EMAIL};
        int to[] = new int[] {R.id.tvName, R.id.tvEmail};

        adapter = new SimpleCursorAdapter(this, R.layout.listitem, cursor, from, to);

        myList = (ListView)findViewById(R.id.listView);
        myList.setAdapter(adapter);

        registerForContextMenu(myList);

        btnAdd = (Button)findViewById(R.id.addBtn);
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addBtn:
                Intent intent = new Intent(this, com.application.adaptersdb.Dialog.class);
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            tempName = data.getStringExtra("name");
            tempEmail = data.getStringExtra("email");

            myDB.addRec(tempName, tempEmail);
            cursor.requery();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo MenuInfo)
    {
        super.onCreateContextMenu(menu, view, MenuInfo);
        menu.add(0, MI_DELETE, 0, R.string.delete);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getItemId() == MI_DELETE){
            AdapterView.AdapterContextMenuInfo acmi= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            myDB.delRec(acmi.id);
            cursor.requery();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menuItemClearDatabase = menu.findItem(R.id.menuItemClearDatabase);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuItemClearDatabase:
                myDB.delAllRec();
                cursor.requery();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        myDB.close();
        super.onDestroy();
    }
}
