package com.example.naxi.bulanci;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;

public class SettingGameActivity extends Activity {


    SeekBar seekBarRed;
    SeekBar seekBarGreen;
    SeekBar seekBarBlue;

    ImageView bulanekView;

    EditText Name;
    EditText Time;
    EditText EnemyCount;

    SharedPreferences mySharedPref;
    SharedPreferences.Editor mySharedEditor;

    String map;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_game);

        map = getIntent().getStringExtra("mapName");

        seekBarRed = findViewById(R.id.seekBarRed);
        seekBarRed.setOnSeekBarChangeListener(SeekBarListener);

        seekBarGreen = findViewById(R.id.seekBarGreen);
        seekBarGreen.setOnSeekBarChangeListener(SeekBarListener);

        seekBarBlue = findViewById(R.id.seekBarBlue);
        seekBarBlue.setOnSeekBarChangeListener(SeekBarListener);

        bulanekView = findViewById(R.id.imageViewBulanek);
        Name = findViewById(R.id.editTextName);
        Time = findViewById(R.id.editTextTime);
        EnemyCount = findViewById(R.id.editTextEnemyCounter);

        mySharedPref = getSharedPreferences("lastSettings", this.MODE_PRIVATE);
        Time.setText(String.valueOf(mySharedPref.getInt("time", 120)));
        Name.setText(mySharedPref.getString("name", "Anonym"));
        EnemyCount.setText(String.valueOf(mySharedPref.getInt("enemyCount", 3)));

        seekBarRed.setProgress(mySharedPref.getInt("colorR", 0));
        seekBarGreen.setProgress(mySharedPref.getInt("colorG", 0));
        seekBarBlue.setProgress(mySharedPref.getInt("colorB", 0));

        SeekBarListener.onProgressChanged(seekBarBlue, 10, true);

    }

    public void startGameClick(View view)
    {
        Bundle pack = new Bundle();
        pack.putString("mapName",map);
        pack.putInt("colorR",seekBarRed.getProgress());
        pack.putInt("colorG",seekBarGreen.getProgress());
        pack.putInt("colorB",seekBarBlue.getProgress());

        int time = 0;
        try {
            time = Integer.parseInt(Time.getText().toString().replace(',', '.'));
        }catch(NumberFormatException e)
        {
            time = 30*60*2;
        }

        int enemyCount = 0;
        try {
            enemyCount = Integer.parseInt(EnemyCount.getText().toString().replace(',', '.'));
        }catch(NumberFormatException e)
        {
            enemyCount = 2;
        }

        pack.putInt("time",time);
        pack.putString("name", Name.getText().toString());
        pack.putInt("enemyCount",enemyCount);

        mySharedEditor = mySharedPref.edit();
        mySharedEditor.putInt("colorR", seekBarRed.getProgress());
        mySharedEditor.putInt("colorG", seekBarGreen.getProgress());
        mySharedEditor.putInt("colorB", seekBarBlue.getProgress());
        mySharedEditor.putInt("time", time);
        mySharedEditor.putInt("enemyCount", enemyCount);
        mySharedEditor.putString("name", Name.getText().toString());
        mySharedEditor.apply();



        Intent intent = new Intent(this,GameActivity.class);
        intent.putExtras(pack);
        startActivity(intent);
        finish();
    }


    SeekBar.OnSeekBarChangeListener SeekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            bulanekView.getDrawable().setColorFilter(Color.argb(255, seekBarRed.getProgress() ,seekBarGreen.getProgress(),seekBarBlue.getProgress()), PorterDuff.Mode.MULTIPLY);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };




}
