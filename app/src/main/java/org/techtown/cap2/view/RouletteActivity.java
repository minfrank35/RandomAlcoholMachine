package org.techtown.cap2.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.OnLuckyWheelReachTheTarget;
import com.bluehomestudio.luckywheel.WheelItem;

import org.techtown.cap2.BluetoothThread;
import org.techtown.cap2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RouletteActivity extends AppCompatActivity {
    private LuckyWheel luckyWheel;
    private String message1,message2,message3;
    private List<WheelItem> wheelItems;
    private BluetoothThread bluetoothThread;
    Context context;

    ImageButton restartButton,start,backButton;

    public RouletteActivity() {
        // BluetoothThread 인스턴스를 가져옴
        bluetoothThread = BluetoothThread.getInstance(this);
    }

    public void sendDataToBluetooth2(String message1) {
        // BluetoothThread 객체의 sendData 메서드 호출
        bluetoothThread.sendData2(message1);
    }
    public void sendDataToBluetooth(String message1, String message2, String message3) {
        // BluetoothThread 객체의 sendData 메서드 호출
        bluetoothThread.sendData(message1, message2, message3);
    }
    String point;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roulette);
        context = this;


        bluetoothThread = BluetoothThread.getInstance(this);
        luckyWheel = findViewById(R.id.luck_wheel);

        setupPlayerCountDialog();
        generateWheelItems();

         backButton = findViewById(R.id.back_btn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

         restartButton = findViewById(R.id.restart_btn);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setupPlayerCountDialog();


            }
        });
        luckyWheel.setLuckyWheelReachTheTarget(new OnLuckyWheelReachTheTarget() {
            @Override
            public void onReachTarget() {

                //아이템 변수에 담기
                WheelItem wheelItem = wheelItems.get(Integer.parseInt(point)+1);

                //아이템 텍스트 변수에 담기
                String money = wheelItem.text;

                //메시지
                Toast.makeText(RouletteActivity.this, money, Toast.LENGTH_SHORT).show();

                navigateToNextScreen();
            }

            private void navigateToNextScreen() {
                Intent intent = new Intent(RouletteActivity.this, RouletteActivity.class);
                startActivity(intent);
                finish(); // 현재 액티비티 종료
            }
        });

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

        start = findViewById(R.id.spin_btn);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToBluetooth2("g");
                sendDataToBluetooth(message1,message2,message3);
                luckyWheel.setLuckyWheelReachTheTarget(null);
                Random random = new Random();
                point = String.valueOf(random.nextInt(10)+1);
                luckyWheel.rotateWheelTo(Integer.parseInt(point));

            }
        });
    }
    // AlertDialog 및 AlertDialog.Builder 객체를 클래스 멤버로 선언
    private AlertDialog alertDialog;
    private AlertDialog.Builder ad;

    private void setupPlayerCountDialog() {
        // 다이얼로그 객체 생성
        final Dialog dialog = new Dialog(RouletteActivity.this);
        // 레이아웃 설정
        dialog.setContentView(R.layout.roulette_dialog);
        dialog.setCancelable(false);

        // 다이얼로그 내부의 뷰들 설정
        TextView textView = dialog.findViewById(R.id.textView2);
        final EditText editText = dialog.findViewById(R.id.textView3);
        ImageButton cancelButton = dialog.findViewById(R.id.closebtn);
        ImageButton okButton = dialog.findViewById(R.id.okaybtn);

        textView.setText("룰렛 인원 설정 2 ~ 10 사이에 값만 입력해주세요 \n (영어,한글,특수문자는 입력 시 작동이 안됩니다.");

        // 확인 버튼을 처음에 비활성화
        okButton.setEnabled(false);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String inputText = s.toString().trim();

                // 입력된 텍스트가 숫자인지 확인
                if (!isValidNumber(inputText)) {
                    okButton.setEnabled(false);
                } else {
                    int number = Integer.parseInt(inputText);
                    if (number >= 2 && number <= 10) {
                        okButton.setEnabled(true);
                    } else {
                        okButton.setEnabled(false);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // 취소 버튼 클릭 시 다이얼로그 닫기
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // 확인 버튼 클릭 시 작업 수행
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = editText.getText().toString().trim();

                if (inputText.isEmpty()) {
                    // 아무 것도 입력되지 않았을 때 토스트 메시지 표시
                    Toast.makeText(RouletteActivity.this, "값을 입력하세요", Toast.LENGTH_SHORT).show();
                } else if (!isValidNumber(inputText)) {
                    // 유효하지 않은 숫자가 입력되었을 때 토스트 메시지 표시
                    Toast.makeText(RouletteActivity.this, "올바른 값을 입력하세요", Toast.LENGTH_SHORT).show();
                } else {
                    // 입력이 유효할 때 필요한 작업 수행
                    int count = Integer.parseInt(inputText);
                    wheelItems = new ArrayList<>();

                    Drawable d = getResources().getDrawable(R.drawable.ic_money, null);
                    Bitmap bitmap = drawableToBitmap(d);

                    String[] colors = {"#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#FFA500", "#800080", "#FFC0CB", "#808080", "#000000", "#FFC0CB"};
                    String[] animals = {"강 아 지", "고 양 이", "사 자", "기 린", "호 랑 이", "팬 더", "드 래 곤", "토 끼", "오 리", "돼 지"};
                    for (int i = 1; i <= count && i < colors.length && i < animals.length; i++) {
                        WheelItem wheelItem = new WheelItem(Color.parseColor(colors[i]), bitmap, animals[i]);
                        wheelItems.add(wheelItem);
                    }

                    luckyWheel.addWheelItems(wheelItems);
                    dialog.dismiss();
                }
            }

            private boolean isValidNumber(String input) {
                try {
                    Integer.parseInt(input);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        });

        // 다이얼로그 보여주기
        dialog.show();
    }









    private boolean isValidNumber(String inputText) {
        // 입력값이 비어있지 않고, 숫자로 변환 가능한 경우에만 true를 반환합니다.
        return !inputText.isEmpty() && inputText.matches("\\d+");
    }

    /**
     * 데이터 담기
     */
    private void generateWheelItems() {
        wheelItems = new ArrayList<>();

        Drawable d = getResources().getDrawable(R.drawable.roulette5, null);
        Bitmap bitmap = drawableToBitmap(d);

        wheelItems.add(new WheelItem(Color.parseColor("#F44336"), bitmap, "기 린"));
        wheelItems.add(new WheelItem(Color.parseColor("#E91E63"), bitmap, "강 아 지"));
        wheelItems.add(new WheelItem(Color.parseColor("#9C27B0"), bitmap, "고 양 이"));
        wheelItems.add(new WheelItem(Color.parseColor("#3F51B5"), bitmap, "드 래 곤"));
        wheelItems.add(new WheelItem(Color.parseColor("#1E88E5"), bitmap, "사  자"));
        wheelItems.add(new WheelItem(Color.parseColor("#009688"), bitmap, "토 끼"));

        luckyWheel.addWheelItems(wheelItems);
    }

    /**
     * drawable -> bitmap
     * @param drawable drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
