package com.applet.jlf.Activity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.applet.jlf.Adapter.SearchAdapter;
import com.applet.jlf.R;
import com.applet.jlf.Service.DatabaseHelper;
import com.applet.jlf.Service.DatabaseMethods;

import java.io.IOException;

public class SearchPage extends AppCompatActivity {

    private Toolbar toolbar;

    private EditText et_search;
    private ImageButton bt_clear;
    DatabaseMethods myDbMethods;
    DatabaseHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        myDbMethods = new DatabaseMethods();

        //Creating Database Instance
        myDbHelper = new DatabaseHelper(SearchPage.this);
        try {
            //Creating Database
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            //Opening Database
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }

        Intent intent = getIntent();
        String context = intent.getStringExtra("context");

        Log.i("Context",context);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerSearchPage);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchPage.this));

        if(context.equals("route")){
            recyclerView.setAdapter(new SearchAdapter(myDbMethods.getRouteList(myDbHelper),SearchPage.this));
        }

        if(context.equals("stop")){
            recyclerView.setAdapter(new SearchAdapter(myDbMethods.getStopList(myDbHelper,"complete_data"),SearchPage.this));
        }

        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initComponent() {
        bt_clear = (ImageButton) findViewById(R.id.bt_clear);
        bt_clear.setVisibility(View.GONE);

        et_search = (EditText) findViewById(R.id.et_search);
        TextWatcher textWatcher = null;
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().trim().length() == 0) {
                    bt_clear.setVisibility(View.INVISIBLE);
                } else {
                    bt_clear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Clear text Button
        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_search.setText("");
            }
        });
    }

    public void sendPosition(int position){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("position",position);
        setResult(RESULT_OK,returnIntent);
        Toast.makeText(SearchPage.this," "+position,Toast.LENGTH_LONG).show();
        finish();
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}
