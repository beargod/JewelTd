package com.example.administrator.monster;

import com.example.administrator.skill.monsterSkill.DeBuff;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/31.
 */
public class Monster{
    private String name;//怪物名字
    private int x;//
    private int y;
    private int id;//编号
    private int hp;//血量
    private int indexOfAttackJewel;//攻击宝石的下标
    private boolean isFirstAttack;
    private int current_hp;//当前血量
    private int defaultSpeed;//基础行走速度
    private int speed;//当前行走速度
    private float way_length;//行走距离
    private boolean isAlive;//是否活着
    private ArrayList skills;//技能
    private int armor;//护甲
    private int defaultArmor;//基础护甲
    private int defaultMk;//基础魔抗
    private int mk;//魔抗
    private ArrayList debuffs;//状态

    public int getArmor() {
        return armor;
    }

    public int getIndexOfAttackJewel() {
        return indexOfAttackJewel;
    }

    public void setIndexOfAttackJewel(int indexOfAttackJewel) {
        this.indexOfAttackJewel = indexOfAttackJewel;
    }

    public boolean isFirstAttack() {
        return isFirstAttack;
    }

    public void setFirstAttack(boolean firstAttack) {
        isFirstAttack = firstAttack;
    }

    public boolean beAttacked(int damage){
        this.current_hp-=damage;
        if(current_hp<=0){
            setAlive(false);
        }
        return current_hp<= 0;
    }

    public int getDefaultArmor() {
        return defaultArmor;
    }

    public void setDefaultArmor(int defaultArmor) {
        this.defaultArmor = defaultArmor;
    }

    public int getDefaultMk() {
        return defaultMk;
    }

    public void setDefaultMk(int defaultMk) {
        this.defaultMk = defaultMk;
    }

    public int getDefaultSpeed() {
        return defaultSpeed;
    }

    public void setDefaultSpeed(int defaultSpeed) {
        this.defaultSpeed = defaultSpeed;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public float getWay_length() {
        return way_length;
    }

    public void setWay_length(float way_length) {
        this.way_length = way_length;
    }

    public ArrayList getDebuffs() {
        return debuffs;
    }

    public int getCurrent_hp() {
        return current_hp;
    }

    public void setCurrent_hp(int current_hp) {
        this.current_hp = current_hp;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setDebuffs(ArrayList debuffs) {
        this.debuffs = debuffs;
    }
    public void addDebuff(DeBuff deBuff){
        debuffs.add(deBuff);
    }
    public void removeDebuff(int i ){
        debuffs.remove(i);
    }
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMk() {
        return mk;
    }

    public void setMk(int mk) {
        this.mk = mk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList getSkills() {
        return skills;
    }

    public void setSkills(ArrayList skills) {
        this.skills = skills;
    }

    public int getSpeed() {
        return speed;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public void move(int speedUpNum,int time){
        this.way_length+=speed*time/50/50*speedUpNum;
    }
    public void handlerDebuffs(long current_time){
        synchronized (this) {
            if (debuffs != null) {
                for (int i = 0; i < debuffs.size(); i++) {
                    DeBuff deBuff = (DeBuff) debuffs.get(i);
                    if (!deBuff.judgeTime(current_time))
                        deBuff.handlerDebuff(this);
                    else {
                        deBuff.removeDebuff(this);
                        debuffs.remove(i);
                    }
                }
            }
        }
    }
}
