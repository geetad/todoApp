package com.astro.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class EditItemActivity extends ActionBarActivity {

    String passedItem = null;
    private EditText tvMultiline = null;
    private Button btnSave = null;
    public final static String item_Changed= "com.astro.todoapp._ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        passedItem = getIntent().getStringExtra(TodoListActivity.Id_Extra);
        tvMultiline = (EditText)findViewById(R.id.editLstItem);
        tvMultiline.setText(passedItem);

        tvMultiline.setFocusableInTouchMode(true);
        tvMultiline.requestFocus();

        btnSave = (Button) findViewById(R.id.btnSave);
    }

    public void updateItem(View v)
    {
        tvMultiline.setEnabled(true);
        //tvMultiline.setFocusable(true);
    }

    public void btnOnClick(View v)
    {
        String ret = tvMultiline.getText().toString();
        //Toast.makeText(this, ret, Toast.LENGTH_SHORT).show();

         Intent i = new Intent();
         i.putExtra("item_Changed",tvMultiline.getText().toString());
         setResult(RESULT_OK,i);

        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
