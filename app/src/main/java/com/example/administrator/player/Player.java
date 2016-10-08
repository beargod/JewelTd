package com.example.administrator.player;

import com.example.administrator.jewel.Jewel;
import com.example.administrator.jewel.Stone;
import com.example.administrator.jewelshow.GameView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/15.
 */
//玩家
public class Player implements Serializable{
    private String name;//昵称
    private int level;//玩家等级
    private int experience;//玩家经验
    private int money;//金币
    private transient GameView gameView;
    private int hpUpper;//血量上限
    private int castleHP;//当前血量
    private int checkpoint;//当前关卡
    private boolean isDayOrNight;//true为白天false为晚上
    private ArrayList jewels;//玩家的宝石
    private ArrayList stones;//玩家的石头
    public Player(GameView gameView){
        this.gameView=gameView;
        setLevel(1);
        setExperience(0);
        setMoney(100);
        setCastleHP(100);
        setIsDayOrNight(true);
        setCheckpoint(1);
        setName("测试号");
        hpUpper=100;
        jewels = new ArrayList<Jewel>();
        stones = new ArrayList<Stone>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHpUpper() {
        return hpUpper;
    }

    public void setHpUpper(int hpUpper) {
        this.hpUpper = hpUpper;
    }

    public ArrayList getStones() {
        return stones;
    }

    public ArrayList getJewels() {
        return jewels;
    }
    public void addJewel(Jewel tower){
        this.jewels.add(tower);
        updateHalo();
    }
    public void updateHalo (){
        for(int i =0;i<jewels.size();i++) {
            Jewel jewel = (Jewel) jewels.get(i);
            jewel.handleHalo(jewels);
        }
    }
    public void addJewel(int index,Jewel tower){
        this.jewels.add(index,tower);
        updateHalo();
    }
    public boolean addExperience(int experience){
        boolean isLevelUp=false;
        if(level<5) {
            this.experience += experience;
            if (this.experience >= 100) {
                isLevelUp=true;
                if(level==4)
                    this.experience=0;
                else
                    this.experience -= 100;
                this.level += 1;
            }
        }
        return isLevelUp;
    }
    public void removeJewel(int index){
        this.jewels.remove(index);
        updateHalo();
    }
    public void removeStone(int index){
        this.stones.remove(index);
    }
    public void turnStone(int index){
        Jewel jewel = (Jewel) jewels.get(index);
        int x = jewel.getX();
        int y = jewel.getY();
        removeJewel(index);
        Stone stone = new Stone(x,y);
        stones.add(stone);
    }
    public void turnNewJewel(){

    }
    public int getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(int checkpoint) {
        this.checkpoint = checkpoint;
    }

    public boolean isDayOrNight() {
        return isDayOrNight;
    }

    public void setIsDayOrNight(boolean isDayOrNight) {
        this.isDayOrNight = isDayOrNight;
    }

    public int getCastleHP() {
        return castleHP;
    }

    public void setCastleHP(int castleHP) {
        this.castleHP = castleHP;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
