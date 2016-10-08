package com.example.administrator.jewel.specialJewel;

import com.example.administrator.jewel.Jewel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/1.
 */
public abstract class SpecialJewelBuilder {
    protected SpecialJewel specialJewel= new SpecialJewel();
    public abstract void buildMaterial_jewel(ArrayList<Jewel> materials);
    public abstract void buildName();
    public abstract void buildDamage();
    public abstract void buildSpeed();
    public abstract void buildSkill();
    public abstract void buildID();
    public abstract void buildRange();
    public void bulidX(int x){
        specialJewel.setX(x);
    }
    public void bulidY(int y){
        specialJewel.setY(y);
    }
    public void buildIsAttack(){
        specialJewel.setAttack(true);
    }
    public void buildBuffs(){
        specialJewel.setBuffs(new ArrayList());
    }
    public void buildLevel(){
        boolean isBreach = false;
        int level = 0;
        int experience = 0;
        for(Jewel jewel:specialJewel.getSynthesisWay()){
            if(jewel.getLevel()>10){
                isBreach =true;
            }
            level += jewel.getLevel();
            experience+=jewel.getExperience();
        }
        level+=experience/100;
        if(level>10){
            if(!isBreach)
                level =10;
        }
        specialJewel.setLevel(level);
        specialJewel.setExperience(experience % 100);
    }
    public void buildExtraDamage(){
        specialJewel.setExtraDamage();
    }
    public void buildLastAttackTime(long time){
        specialJewel.setLast_attcktime(time);
    }
    public void buildAttack_priority(){
        specialJewel.setAttack_priority(new ArrayList());
    }
    public void buildExtraSpeed(){
        this.specialJewel.setExtraSpeed();
    }
    public SpecialJewel createSpecialTower(){
        return specialJewel;
    }
}
