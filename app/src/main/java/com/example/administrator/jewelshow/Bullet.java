package com.example.administrator.jewelshow;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.administrator.jewel.Jewel;
import com.example.administrator.monster.Monster;
import com.example.administrator.skill.towerSkill.AttackSkill;
import com.example.administrator.skill.towerSkill.JewelSkill;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/2.
 */
public class Bullet {
    private Jewel startJewel;
    private Monster goal_monster;
    long last_time;//持续时间
    long start_time;//开始的时间
    private int damage;
    public Bullet(int damage,Jewel startJewel,Monster monster,long start_time) {
        this.damage=damage;
        this.goal_monster=monster;
        this.startJewel=startJewel;
        this.last_time = 500;
        this.start_time = start_time;
    }

    public boolean handlerAttack(ArrayList monsters){
        ArrayList<JewelSkill> skills = startJewel.getSkills();
        int index = monsters.indexOf(goal_monster);
        if(skills!=null){
            for(int i = 0;i<skills.size();i++){
                JewelSkill skill = skills.get(i);
                if(skill.getSkillType()==1) {
                    AttackSkill aSkill = (AttackSkill)skill;
                    aSkill.handlerSkill(monsters,index,startJewel);
                }
            }
        }
        int finalDamage = (int)(damage*
                (1-(goal_monster.getArmor()*0.06/(1+goal_monster.getArmor()))));
        return goal_monster.beAttacked(finalDamage);
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Jewel getStartJewel() {
        return startJewel;
    }

    public Monster getGoalMonster() {
        return goal_monster;
    }
    public long getStart_time() {
        return start_time;
    }

    public long getLast_time() {
        return last_time;
    }
    public void drawAttackLine(Canvas canvas, Paint paint){
        canvas.drawLine(startJewel.getX()*100-50,startJewel.getY()*100-50,goal_monster.getX()+50,goal_monster.getY()+50,paint);
    }
}
