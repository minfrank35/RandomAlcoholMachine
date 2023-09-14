package org.techtown.cap2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Loading extends AppCompatActivity {

    private WebView loadingWebView;

    ImageView imageView;
    Button button8;
    private static final int REQUEST_PERMISSION_CODE_1 = 1001;
    @SuppressLint({"SetJavaScriptEnabled", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        String bluetoothPermission = Manifest.permission.BLUETOOTH_ADVERTISE;
        Context context = this;


        if (ContextCompat.checkSelfPermission(this, bluetoothPermission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{bluetoothPermission}, REQUEST_PERMISSION_CODE_1);
        }

        imageView = findViewById(R.id.loadinggggg);
        Glide.with(this).load(R.drawable.loadinggggg).into(imageView);

        button8 = findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // BluetoothThread 객체 생성
                BluetoothThread bluetoothThread = BluetoothThread.getInstance(context);

                // BluetoothThread 스레드 시작
                bluetoothThread.start();
                Intent intent = new Intent(Loading.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // 일정 시간이 지난 후에 메인 액티비티로 전환하도록 설정할 수 있습니다.
//        long loadingTime = 5000; // 로딩 화면을 표시할 시간 (밀리초)
//        new Handler().postDelayed(() -> {
//            Intent intent = new Intent(Loading.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }, loadingTime);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION_CODE_1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "블루투스 권한을 허용하였습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "블루투스 권한을 거절하였습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
