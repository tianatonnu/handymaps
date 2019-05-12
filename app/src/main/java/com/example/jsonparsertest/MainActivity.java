package com.example.jsonparsertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    String[] cpCourses_arr;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            cpCourses_arr = JSONParser.getCourses();

            //Setting the ListView object with the ListView element in activity_main.xml
            lv =(ListView) findViewById(R.id.listView);

            //Add the values from the String Array to the layout type
            ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cpCourses_arr);
            lv.setAdapter(adapter);

            Log.v("Success: ", "Completed list adapter");

    }

}
