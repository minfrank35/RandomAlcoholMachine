package org.techtown.cap2.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.cap2.R;

import java.util.ArrayList;

public class BeverRegisterDialog extends Dialog {
    private Context context;
    private TextView cancel;

    private String titleText;


    private View.OnClickListener cancelBtnListener;
    private OnClickBeverRegisterItem onClickBeverRegisterItem;

    public TextView title;
    private TextView cola, cider, sonicWater;

    private RecyclerView recipeRecyclerView;
    private RecipeDialogAdapter recipeRecyclerAdapter;
    private ArrayList<String[]> recipeList;
    private RecipeDialogAdapter.OnClickRecipeItem onClickRecipeItem;

    public BeverRegisterDialog(@NonNull Context context, View.OnClickListener cancelBtnListener, String title, OnClickBeverRegisterItem onClickBeverRegisterItem) {
        super(context);
        //생성자에서 리스너 및 텍스트 초기화
        this.context = context;
        this.cancelBtnListener = cancelBtnListener;
        this.titleText = title;
        this.onClickBeverRegisterItem = onClickBeverRegisterItem;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그의 꼭짓점이 짤리는부분 방지.
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setContentView(R.layout.dialog_bever_register);

        title = findViewById(R.id.title);
        cola = findViewById(R.id.cola);
        cider = findViewById(R.id.cider);
        sonicWater = findViewById(R.id.sonic_water);
        cancel = findViewById(R.id.button_cancel);

        cancel.setOnClickListener(cancelBtnListener);
        cola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBeverRegisterItem.onClickFirstItem("콜라");
                BeverRegisterDialog.this.dismiss();
            }
        });
        cider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBeverRegisterItem.onClickSecondItem("사이다");
                BeverRegisterDialog.this.dismiss();
            }
        });
        sonicWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBeverRegisterItem.onClickThirdItem("소닉워터");
                BeverRegisterDialog.this.dismiss();
            }
        });
        //타이틀과 바디의 글씨 재셋팅
        title.setText(titleText);
    }

    public interface OnClickBeverRegisterItem {
        public void onClickFirstItem(String Text);
        public void onClickSecondItem(String Text);
        public void onClickThirdItem(String Text);
    }


}