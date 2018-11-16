package com.example.ray.myapplication.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ray.myapplication.MaterailU.MaterailBean;
import com.example.ray.myapplication.MaterailU.MaterialRecyclerAdapter;
import com.example.ray.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentOne extends Fragment {

    private RecyclerView recyclerView;
    private MaterialRecyclerAdapter adapter;
    private List<MaterailBean> mDatas = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one,container,false);
        addItem();
        recyclerView = view.findViewById(R.id.viewpager_fragment_one_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new MaterialRecyclerAdapter(mDatas,getContext());
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void addItem(){

        int []imageId ={R.mipmap.image1,R.mipmap.image12,R.mipmap.image13,
                R.mipmap.image14, R.mipmap.image15,R.mipmap.image16,R.mipmap.image17,
                R.mipmap.image1,R.mipmap.image12,R.mipmap.image13,R.mipmap.image14,
                R.mipmap.image15,R.mipmap.image16,R.mipmap.image17,R.mipmap.image1,
                R.mipmap.image12,R.mipmap.image13,R.mipmap.image14, R.mipmap.image15,
                R.mipmap.image16,R.mipmap.image17};
        for (int i=0;i<imageId.length;i++){
            MaterailBean data = new MaterailBean();
            data.setImageId(imageId[i]);
            data.setImageName("this is "+i);
            mDatas.add(data);
        }
    }
}
