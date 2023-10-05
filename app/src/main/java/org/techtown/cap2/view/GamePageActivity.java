package org.techtown.cap2.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import org.techtown.cap2.BluetoothThread;
import org.techtown.cap2.BoomGameActivity;
import org.techtown.cap2.R;

import java.util.Random;

public class GamePageActivity extends AppCompatActivity {

    Button rullet_button, son_byung_ho_button, button3, back;
    private BluetoothThread bluetoothThread;
    private String message1,message2,message3;
    Context context;

    public GamePageActivity() {
        // BluetoothThread 인스턴스를 가져옴
        this.context = context;
        bluetoothThread = BluetoothThread.getInstance(this);
    }

    public void sendDataToBluetooth(String message1, String message2, String message3) {
        // BluetoothThread 객체의 sendData 메서드 호출
        bluetoothThread.sendData(message1, message2, message3);
    }
    public void sendDataToBluetooth2(String message1) {
        // BluetoothThread 객체의 sendData 메서드 호출
        bluetoothThread.sendData2(message1);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);
        context = this;


        bluetoothThread = BluetoothThread.getInstance(this);
        Random random = new Random();
        int num1 = random.nextInt(6); // Generates a random number between 0 and 5
        int num2 = random.nextInt(Math.max(0, 10 - num1 - 4)); // Generates a random number between 0 and (10 - num1 - 4)
        int num3 = 10 - num1 - num2;

        if (num3 < 0) {
            num1 = 0;
            num2 = 0;
            num3 = 0;
        }
        message1 = String.valueOf(num1);
        message2 = String.valueOf(num2);
        message3 = String.valueOf(num3);
        back = findViewById(R.id.back);
        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(view -> {
            Intent intent = new Intent(GamePageActivity.this, BoomGameActivity.class);
            startActivity(intent);
        });

        back.setOnClickListener(view -> {
            finish();
        });

        rullet_button = findViewById(R.id.rullet_button);
        son_byung_ho_button = findViewById(R.id.son_byung_ho_button);


        rullet_button.setOnClickListener(view -> {
            showDialog01(1);
        });

        son_byung_ho_button.setOnClickListener(view -> {
            showDialog01(2);
        });


    }

    private void showDialog01(int buttonIndex) {
        Dialog dialog01 = new Dialog(this);
        dialog01.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog01.setContentView(R.layout.activity_game_dialog);
        dialog01.show();

        Button noBtn = dialog01.findViewById(R.id.noBtn);
        noBtn.setOnClickListener(view -> {
            // '아니오' 버튼 클릭 시 동작 구현
            if (buttonIndex == 1) {

                // 버튼 1에 대한 처리
                startActivity(new Intent(this, RouletteActivity.class));
            } else if (buttonIndex == 2) {
                sendDataToBluetooth(message1, message2, message3);
                // 버튼 2에 대한 처리
                startActivity(new Intent(this, Game2Activity.class));
            }
            dialog01.dismiss();
        });

        Button yesBtn = dialog01.findViewById(R.id.yesBtn);
        yesBtn.setOnClickListener(view -> {
            // '예' 버튼 클릭 시 동작 구현
            Intent intent = new Intent(this, Drink2Activity.class);
            startActivity(intent);
            dialog01.dismiss();
        });
    }
}
