package org.techtown.cap2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.techtown.cap2.view.BoomGame_setting_dialog;
import org.techtown.cap2.view.GamePageActivity;

public class BoomGameActivity extends AppCompatActivity {

    static ImageView imageView;
    Button backBtn;
    private BluetoothThread bluetoothThread;
    Context context;

    // count 변수를 클래스 멤버로 선언
    private int count = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boomb_game);
        context = this;

        bluetoothThread = BluetoothThread.getInstance(this);

        imageView = findViewById(R.id.view);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BoomGameActivity.this, GamePageActivity.class);
                startActivity(intent);

                sendDataToBluetooth2("c");
                finish();
            }
        });

        showCustomDialog();
    }

    public void showCustomDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(BoomGameActivity.this);
                LayoutInflater inflater = getLayoutInflater(); // inflater 초기화

                View dialogView = inflater.inflate(R.layout.activity_boom_game_setting_dialog, null);
                builder.setView(dialogView);

                // 다이얼로그 내부의 뷰들에 접근
                ImageButton minusBtn = dialogView.findViewById(R.id.minusbtn);
                ImageButton plusBtn = dialogView.findViewById(R.id.plusbtn);
                ImageButton imagebutton = dialogView.findViewById(R.id.imageButton4);
                TextView textView = dialogView.findViewById(R.id.textView);

                // 버튼들에 클릭 리스너 추가
                minusBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // minus 버튼 동작 추가
                        if (count > 0) {
                            count--;
                            textView.setText(String.valueOf(count));
                        }
                    }
                });

                plusBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // plus 버튼 동작 추가
                        count++;
                        textView.setText(String.valueOf(count));
                    }
                });

                imagebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // custom 버튼 동작 추가
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });

                // 다이얼로그 생성 및 표시
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void sendDataToBluetooth2(String message1) {
        // BluetoothThread 객체의 sendData 메서드 호출
        bluetoothThread.sendData2(message1);
    }
}
