package com.example.administrator.jewel.basicJewel;

import com.example.administrator.skill.towerSkill.JewelSkill;
import com.example.administrator.skill.towerSkill.SplitShotSkill;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/1.
 */
public class YellowJewelBuilder extends BasicJewelBuilder{
    private int quality;
    public YellowJewelBuilder(int quality){
        this.quality = quality;
        super.basicJewel.setQuality(quality);
    }
    public void buildSkill(){
        ArrayList skills = new ArrayList<JewelSkill>();
        JewelSkill skill = new SplitShotSkill(1);
        skills.add(skill);
        super.basicJewel.setSkills(skills);
    }
    public void buildDamage(){
        int damage = 1;
        switch (quality){
            case 1:damage = 4;break;
            case 2:damage = 8;break;
            case 3:damage = 16;break;
            case 4:damage = 32;break;
            case 5:damage = 64;break;
            case 6:damage = 128;break;
        }
        super.basicJewel.setDamage(damage);
    }
    public void buildSpeed(){
        int speed = 100;
        super.basicJewel.setSpeed(speed);
    }
    public void buildName(){
        String name=super.addName(quality);
        name +="黄宝石";
        super.basicJewel.setName(name);
    }
    public void buildShortName(){
        String name="Y";
        name+=quality;
        super.basicJewel.setShortName(name);
    }
    public void buildID(){
        super.basicJewel.setId(2);
    }
    public void buildRange(){
        super.basicJewel.setRange(1000);
    }
}
