package org.techtown.cap2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class BoomGameActivity extends AppCompatActivity {

    static ImageView imageView;
//    private Button changeImageButton;
//    private int[] imageResources = {R.drawable.boom1, R.drawable.boom2, R.drawable.boom3};
//    private int currentImageIndex = 0;
    Button backBtn;
    private BluetoothThread bluetoothThread;
    Context context;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boomb_game);
        context = this;

        bluetoothThread = BluetoothThread.getInstance(this);
        showConfirmationDialog();
        imageView = findViewById(R.id.view);

        backBtn= findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDataToBluetooth2("c");
                finish();
            }
        });

    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("게임 방법")
                .setMessage("이 게임은 리모컨으로 진행됩니다.")
                .setPositiveButton("시작하기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        sendDataToBluetooth2("r");

                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void sendDataToBluetooth2(String message1) {
        // BluetoothThread 객체의 sendData 메서드 호출
        bluetoothThread.sendData2(message1);
    }
}
