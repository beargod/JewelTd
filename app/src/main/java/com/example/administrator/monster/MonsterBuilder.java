package com.example.administrator.monster;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/31.
 */
public class MonsterBuilder implements Serializable{
    private Monster monster;
    public MonsterBuilder(){
        monster = new Monster();
        monster.setDebuffs(new ArrayList());
    }
    public void buildX(int x){}
    public void buildY(int y){}
    public void buildWay_length(int length){
        monster.setWay_length(length);
    }
    public void buildCurrent_hp(int hp){
        monster.setCurrent_hp(hp);
    }
    public void buildIndex(int index){
        monster.setIndexOfAttackJewel(index);
    }
    public void buildDefaultArmor(int armor){
        monster.setDefaultArmor(armor);
    }
    public void buildDefaultMk(int Mk){
        monster.setDefaultMk(Mk);
    }
    public void buildIsAlive(){
        monster.setAlive(true);
    }
    public void buildDefalutSpeed(int speed){
        monster.setDefaultSpeed(speed);
    }
    public void buildName(String name){
        monster.setName(name);
    }
    public void buildId(int id){
        monster.setId(id);
    }
    public void buildHp(int hp){
        monster.setHp(hp);
    }
    public void buildSpeed(int speed){
        monster.setSpeed(speed);
    }
    public void buildArmor(int armor){
        monster.setArmor(armor);
    }
    public void buildMk(int mk){
        monster.setMk(mk);
    }
    public void buildIsFirstAttack(boolean is){
        monster.setFirstAttack(is);
    }
    public void buildSkills(ArrayList skills){
        monster.setSkills(skills);
    }
    public Monster creatMonster(){
        return monster;
    }
}
