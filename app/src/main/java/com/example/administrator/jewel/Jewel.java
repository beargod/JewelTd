package com.example.administrator.jewel;

import com.example.administrator.jewelshow.Bullet;
import com.example.administrator.monster.Monster;
import com.example.administrator.skill.towerSkill.Buff;
import com.example.administrator.skill.towerSkill.CritSkill;
import com.example.administrator.skill.towerSkill.DamageUp;
import com.example.administrator.skill.towerSkill.Halo;
import com.example.administrator.skill.towerSkill.JewelSkill;
import com.example.administrator.skill.towerSkill.SpeedUp;
import com.example.administrator.skill.towerSkill.SplitShotSkill;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/18.
 */
public abstract class Jewel implements Serializable{
    private String name;//名字
    private String shortName;//缩略名
    private long last_attcktime;//上一次攻击的时间
    private int x;//x坐标
    private int y;//y坐标
    private boolean isAttack;//是否攻击
    private ArrayList attack_priority;//攻击优先级
    private int damage;//伤害
    private int range;//攻击范围
    private int extraDamage;//额外伤害
    private int speed;//攻击速度
    private int extraSpeed;//额外攻速
    private int level;//等级
    private int experience;//经验
    private int id;//编号  1.红宝石2.黄宝石3.蓝宝石4.紫晶5.翡翠6.蛋白石7.钻石8.海蓝石
    private ArrayList<JewelSkill> skills;
    /*技能:1.溅射 2.暴击 3.毒 4.减甲 5.攻速光环 6.克敌机先 7.减速 8.分裂攻击LV1 9.分裂攻击LV2
    10.分裂攻击LV3 11.攻击光环 12.加钱 。。。。。。*/
    private ArrayList<Buff> buffs;//光环状态

    public long getLast_attcktime() {
        return last_attcktime;
    }

    public void setLast_attcktime(long last_attcktime) {
        this.last_attcktime = last_attcktime;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }
    public void addPriority(int index){
        attack_priority.add(index);
    }
    public void addPriority(int index,int i){
        attack_priority.add(index,i);
    }
    public void removePriority(int index){
        attack_priority.remove(index);
    }
    public ArrayList getAttack_priority() {
        return attack_priority;
    }

    public void setAttack_priority(ArrayList attack_priority) {
        this.attack_priority = attack_priority;
    }
    public void clearAttackPriority(){
        attack_priority.clear();
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getExtraDamage() {
        setExtraDamage();
        return extraDamage;
    }

    public void setExtraDamage() {
        extraDamage = 0;
        int sumOfDamagePct = 0;
        if(buffs!=null) {
            for (int i = 0; i < buffs.size(); i++) {
                Buff b = buffs.get(i);
                if (b.getId() == 1) {
                    DamageUp du = (DamageUp) b;
                    sumOfDamagePct += du.getEffect();
                }
            }
        }
        sumOfDamagePct+=level*10;
        extraDamage = damage*sumOfDamagePct/100;
    }
    public int getExtraSpeed() {
        setExtraSpeed();
        return extraSpeed;
    }

    public void setExtraSpeed() {
        extraSpeed= 0;
        int sumOfDamagePct = 0;
        if(buffs!=null) {
            for (int i = 0; i < buffs.size(); i++) {
                Buff b = buffs.get(i);
                if (b.getId() == 2) {
                    SpeedUp su = (SpeedUp) b;
                    sumOfDamagePct += su.getEffect();
                }
            }
        }
        extraSpeed = speed*sumOfDamagePct/100;
    }

    public ArrayList<Buff> getBuffs() {
        return buffs;
    }

    public void setBuffs(ArrayList<Buff> buffs) {
        this.buffs = buffs;
    }


    public boolean isAttack() {
        return isAttack;
    }

    public void setAttack(boolean attack) {
        isAttack = attack;
    }

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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public ArrayList<JewelSkill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<JewelSkill> skills) {
        this.skills = skills;
    }
    public int getSpeed() {
        return speed;
    }
    public boolean addExperience(int number){
        boolean isLevelUp=false;
        experience+=number;
        if(experience>=100){
            level++;
            experience-=100;
            isLevelUp=true;
        }
        return isLevelUp;
    }
    public void handleHalo(ArrayList jewels){
        buffs.clear();
        for(int i =0;i<jewels.size();i++){
            Jewel jewel = (Jewel)jewels.get(i);
            ArrayList jSkills = jewel.getSkills();
            for(int j =0;j<jSkills.size();j++){
                JewelSkill skill = (JewelSkill) jSkills.get(j);
                if(skill.getSkillType()==2) {
                    Halo halo = (Halo)skill;
                    if(halo.isInRange(this,jewel))
                        halo.addBuff(this);
                }
            }
        }
    }
    public void addBuff(Buff buff){
        buffs.add(buff);
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public ArrayList makeBullet(ArrayList monsters){
        int totalDamage = damage+extraDamage;
        SplitShotSkill sss=null;
        CritSkill cs = null;
        ArrayList bullets = new ArrayList();
        if(skills!=null){
            for(int i = 0;i<skills.size();i++){
                JewelSkill skill = skills.get(i);
                if(skill.getSkillType()==4){
                    cs=(CritSkill)skill;
                }
                if(skill.getSkillType()==3) {
                    sss = (SplitShotSkill)skill;
                }
            }
        }
        if(cs!=null){
            totalDamage = cs.getCritDamage(totalDamage);
        }
        if(attack_priority.size()>0)
            bullets.add(new Bullet(totalDamage,this,(Monster) monsters.
                    get((int)attack_priority.get(0)),System.currentTimeMillis()));
        if(sss!=null){
            bullets = sss.handlerSplitShotSkill(monsters,this,totalDamage);
        }
        return  bullets;
    }
}
