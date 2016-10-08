package com.example.administrator.jewelshow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.jewel.CompositeWay;
import com.example.administrator.jewel.Jewel;
import com.example.administrator.jewel.basicJewel.BasicJewel;

import java.util.List;

/**
 * 显示合成方法
 */
public class ShowCompositeWay extends LinearLayout{
    private GameTools gameTools;
    private CompositeWay compositeWay;//显示的方法
    private Context context;
    private Drawable default_pic;
    private ImageResourse ir;
    private List jewels;
    public ShowCompositeWay(Context context, CompositeWay compositeWay, ImageResourse ir, boolean canClick, List jewels){
        super(context);
        gameTools=new GameTools();
        this.context = context;
        this.jewels = jewels;
        this.compositeWay = compositeWay;
        this.ir = ir;

        if(!canClick){
            this.setAlpha((float)0.7);
        }
        this.setGravity(Gravity.CENTER);
        this.setBackgroundResource(R.drawable.boder);
        int height = (int)context.getResources().getDimension(R.dimen.compsite_height);
        int width = (int)context.getResources().getDimension(R.dimen.compsite_width);
        LinearLayout.LayoutParams lp= new LinearLayout.LayoutParams(width,height);
        lp.setMargins(0,0,0,10);
        this.setLayoutParams(lp);
        this.setPadding(0,0,0,5);
        default_pic = ContextCompat.getDrawable(context,R.mipmap.ic_launcher);
        setResult();
        setMaterials();
    }
    public ShowCompositeWay(Context context, AttributeSet attrs,CompositeWay compositeWay,ImageResourse ir){
        super(context,attrs);
        this.context = context;
        this.compositeWay = compositeWay;
        this.ir = ir;
        default_pic = ContextCompat.getDrawable(context,R.mipmap.ic_launcher);
        setResult();
    }

    //添加合成的结果
    public void setResult(){
        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setGravity(Gravity.CENTER_VERTICAL);
        ShowSkill result = new ShowSkill(context);
        result.setPic(default_pic);
        int id = compositeWay.getResult_id();
        String name = "";
        name+=gameTools.getNameById(id);
        if(id<=8){
            name+="6";
        }
        result.setName(name);
        layout.addView(result);
        addView(layout);
    }
    //添加合成的材料
    public void setMaterials(){
        LinearLayout layout = new LinearLayout(context);
        layout.setPadding(20,0,0,0);
        layout.setOrientation(VERTICAL);
        String[] materials = compositeWay.getMaterials();
        for(int i =0;i<materials.length;){
            ShowSkill material = new ShowSkill(context);
            int id = gameTools.toInt(materials[i]);
            Bitmap pic = ir.getImageById(id);
            material.setPic(pic);
            i++;
            int quality = gameTools.toInt(materials[i]);
            material.setName(gameTools.getShortById(id,quality));
            if(!isContainJewel(id,quality))
                material.setAlpha((float)0.5);
            layout.addView(material);
            i++;
        }
        addView(layout);
    }
    private boolean isContainJewel(int jewelID,int quality){
        if(quality==0) {
            for (int i = 0; i < jewels.size(); i++) {
                Jewel jewel = (Jewel) jewels.get(i);
                if (jewelID == jewel.getId())
                    return true;
            }
        }
        else{
            for (int i = 0; i < jewels.size(); i++) {
                Jewel jewel = (Jewel) jewels.get(i);
                if (jewel.getId()<9&&jewel.getId()>0) {
                    BasicJewel bj = (BasicJewel)jewel;
                    if (jewelID == jewel.getId()&&bj.getQuality() ==quality)
                        return true;
                }
            }
        }
        return false;
    }
}
