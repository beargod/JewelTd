package com.example.administrator.skill.towerSkill;

import com.example.administrator.jewel.Jewel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/14.
 */
public abstract class Halo extends JewelSkill{
    private int haloNumber;//光环编号

    private int haloRange;

    public int getHaloNumber() {
        return haloNumber;
    }

    public void setHaloNumber(int haloNumber) {
        this.haloNumber = haloNumber;
    }

    public int getHaloRange() {
        return haloRange;
    }

    public void addBuff(ArrayList jewels, Jewel jewel){
        for(int i =0;i<jewels.size();i++){
            Jewel jewel1 = (Jewel)jewels.get(i);
            if(isInRange(jewel1,jewel)){
                addBuff(jewel1);
            }
        }
    }
    public void addBuff(Jewel jewel){
        boolean isContain = false;
        ArrayList buffs = jewel.getBuffs();
        synchronized (buffs) {
            for (int j = 0; j < buffs.size(); j++) {
                Buff buff = (Buff) buffs.get(j);
                if ((buff.getId() == getHaloNumber())&&
                        (buff.getLevel() == this.getSkillLevel())) {
                    isContain=true;
                    break;
                }
            }
            if(!isContain ){
                switch (haloNumber) {
                    case 1:
                        DamageHalo dhalo = (DamageHalo)this;
                        jewel.addBuff(new DamageUp(dhalo));
                        jewel.setExtraDamage();
                        break;
                    case 2:SpeedHalo shalo = (SpeedHalo) this;
                        jewel.addBuff(new SpeedUp(shalo));
                        jewel.setExtraSpeed();
                        break;
                }
            }
        }

    }
    public boolean isInRange(Jewel jewel1,Jewel jewel2){
        if(jewel1==null||jewel2==null) {
            return false;
        }
        else {
            float x = (jewel1.getX()-jewel2.getX())*100;
            float y = (jewel1.getY()-jewel2.getY())*100;
            if(x*x+y*y<haloRange*haloRange){
                return true;
            }
            else
                return false;
        }
    }

    public void setHaloRange(int haloRange) {
        this.haloRange = haloRange;
    }
}
