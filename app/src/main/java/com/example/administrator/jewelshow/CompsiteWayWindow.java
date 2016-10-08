package com.example.administrator.jewelshow;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.administrator.jewel.CompositeList;
import com.example.administrator.jewel.CompositeWay;

import java.util.ArrayList;

/**
 * Created by BigGod on 2016/9/26.
 */
public class CompsiteWayWindow extends PopupWindow {
    private Context context;
    private GameView gameView;
    private ImageResourse ir;
    private View view;
    private CompositeList cl;
    public CompsiteWayWindow(Context context, GameView gameView) {
        super(context);
        this.context=context;
        this.gameView=gameView;
        this.ir=gameView.getIr();
        this.cl = gameView.getCl();
        this.view= LayoutInflater.from(context).inflate(R.layout.compsite_way,null);
        this.setWidth(450);
        this.setHeight(850);
        setCompsite();
        this.setContentView(view);
    }
    private void setCompsite(){
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.compsites);
        ArrayList compsiteWays = cl.getCompositeWays();
        for(int i =0;i<compsiteWays.size();i++){
            final CompositeWay cw = (CompositeWay)compsiteWays.get(i);
            ShowCompositeWay scw=new ShowCompositeWay(context,cw,ir,false,gameView.getPlayer().getJewels());
            scw.setGravity(Gravity.CENTER);
            layout.addView(scw);
        }
    }
}
