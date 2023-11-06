package org.techtown.cap2.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.cap2.R;

import java.util.ArrayList;

public class RecipeDialog extends Dialog {
    private Context context;
    private TextView Confirm;


    private View.OnClickListener Confirm_Btn;

    public TextView Title;
    public TextView tvWarn;
    public String title;

    private RecyclerView recipeRecyclerView;
    private RecipeDialogAdapter recipeRecyclerAdapter;
    private ArrayList<BeverRecipe> recipeList;
    private RecipeDialogAdapter.OnClickRecipeItem onClickRecipeItem;

    private RadioButton radioButton, radioButton2, radioButton3;

    private OnClickRadioButton onClickRadioButton;

    public RecipeDialog(@NonNull Context context, View.OnClickListener Confirm_Btn, String title, ArrayList<BeverRecipe> recipeList, RecipeDialogAdapter.OnClickRecipeItem onCLickRecipeItem) {
        super(context);
        //생성자에서 리스너 및 텍스트 초기화
        this.context = context;
        this.Confirm_Btn = Confirm_Btn;
        this.title = title;
        this.recipeList = (ArrayList<BeverRecipe>) recipeList.clone();
        this.onClickRecipeItem = onCLickRecipeItem;
    }

    public void setOnClickRadioButton(OnClickRadioButton onClickRadioButton) {
        this.onClickRadioButton = onClickRadioButton;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그의 꼭짓점이 짤리는부분 방지.
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setContentView(R.layout.dialog_recipe);

        Confirm=findViewById(R.id.Confirm);
        Title = findViewById(R.id.title);
        tvWarn = findViewById(R.id.warn);
        recipeRecyclerView = findViewById(R.id.recipe_list);
        recipeRecyclerAdapter = new RecipeDialogAdapter(context, recipeList);
        recipeRecyclerAdapter.setOnClickRecipeItem(onClickRecipeItem);
        recipeRecyclerView.setAdapter(recipeRecyclerAdapter);

        if(recipeList.size() == 0) {
            recipeRecyclerView.setVisibility(View.GONE);
            tvWarn.setVisibility(View.VISIBLE);
        } else {
            recipeRecyclerView.setVisibility(View.VISIBLE);
            tvWarn.setVisibility(View.GONE);
        }

        radioButton = findViewById(R.id.radioButton);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3= findViewById(R.id.radioButton3);

        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRadioButton.onClickButton(1);
            }
        });

        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRadioButton.onClickButton(2);

            }
        });
        radioButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRadioButton.onClickButton(3);
            }
        });

        Confirm.setOnClickListener(Confirm_Btn);

        //타이틀과 바디의 글씨 재셋팅
        Title.setText(this.title);
    }

    public interface OnClickRadioButton {
        public void onClickButton(int weight);
    }


}