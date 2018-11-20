package com.example.naxi.bulanci;

import android.app.Activity;
import android.content.Intent;
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

    Paint paint;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_game);

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

    }

    public void startGameClick(View view)
    {
        Bundle pack = new Bundle();
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



        Intent intent = new Intent(this,GameActivity.class);
        intent.putExtras(pack);
        startActivity(intent);
        finish();
    }


    SeekBar.OnSeekBarChangeListener SeekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            bulanekView.getDrawable().setColorFilter(Color.argb(100, seekBarRed.getProgress() ,seekBarGreen.getProgress(),seekBarBlue.getProgress()), PorterDuff.Mode.SRC_ATOP);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };




}
