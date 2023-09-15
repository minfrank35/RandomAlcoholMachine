package org.techtown.cap2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.techtown.cap2.util.PermissionCallback;
import org.techtown.cap2.util.PermissionUtil;

public class LoadingActivity extends BaseActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private WebView loadingWebView;

    ImageView imageView;
    Button button8;
    @SuppressLint({"SetJavaScriptEnabled", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);

        PermissionUtil.checkPermission(this, Manifest.permission.BLUETOOTH_SCAN, Const.PERMISSION_BLUETOOTH_LOADING_ACTIVITY, new PermissionCallback() {
            @Override
            public void onPermissionGranted(Activity activity) {
                Toast.makeText(activity, "블루투스 권한이 허용되어 있습니다.", Toast.LENGTH_SHORT).show();
                init();
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

    private void init() {
        imageView = findViewById(R.id.loadinggggg);
        Glide.with(this).load(R.drawable.loadinggggg).into(imageView);

        button8 = findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // BluetoothThread 객체 생성
                BluetoothThread bluetoothThread = BluetoothThread.getInstance(LoadingActivity.this);

                // BluetoothThread 스레드 시작
                bluetoothThread.start();
                Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Const.PERMISSION_BLUETOOTH_LOADING_ACTIVITY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "블루투스 권한을 허용하였습니다.", Toast.LENGTH_SHORT).show();
                init();
            } else {
                Toast.makeText(this, "블루투스 권한을 거절하였습니다. 앱을 종료합니다.", Toast.LENGTH_SHORT).show();
                finishApp();
            }
        }
    }
}
