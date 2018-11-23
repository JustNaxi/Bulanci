package com.example.naxi.bulanci;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.File;

public class MapListActivity extends Activity
{

    String[] maps;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_list);




        String path = Environment.getExternalStorageDirectory().toString()+"/bulanci";
        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();
        Log.d("Files", "Size: "+ files.length);

        maps = new String[files.length];
        for (int i = 0; i < files.length; i++)
        {
            maps[i]=files[i].getName();
        }

        ListAdapter myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, maps);
        ListView mapListView = findViewById(R.id.listViewMaps);
        mapListView.setAdapter(myAdapter);

        mapListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String map = String.valueOf(parent.getItemAtPosition(position));

                        Intent intent = new Intent(MapListActivity.this,SettingGameActivity.class);
                        intent.putExtra("mapName", map);
                        startActivity(intent);
                        finish();
                    }
                }
        );
    }
}
