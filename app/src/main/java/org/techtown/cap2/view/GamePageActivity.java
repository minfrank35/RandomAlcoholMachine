package org.techtown.cap2.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ViewFlipper;

import org.techtown.cap2.BluetoothThread;
import org.techtown.cap2.BoomGameActivity;
import org.techtown.cap2.R;
import org.techtown.cap2.util.ViewflipperUtil;

import java.util.Random;

public class GamePageActivity extends AppCompatActivity {

    private ViewFlipper viewflipper;

    private ImageButton back,rulletButton,sonByungHobutton,rollBombbutton,backButton;
    private String message1,message2,message3;
    private View inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);
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
        backButton = findViewById(R.id.back);
        rollBombbutton = findViewById(R.id.roll_bomb);
        rollBombbutton.setOnClickListener(view -> {


            Intent intent = new Intent(GamePageActivity.this, BoomGameActivity.class);
            startActivity(intent);
        });


        viewflipper = findViewById(R.id.view);
        int images[] = {
                R.drawable.logologo1,
                R.drawable.logologo2,
                R.drawable.logologo3
        };
        ViewflipperUtil viewFlipperUtil = new ViewflipperUtil(this, images);
        viewFlipperUtil.basicFlip(viewflipper);

        viewflipper.setOnClickListener(view -> {
            Intent vintetn = new Intent(Intent.ACTION_VIEW, Uri.parse("https://computer.silla.ac.kr/computer2016/"));
            startActivity(vintetn);
        });

        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(GamePageActivity.this, MainActivity.class);
            startActivity(intent);
        });

        rulletButton = findViewById(R.id.rullet_button);
        sonByungHobutton = findViewById(R.id.son_byung_ho_button);


        rulletButton.setOnClickListener(view -> {
            showDialog01(1);
        });

        sonByungHobutton.setOnClickListener(view -> {
            showDialog01(2);
        });

        rollBombbutton.setOnClickListener(view -> {
            showConfirmationDialog();
        });




    }
    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater(); // inflater 초기화

        View dialogView = inflater.inflate(R.layout.activity_bom_dialog, null);
        builder.setView(dialogView);

        // Dialog 레이아웃 내에서 noBtn 찾기
        Button noBtn = dialogView.findViewById(R.id.noBtn);

        // Dialog 객체 생성
        final AlertDialog dialog = builder.create();

        // noBtn 클릭 이벤트 처리
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 여기에 noBtn을 눌렀을 때의 동작을 추가
                // 예를 들어, 다른 Activity로 이동하는 코드를 추가할 수 있습니다.
                Intent intent = new Intent(GamePageActivity.this, BoomGameActivity.class);
                startActivity(intent);

                // Dialog 닫기
                dialog.dismiss();
            }
        });

        dialog.show();
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

    private void sendDataToBluetooth(String message1, String message2, String message3) {
        // BluetoothThread 객체의 sendData 메서드 호출
        BluetoothThread.getInstance(this).sendData(message1, message2, message3);
    }
    private void sendDataToBluetooth2(String message1) {
        // BluetoothThread 객체의 sendData 메서드 호출
        BluetoothThread.getInstance(this).sendData2(message1);
    }
}
