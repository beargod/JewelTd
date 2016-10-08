package com.example.administrator.jewelshow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.administrator.monster.Monster;
import com.example.administrator.skill.monsterSkill.DeBuff;
import com.example.administrator.skill.monsterSkill.MonsterSkill;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/7.
 */
public class MonsterDetailWindow extends PopupWindow {
    private Monster monster;
    private Context context;
    private View monsterDetailView;
    private final int TEXT_COLOR = Color.WHITE;
    private ToggleButton firstAttck;
    private GameView gameView;
    private ImageResourse ir;

    public MonsterDetailWindow(Context context, Monster monster , ImageResourse ir, GameView gameView,int width,int height) {
        super(context);
        this.monster=monster;
        this.context=context;
        this.gameView=gameView;
        this.ir=ir;
        this.monsterDetailView= LayoutInflater.from(context).inflate(R.layout.monster_detail,null);
        drawDetail();
        this.setWidth(width);
        this.setHeight(height);
        this.setContentView(monsterDetailView);

    }
    private void setFirstAttckButton(){
        firstAttck = (ToggleButton) monsterDetailView.findViewById(R.id.first_attck);
        firstAttck.setVisibility(View.VISIBLE);
        firstAttck.setChecked(!monster.isFirstAttack());
        firstAttck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                monster.setFirstAttack(true);
            }
        });
    }
    public void updateDetail(){
        setHp();
        setDebuffs();
    }
    private void drawDetail(){
        setMonsterView();
        setMonsterName();
        setHp();
        setArror();
        setMk();
        setSpeed();
        setSkills();
        setDebuffs();
        setFirstAttckButton();
    }
    private Bitmap getMonsterPicture(){
        Bitmap picture = ir.getMonsterById(monster.getId());
        int newWidth = picture.getWidth();
        int newHeight = picture.getHeight();
        float scaleWidth =  (150)/((float) newWidth);
        float scaleHeight = (150)/((float) newHeight);
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        picture = Bitmap.createBitmap(picture,0,0,newHeight,newWidth,matrix,true);
        return picture;
    }
    public void repaintDetail(Monster monster){
        this.monster=monster;
    }
    private void setMonsterView(){
        ImageView view =(ImageView) monsterDetailView.findViewById(R.id.monsterView);
        view.setImageBitmap(getMonsterPicture());
    }
    private void setHp(){
        TextView hp =(TextView) monsterDetailView.findViewById(R.id.hp);
        hp.setText("血量: "+monster.getCurrent_hp()+" / "+monster.getHp());
    }
    private void setMonsterName(){
        TextView name = (TextView) monsterDetailView.findViewById(R.id.monsterName);
        name.setText(monster.getName());
    }
    private void setArror(){
        TextView armor = (TextView) monsterDetailView.findViewById(R.id.armor);
        armor.setText("护甲: "+monster.getArmor());
    }
    private void setMk(){
        TextView mk = (TextView) monsterDetailView.findViewById(R.id.mk);
        mk.setText("魔抗: "+monster.getMk());
    }
    private void setSpeed(){
        TextView speed = (TextView) monsterDetailView.findViewById(R.id.speed);
        speed.setText("速度: "+monster.getSpeed());
    }
    private void setSkills(){
        LinearLayout layout = (LinearLayout) monsterDetailView.findViewById(R.id.skills);
        if(monster.getSkills()!=null) {
            ArrayList skills = monster.getSkills();
            for (int i = 0; i < skills.size(); i++) {
                ShowSkill showSkill = new ShowSkill(context);
                MonsterSkill skill = (MonsterSkill) skills.get(i);
                String string_skill = "";
                string_skill+=skill.getSkillName();
                string_skill+="(";
                string_skill+=skill.getSkillLevel();
                string_skill+=")";
                showSkill.setName(string_skill);
                showSkill.setSkill_color(TEXT_COLOR);
                showSkill.setPic(ir.getImageById(1));
                layout.addView(showSkill);
            }
        }
    }
    private void setDebuffs(){
        LinearLayout layout = (LinearLayout) monsterDetailView.findViewById(R.id.debuffs);
        layout.removeAllViews();
        if(monster.getDebuffs()!=null) {
            ArrayList debuffs= monster.getDebuffs();
            for (int i = 0; i < debuffs.size(); i++) {
                DeBuff deBuff = (DeBuff) debuffs.get(i);
                int id =deBuff.getId();
                ImageView debuff_pic = new ImageView(context);
                debuff_pic.setImageBitmap(ir.getImageById(1));
                layout.addView(debuff_pic);
            }
        }
    }
}
