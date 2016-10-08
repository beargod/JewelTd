package com.example.administrator.jewelshow;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 一个图片加文字的控件
 */
public class ShowSkill extends LinearLayout{
    ImageView skill_pic;
    TextView skill_name;
    private int skill_color;
    private Drawable pic;
    private String name;
    private Context context;
    public ShowSkill(Context context){
        super(context);
        this.context = context;
        skill_pic = new ImageView(context);
        skill_name = new TextView(context);
        addView(skill_pic);
        addView(skill_name);
    }
    public ShowSkill(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.Skills);
        skill_color = ta.getColor(R.styleable.Skills_skill_color,0);
        pic = ta.getDrawable(R.styleable.Skills_skill_pic);
        name = ta.getString(R.styleable.Skills_skill_name);
        ta.recycle();

        skill_pic = new ImageView(context);
        skill_name = new TextView(context);
        skill_pic.setImageDrawable(pic);
        skill_name.setTextColor(skill_color);
        skill_name.setText(name);
        this.setOrientation(VERTICAL);
        this.setGravity(Gravity.LEFT);
        this.setPadding(0,0,0,3);
        addView(skill_pic);
        addView(skill_name);
    }

    public void setSkill_color(int skill_color) {
        this.skill_color = skill_color;
    }

    public void setPic(Drawable pic) {
        this.pic = pic;
        LayoutParams lp = (LayoutParams)skill_pic.getLayoutParams();
        float size = context.getResources().getDimension(R.dimen.image_size);
        lp.height = (int)size;
        lp.width = (int)size;
        skill_pic.setLayoutParams(lp);
        skill_pic.setPadding(0,2,0,0);
        skill_pic.setImageDrawable(pic);
    }
    public void setPic(Bitmap pic) {
        LayoutParams lp = (LayoutParams)skill_pic.getLayoutParams();
        float size = context.getResources().getDimension(R.dimen.image_size);
        lp.height = (int)size;
        lp.width = (int)size;
        skill_pic.setLayoutParams(lp);
        skill_pic.setPadding(0,2,0,0);
        skill_pic.setImageBitmap(pic);
    }
    public void setName(String name) {
        this.name = name;
        skill_name.setTextColor(Color.WHITE);
        skill_name.setTextSize(context.getResources().getDimension(R.dimen.text_size));
        skill_name.setText(name);

    }
}
