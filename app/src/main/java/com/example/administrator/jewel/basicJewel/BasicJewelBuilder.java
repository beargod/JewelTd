package com.example.administrator.jewel.basicJewel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/18.
 */
public abstract class BasicJewelBuilder {
    protected BasicJewel basicJewel= new BasicJewel();
    public abstract  void buildName();
    public abstract  void buildShortName();
    public abstract  void buildDamage();
    public abstract  void buildSpeed();
    public abstract  void buildSkill();
    public abstract  void buildID();
    public abstract  void buildRange();
    public void bulidX(int x){
        basicJewel.setX(x);
    }
    public void bulidY(int y){
        basicJewel.setY(y);
    }
    public void buildLevel(){
        basicJewel.setLevel(0);
    }
    public void buildExperience(){
        basicJewel.setExperience(0);
    }
    public void buildAttack_priority(){
        basicJewel.setAttack_priority(new ArrayList());
    }
    public void buildBuffs(){
        basicJewel.setBuffs(new ArrayList());
    }
    public void buildLastAttackTime(long time){
        basicJewel.setLast_attcktime(time);
    }
    public void buildIsAttack(){
        basicJewel.setAttack(true);
    }
    public String addName(int quality){
        String name = "";
        switch (quality){
            case 1:name+= "破碎的";break;
            case 2:name+= "瑕疵的";break;
            case 3:name+= "普通的";break;
            case 4:name+= "无暇的";break;
            case 5:name+= "完美的";break;
            case 6:name+= "巨型的";break;
        }
        return name;
    }
    public void buildExtraDamage(){
        basicJewel.setExtraDamage();
    }
    public void buildExtraSpeed(){
        this.basicJewel.setExtraSpeed();
    }
    public BasicJewel createBasicJewel(){
        return basicJewel;
    }
}
