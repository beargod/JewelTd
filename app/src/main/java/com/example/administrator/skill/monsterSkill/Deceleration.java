package com.example.administrator.skill.monsterSkill;

import com.example.administrator.jewel.Jewel;
import com.example.administrator.monster.Monster;
import com.example.administrator.skill.towerSkill.DecelerationSkill;

/**
 * Created by Administrator on 2016/9/22.
 */
public class Deceleration extends DeBuff{
    private int decelerationEffect;//减速效果
    private boolean haveDeceleration;
    private int inFactDecelerationEffect;//实际减少的效果
    public Deceleration(DecelerationSkill ds, Jewel jewel){
        this.id = 1;
        haveDeceleration=false;
        this.setJewel(jewel);
        inFactDecelerationEffect=-1;
        this.startTime = System.currentTimeMillis();
        this.remainTime = ds.getDeceleration_time();
        this.decelerationEffect = ds.getDeceleration_number();
        this.level = ds.getSkillLevel();
    }

    public int getDecelerationEffect() {
        return decelerationEffect;
    }

    public void setDecelerationEffect(int decelerationEffect) {
        this.decelerationEffect = decelerationEffect;
    }
    public void handlerDebuff(Monster monster) {
        if(!haveDeceleration){
            int speed = monster.getSpeed()-decelerationEffect;
            if(speed>=200)
                monster.setSpeed(speed);
            else {
                inFactDecelerationEffect = monster.getSpeed()-200;
                monster.setSpeed(200);
            }
            haveDeceleration=true;
        }
    }
    public void removeDebuff(Monster monster) {
        if(haveDeceleration){
            int speed;
            if(inFactDecelerationEffect!=-1)
                speed = monster.getSpeed()+inFactDecelerationEffect;
            else
                speed = monster.getSpeed()+decelerationEffect;

            monster.setSpeed(speed);
        }
    }
}
