package com.example.administrator.skill.monsterSkill;

import com.example.administrator.jewel.Jewel;
import com.example.administrator.monster.Monster;

/**
 * Created by Administrator on 2016/8/31.
 */
public abstract class DeBuff {
    //1.减速
    String name;//名字
    int id;//编号
    long startTime;//开始时间
    int remainTime;//持续时间
    int level;//状态等级
    Jewel jewel;//来源的宝石

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(int remainTime) {
        this.remainTime = remainTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public Jewel getJewel() {
        return jewel;
    }

    public void setJewel(Jewel jewel) {
        this.jewel = jewel;
    }

    public abstract void handlerDebuff(Monster monster);
    public abstract void removeDebuff(Monster monster);
    public boolean judgeTime(long time){
        return(time-startTime)/1000>remainTime;
    }
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
