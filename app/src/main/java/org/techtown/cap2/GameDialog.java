package org.techtown.cap2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameDialog extends AppCompatActivity {

    Button yesBtn,noBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_dialog);

        yesBtn = findViewById(R.id.yesBtn);

//        yesBtn.setOnClickListener(view -> {
//            Intent intent = new Intent(this,Drink2.class);
//            startActivity(intent);
//        });

        noBtn = findViewById(R.id.noBtn);

//        noBtn.setOnClickListener(view -> {
//            Intent iintent = new Intent(this,GamePage.class);
//            startActivity(iintent);
//        });

    }
}