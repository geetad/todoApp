package com.astro.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;

public class TodoListActivity extends ActionBarActivity {

    private ArrayList<String> todoItems;
    public ArrayAdapter<String> todoAdapter;
    private ListView lvItems;
    private EditText etView;
    private Boolean isEditable = false;
    private int req_code=0;

    public final static String Id_Extra= "com.astro.todoapp._ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        etView = (EditText)findViewById(R.id.etView);
        lvItems = (ListView)findViewById(R.id.lvItems);
       //populateArrayItems();
        readItems();
        todoAdapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,todoItems);
        lvItems.setAdapter(todoAdapter);

        setupListViewListener();
        lvItems.setOnItemClickListener(onListClick);
    }

    private void readItems()
    {
      File filesDir = getFilesDir();
       File todoFile = new File(filesDir,"todo.txt");

       try{
           todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));

        }catch(java.io.IOException e)
        {
            todoItems=new ArrayList<>();
            e.printStackTrace();
        }
    }

    private void saveItems()
    {
        java.io.File filesDir = getFilesDir();
        java.io.File todoFile= new java.io.File(filesDir,"todo.txt");
        try
        {
            FileUtils.writeLines(todoFile, todoItems);

        }catch(java.io.IOException e)
        {
            e.printStackTrace();
        }
    }

    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener()
    {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            // When clicked, show a toast with the TextView text
            Intent myIntent = new Intent(view.getContext(), EditItemActivity.class);

            myIntent.putExtra(Id_Extra, todoItems.get((int) id));

            req_code=(int)id;
            startActivityForResult(myIntent, req_code);
        }

    };

    private void setupListViewListener()
    {
            lvItems.setOnItemLongClickListener(new OnItemLongClickListener()
            {
                public boolean onItemLongClick(AdapterView<?> adapter,View item,int pos,long id)
                {
                    todoItems.remove(pos);
                    todoAdapter.notifyDataSetChanged();
                    saveItems();
                    return true;
                }
            });
    }

    private void populateArrayItems() {

        todoItems = new ArrayList<String>();
        todoItems.add("Buy Milk");
        todoItems.add("Do Laundry");
        todoItems.add("Get NewsPaper");
        todoItems.add("Get Groceries");
    }

    private void makeEditable(Boolean isEditable)
    {
        etView.setFocusable(true);
        etView.setEnabled(true);
        etView.setClickable(true);
        etView.setFocusableInTouchMode(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo_list, menu);
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


    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode ==req_code){
            if (resultCode == RESULT_OK)
            {
                String strReturned = data.getStringExtra("item_Changed");
                Toast.makeText(this, strReturned,Toast.LENGTH_SHORT).show();

                todoAdapter.remove(todoAdapter.getItem(req_code));
                todoAdapter.insert(strReturned, req_code);
                todoAdapter.notifyDataSetChanged();
                saveItems();
            }
        }
    }

    public void addTodoItem(View view) {

        EditText txtNewItem = (EditText)findViewById(R.id.etView);
        todoAdapter.add(txtNewItem.getText().toString());
        saveItems();
        etView.setText("");
    }
}
