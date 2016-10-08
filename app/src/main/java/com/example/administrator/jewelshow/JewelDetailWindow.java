package com.example.administrator.jewelshow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.administrator.jewel.CompositeList;
import com.example.administrator.jewel.CompositeWay;
import com.example.administrator.jewel.Jewel;
import com.example.administrator.jewel.basicJewel.BasicJewel;
import com.example.administrator.jewel.basicJewel.BasicJewelBuilderControl;
import com.example.administrator.jewel.specialJewel.SpecialJewelController;
import com.example.administrator.player.Chapter;
import com.example.administrator.player.Player;
import com.example.administrator.skill.towerSkill.Buff;
import com.example.administrator.skill.towerSkill.JewelSkill;

import java.util.ArrayList;

/**
 * 宝石显示类
 */
public class JewelDetailWindow extends PopupWindow{
    private Jewel jewel;
    private ImageResourse ir;
    private View jewelDetailView;
    private Chapter chapter;
    private Player player;
    private Context context;
    private final int TEXT_COLOR = Color.WHITE;
    private CompositeList cl;
    private Drawable default_pic;
    private GameView gameView;
    private SpecialJewelController sjc;
    private ToggleButton hold;
    private Button startAttack;
    private BasicJewelBuilderControl bjb;
    public JewelDetailWindow(Context context, Jewel jewel,ImageResourse ir,GameView gameView,CompositeList cl){
        super(context);
        this.gameView = gameView;
        sjc = new SpecialJewelController();
        bjb = new BasicJewelBuilderControl();
        this.context = context;

        this.cl = cl;
        default_pic = ContextCompat.getDrawable(context,R.mipmap.ic_launcher);
        this.ir = ir;
        this.jewel=jewel;
        jewelDetailView = LayoutInflater.from(context).inflate(R.layout.jewel_detail,null);
        //设置宝石图片
        ImageView jewel_pic = (ImageView)jewelDetailView.findViewById(R.id.jewel_map);
        jewel_pic.setImageBitmap(ir.getImageById(jewel.getId()));
        chapter = gameView.getChapter();
        player = gameView.getPlayer();
        updateDetail();
        setSkills();
        setCompsite();
        setBuffs();
        if (chapter.getJudgeStage()==1){
            setFuceOperation();
        }
        else{
            setHoldButton();
        }
        this.setContentView(jewelDetailView);
    }
    public void updateDetail(){
        setDamage();
        setSpeed();
        setLevel();
        setJewelName();
    }
    public void setJewel(Jewel jewel){
        this.jewel = jewel;
    }
    private void setFuceOperation(){
        if(chapter.getJewel().contains(jewel)){
            switch (chapter.isMix(jewel)) {
                case 0:
                    break;
                case 1:
                    setStayButton();
                    break;
                case 2: {
                    setStayButton();
                    setFuseButton();
                    break;
                }
                case 3: {
                    setStayButton();
                    setFuseButton();
                    Button super_fuse = (Button) jewelDetailView.findViewById(R.id.super_fuse);
                    super_fuse.setVisibility(View.VISIBLE);
                    super_fuse.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (int j = 0; j < 5; j++) {
                                int index3 = player.getJewels().size() - 5 + j;
                                if (isSelectedJewel((Jewel) player.getJewels().get(index3))) {
                                    int x = jewel.getX();
                                    int y = jewel.getY();
                                    player.removeJewel(index3);
                                    BasicJewel bj = (BasicJewel) jewel;
                                    Jewel newjewel = bjb.constructByQuality(bj.getId(), bj.getQuality() + 2, x, y,player.getJewels());
                                    player.addJewel(index3, newjewel);
                                    gameView.drawJewel(newjewel);
                                } else {
                                    player.turnStone(index3);
                                }
                            }
                            gameView.repaintMap();
                            finish();
                        }
                    });
                    break;
                }
            }
        }
    }
    private void finish(){
        chapter.turnJudgeStage();
        gameView.repaintChapter();
        gameView.startChapter();
        gameView.invalidate();
        dismiss();
    }
    private void setFuseButton(){
        Button fuce = (Button) jewelDetailView.findViewById(R.id.fuse);
        fuce.setVisibility(View.VISIBLE);
        fuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int j = 0;j<5;j++){
                    int index3 = player.getJewels().size()-5+j;

                    if(isSelectedJewel((Jewel) player.getJewels().get(index3))){
                        int x = jewel.getX();
                        int y = jewel.getY();
                        player.removeJewel(index3);
                        BasicJewel bj = (BasicJewel)jewel;
                        Jewel newJewel = bjb.constructByQuality(bj.getId(),bj.getQuality()+1,x,y,player.getJewels());
                        player.addJewel(index3,newJewel);
                        gameView.drawJewel(newJewel);
                    }
                    else
                        player.turnStone(index3);
                }
                gameView.repaintMap();
                finish();
            }
        });
    }
    private void setHoldButton(){
        hold = (ToggleButton) jewelDetailView.findViewById(R.id.hold);
        hold.setVisibility(View.VISIBLE);
        hold.setChecked(jewel.isAttack());
        hold.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                jewel.setAttack(isChecked);
            }
        });
    }
    private void setStayButton(){
        Button stay = (Button) jewelDetailView.findViewById(R.id.stay);
        stay.setVisibility(View.VISIBLE);
        stay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int j = 0;j<5;j++){
                    int index3 = player.getJewels().size()-5+j;
                    Jewel jewel = (Jewel) player.getJewels().get(index3);
                    if(!isSelectedJewel(jewel)) {
                        player.turnStone(index3);
                    }
                    else
                        gameView.drawJewel(jewel);
                }
                gameView.repaintMap();
                finish();
            }
        });
    }
    private void setJewelName(){
        //设置宝石名字
        String string_name = jewel.getName();
        if(jewel.getId()>0&&jewel.getId()<9)
            string_name+="("+ jewel.getShortName() + ")";
        TextView jewel_name = (TextView)jewelDetailView.findViewById(R.id.name);
        jewel_name.setTextColor(TEXT_COLOR);
        jewel_name.setText(string_name);
    }
    //设置宝石技能
    private void setSkills(){
        LinearLayout layout = (LinearLayout) jewelDetailView.findViewById(R.id.skills);
        if(jewel.getSkills()!=null) {
            ArrayList skills = jewel.getSkills();
            for (int i = 0; i < skills.size(); i++) {
                ShowSkill showSkill = new ShowSkill(context);
                JewelSkill skill = (JewelSkill) skills.get(i);
                String string_skill = "";
                string_skill+=skill.getSkillName();
                string_skill+="(";
                string_skill+=skill.getSkillLevel();
                string_skill+=")";
                showSkill.setName(string_skill);
                showSkill.setSkill_color(TEXT_COLOR);
                showSkill.setPic(default_pic);
                layout.addView(showSkill);
            }
        }

    }
    //设置等级
    private void setLevel(){
        ImageView level_pic = (ImageView)jewelDetailView.findViewById(R.id.level_pic);
        level_pic.setImageResource(R.mipmap.ic_launcher);
        String string_level = "";
        string_level+=Integer.toString(jewel.getLevel());
        string_level+="  ";
        string_level+=Integer.toString(jewel.getExperience());
        string_level+="%";

        TextView level_text = (TextView)jewelDetailView.findViewById(R.id.level);
        level_text.setTextColor(TEXT_COLOR);
        level_text.setText(string_level);
    }
    //设置伤害
    private void setDamage(){
        ImageView damage_pic = (ImageView)jewelDetailView.findViewById(R.id.damage_pic);
        damage_pic.setImageResource(R.mipmap.ic_launcher);
        String string_damage = "";
        if (jewel==null) {
        }
        string_damage+=Integer.toString(jewel.getDamage());
        if(jewel.getExtraDamage()!=0) {
            string_damage += "+" +Integer.toString(jewel.getExtraDamage());
        }
        TextView damage_text = (TextView)jewelDetailView.findViewById(R.id.damage);
        damage_text.setTextColor(TEXT_COLOR);
        damage_text.setText(string_damage);
    }
    //设置攻速
    private void setSpeed(){
        ImageView speed_pic = (ImageView)jewelDetailView.findViewById(R.id.speed_pic);
        speed_pic.setImageResource(R.mipmap.ic_launcher);
        String string_speed = "";
        string_speed+=Integer.toString(jewel.getSpeed());
        if(jewel.getExtraSpeed()!=0) {
            string_speed += "+" +Integer.toString(jewel.getExtraSpeed());
        }
        TextView speed_text = (TextView)jewelDetailView.findViewById(R.id.speed);
        speed_text.setTextColor(TEXT_COLOR);
        speed_text.setText(string_speed);
    }
    private void setBuffs(){
        LinearLayout layout = (LinearLayout) jewelDetailView.findViewById(R.id.buffs);
        layout.removeAllViews();
        if(jewel.getBuffs()!=null) {
            ArrayList buffs= jewel.getBuffs();
            Log.i("buffs"," "+buffs.size());
            for (int i = 0; i < buffs.size(); i++) {
                Buff buff = (Buff) buffs.get(i);
                int id =buff.getId();
                ImageView buff_pic = new ImageView(context);
                buff_pic.setImageBitmap(ir.getImageById(1));
                layout.addView(buff_pic);
            }
        }
    }
    //添加合成表
    private void setCompsite(){
        LinearLayout layout = (LinearLayout) jewelDetailView.findViewById(R.id.compsite);
        ArrayList compsiteWays = cl.findWay(jewel);
        for(int i =0;i<compsiteWays.size();i++){
            final CompositeWay cw = (CompositeWay)compsiteWays.get(i);

            ShowCompositeWay scw;
            if (chapter.getJudgeStage()==1){
                ArrayList jewels = chapter.getJewel();
                final int[] index = cw.compositeNeed(jewels);

                if(index[0]!=0&&chapter.getJewel().size()==5&&chapter.getJewel().contains(jewel))
                {
                    scw= new ShowCompositeWay(context,cw,ir,true,player.getJewels());
                    scw.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ArrayList<Jewel> materials = new ArrayList<Jewel>();
                            for (int j = 1;j<4;j++){
                                materials.add((Jewel)player.getJewels().get(player.getJewels().size()-5+index[j]));
                            }
                            for (int j = 0;j<5;j++){
                                int index3 = player.getJewels().size()-5+j;
                                Jewel jewel = (Jewel) player.getJewels().get(index3);
                                if(isSelectedJewel(jewel)){
                                    int x = jewel.getX();
                                    int y = jewel.getY();
                                    player.removeJewel(index3);
                                    Jewel newJewel = sjc.construct(materials,cw.getResult_id(),x,y,player.getJewels());
                                    player.addJewel(index3,newJewel);
                                    gameView.drawJewel(newJewel);
                                }
                                else {
                                    player.turnStone(index3);
                                }
                            }
                            gameView.repaintMap();
                            finish();
                        }
                    });
                }
                else{
                    scw= new ShowCompositeWay(context,cw,ir,false,player.getJewels());
                }
            }
            else{
                final int[] index = cw.compositeNeed(player.getJewels());
                if(index[0]==0){
                    continue;
                }
                else{
                    scw= new ShowCompositeWay(context,cw,ir,true,player.getJewels());
                    scw.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ArrayList<Jewel> materials = new ArrayList<Jewel>();
                            for (int j = 1;j<4;j++){
                                Jewel jewel = (Jewel) player.getJewels().get(index[j]);
                                materials.add(jewel);
                                if(isSelectedJewel(jewel)){
                                    int x = jewel.getX();
                                    int y = jewel.getY();
                                    player.removeJewel(j);
                                    Jewel newJewel = sjc.construct(materials,cw.getResult_id(),x,y,player.getJewels());
                                    player.addJewel(j,newJewel);
                                    gameView.drawJewel(newJewel);
                                }
                                else {
                                    player.turnStone(index[j]);
                                }
                            }
                            gameView.repaintMap();
                        }
                    });
                }
            }
            layout.addView(scw);
        }
    }
    public boolean isSame(Jewel jewel){
        if (this.jewel.getX()==jewel.getX()&&this.jewel.getY()==jewel.getY()){
            return true;
        }
        else
            return false;
    }
    public boolean isSelectedJewel(Jewel jewel){
        return jewel.getX()==this.jewel.getX()&&jewel.getY()==this.jewel.getY();
    }
}
