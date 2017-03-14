package com.example.miaoz.xgpush.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.miaoz.xgpush.view.pieview.PieData;
import com.example.miaoz.xgpush.view.pieview.PieView;

import java.util.ArrayList;

/**
 * Created by miaoz on 2017/3/14.
 */
public class PieActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PieView view = new PieView(this);
        setContentView(view);

        ArrayList<PieData> datas = new ArrayList<>();
        PieData pieData = new PieData("sloop",60);
        PieData pieData2 = new PieData("sloop",30);
        PieData pieData3 = new PieData("sloop",40);
        PieData pieData4 = new PieData("sloop",20);
        PieData pieData5 = new PieData("sloop",20);

        datas.add(pieData);
        datas.add(pieData2);
        datas.add(pieData3);
        datas.add(pieData4);
        datas.add(pieData5);
        view.setData(datas);

    }
}
