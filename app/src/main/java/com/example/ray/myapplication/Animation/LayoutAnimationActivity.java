package com.example.ray.myapplication.Animation;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ray.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class LayoutAnimationActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mAddItem,mRemoveItem;
    private LinearLayout mLinearLayout;
    private LayoutTransition mlayoutTransition;
    private int count = 0;
    private RecyclerView mRecyclerView;
    private AnimaRecycler mAnimaRecycler;
    private List<Integer> mlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_animation);
        init();
        addImage();
        //1.创建实例
        mlayoutTransition = new LayoutTransition();
        //2.创建动画
        ObjectAnimator animaIn = ObjectAnimator.ofFloat(null,"rotationY",0f,360f,0f);
        //3.将动画添加到LayoutTransition.setAnimator(应用动画的对象，动画)
        //APPEARING  元素在容器中出现时所定义的动画。DISAPPEARING  元素在容器中消失时所定义的动画。
        //CHANGE_APPEARING  由于容器中要显现一个新的元素，其它需要变化的元素所应用的动画
        //CHANGE_DISAPPEARING  当容器中某个元素消失，其它需要变化的元素所应用的动画
        mlayoutTransition.setAnimator(LayoutTransition.APPEARING,animaIn);

        ObjectAnimator animaOut = ObjectAnimator.ofFloat(null,"rotation",0f,90f,0f);
        mlayoutTransition.setAnimator(LayoutTransition.APPEARING,animaOut);
        //4.将LayoutTransition添加到LinearLayout
        mLinearLayout.setLayoutTransition(mlayoutTransition);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAnimaRecycler=new AnimaRecycler(mlist,this);
        mRecyclerView.setAdapter(mAnimaRecycler);

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy>0){
                    //Toast.makeText(LayoutAnimationActivity.this,"下滑",Toast.LENGTH_SHORT).show();
                }else {
                    //Toast.makeText(LayoutAnimationActivity.this,"上滑",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void init()
    {
            mAddItem = findViewById(R.id.add_item_btn);
            mRemoveItem = findViewById(R.id.remove_item_btn);
            mLinearLayout = findViewById(R.id.layoutTransitonGroup);
            mRecyclerView = findViewById(R.id.recycler_anima);
            mAddItem.setOnClickListener(this);
            mRemoveItem.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.add_item_btn:
                addItem();
                break;
            case R.id.remove_item_btn:
                removeItem();
                break;
            default:
                break;
        }
    }

    private void addItem(){
        // 控件实际添加位置为当前触发位置点下一位
        count++;
        //创建Button实例
        Button button = new Button(this);
        button.setText("button + "+count);
        //创建外围控件LinearLayout mLinearLayout;
        //设置属性 LayoutParams
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                ,ViewGroup.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(params);
        //将控件放入外围控件LinearLayout  vertical排列是 index:0 表示在第一个添加
        mLinearLayout.addView(button,0);

    }
    private void removeItem(){
        if (count>0)
        {
            //删除第一个控件
            mLinearLayout.removeViewAt(0);
        }
        count--;
    }

    private void addImage(){
        mlist.add(R.mipmap.image1);
        mlist.add(R.mipmap.image12);
        mlist.add(R.mipmap.image13);
        mlist.add(R.mipmap.image14);
        mlist.add(R.mipmap.image15);
        mlist.add(R.mipmap.image16);
        mlist.add(R.mipmap.image17);
    }
}
