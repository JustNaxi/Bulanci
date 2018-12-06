package com.example.naxi.bulanci;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.File;

public class MainActivity extends Activity {

    String maps[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
            //ask for permission
            ActivityCompat.requestPermissions(MainActivity.this ,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);


        }

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.VIBRATE) == PackageManager.PERMISSION_DENIED){
            //ask for permission
            ActivityCompat.requestPermissions(MainActivity.this ,new String[]{Manifest.permission.VIBRATE}, 1);


        }

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

                        Intent intent = new Intent(MainActivity.this,SettingGameActivity.class);
                        intent.putExtra("mapName", map);
                        startActivity(intent);
                    }
                }
        );

    }

    public void quitGame(View view) {
        finish();
    }
}
