package org.techtown.cap2.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import org.techtown.cap2.BaseActivity;
import org.techtown.cap2.BluetoothThread;
import org.techtown.cap2.R;


public class MainActivity extends BaseActivity {
    ViewFlipper viewflipper;
    Button Dbtn,Gbtn;

    Context context;
    private BluetoothThread bluetoothThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Dbtn = findViewById(R.id.Dbtn);
        Gbtn = findViewById(R.id.Gbtn);



        Button closeBtn = findViewById(R.id.button5);
        closeBtn.setOnClickListener(v -> {
            finishApp();
        });

        Dbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DrinkPage3Activity.class);
                startActivity(intent);
            }
        });

        Gbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GamePageActivity.class);
                startActivity(intent);
            }
        });



//        Dialog dilaog01 = new Dialog(MainActivity.this);       // Dialog 초기화
//        dilaog01.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
//        dilaog01.setContentView(R.layout.activity_custom_dialog);

//        findViewById(R.id.Gbtn).setOnClickListener(view -> {
//            showDialog01(); // 아래 showDialog01() 함수 호출
//        });




        int images[] = {
                R.drawable.silla1,
                R.drawable.silla2
        };
        viewflipper = findViewById(R.id.view);
        for(int image: images){
            fllipperImages(image);
        }
        viewflipper.setOnClickListener(view -> {
            Intent vintetn = new Intent(Intent.ACTION_VIEW, Uri.parse("https://computer.silla.ac.kr/computer2016/"));
            startActivity(vintetn);
        });

        // 대화 상자 생성


    }

//    public void showDialog01() {
//        Dialog dialog01 = new Dialog(this); // this는 액티비티나 컨텍스트 객체입니다.
//        dialog01.setContentView(R.layout.activity_game_dialog); // 다이얼로그에 표시할 레이아웃 파일을 설정합니다.
//        dialog01.show(); // 다이얼로그를 보여줍니다.
//
//        Button noBtn = dialog01.findViewById(R.id.noBtn);
//        noBtn.setOnClickListener(view -> {
//            // '아니오' 버튼 클릭 시 동작 구현
//            Intent intent = new Intent(this, GamePage.class);
//            startActivity(intent);
//            dialog01.dismiss(); // 다이얼로그 닫기
//        });
//
//        Button yesBtn = dialog01.findViewById(R.id.yesBtn);
//        yesBtn.setOnClickListener(view -> {
//            // '예' 버튼 클릭 시 동작 구현
//            Intent intent = new Intent(this, Drink2.class);
//            startActivity(intent);
//            dialog01.dismiss(); // 다이얼로그 닫기
//        });
//    }


    private void fllipperImages(int image) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        viewflipper.addView(imageView);
        viewflipper.setFlipInterval(3000);
        viewflipper.setAutoStart(true);

        viewflipper.setInAnimation(this, android.R.anim.slide_in_left);
        viewflipper.setOutAnimation(this, android.R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishApp();
    }
}





