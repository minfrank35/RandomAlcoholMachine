package org.techtown.cap2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.util.Random;

public class GamePage extends AppCompatActivity {

    Button button, button2, button3, back;
    private BluetoothThread bluetoothThread;
    private String message1,message2,message3;
    Context context;

    public GamePage() {
        // BluetoothThread 인스턴스를 가져옴
        this.context = context;
        bluetoothThread = BluetoothThread.getInstance(context);
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


        bluetoothThread = BluetoothThread.getInstance(getApplicationContext());
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
            Intent intent = new Intent(GamePage.this, boombGame.class);
            startActivity(intent);
        });

        back.setOnClickListener(view -> {
            Intent intent = new Intent(GamePage.this, MainActivity.class);
            startActivity(intent);
        });

        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);


        button.setOnClickListener(view -> {
            showDialog01(1);
        });

        button2.setOnClickListener(view -> {
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
                startActivity(new Intent(this, Roulette.class));
            } else if (buttonIndex == 2) {
                sendDataToBluetooth(message1,message2,message3);
                // 버튼 2에 대한 처리
                startActivity(new Intent(this, Game2.class));
            }
            dialog01.dismiss();
        });

        Button yesBtn = dialog01.findViewById(R.id.yesBtn);
        yesBtn.setOnClickListener(view -> {
            // '예' 버튼 클릭 시 동작 구현
            Intent intent = new Intent(this, Drink2.class);
            startActivity(intent);
            dialog01.dismiss();
        });
    }
}
