package com.example.naxi.bulanci;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ScoreActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);


        TextView bulanekName = findViewById(R.id.textViewScoreBulanekName);
        TextView kills = findViewById(R.id.textViewScoreKills);
        TextView deaths = findViewById(R.id.textViewScoreDeaths);
        TextView score = findViewById(R.id.textViewScore);
        ImageView bulanekImage = findViewById(R.id.imageViewScoreBulanek);

        Bundle pack = getIntent().getExtras();

        bulanekName.setText(pack.getString("name"));
        kills.setText("Kills:   "+pack.getInt("kills"));
        deaths.setText("Deaths:  "+pack.getInt("deaths"));
        score.setText("Score:   "+(pack.getInt("kills")-pack.getInt("deaths")));

        bulanekImage.getDrawable().setColorFilter(Color.argb(255,pack.getInt("colorR", 0),pack.getInt("colorG", 0),pack.getInt("colorB", 0)), PorterDuff.Mode.MULTIPLY);

    }

    public void Next(View view) {
        finish();
    }
}
