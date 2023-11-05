package org.techtown.cap2.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.techtown.cap2.BoomGameActivity;
import org.techtown.cap2.R;

public class BoomGame_setting_dialog extends AppCompatActivity {
    private
    ImageButton minusButton, plusButton,Button4;
    private TextView textView;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boom_game_setting_dialog);



    }
}