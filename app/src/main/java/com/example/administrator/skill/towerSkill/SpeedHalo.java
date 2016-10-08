package com.example.administrator.skill.towerSkill;

/**
 * Created by Administrator on 2016/5/1.
 */
public class SpeedHalo extends Halo {
    private int speedPct;
    public SpeedHalo(int haloLevel){
        super.setSkillLevel(haloLevel);
        super.setHaloNumber(2);
        setHaloRange(500);
        setSkillName("攻速光环");
        setSkillType(2);
        switch (haloLevel){
            case 1:speedPct=10;break;
            case 2:speedPct=20;break;
            case 3:speedPct=25;break;
            case 4:speedPct=30;break;
            case 5:speedPct=40;break;
            case 6:speedPct=50;break;
            case 7:speedPct=200;break;
        }
    }

    public int getSpeedPct() {
        return speedPct;
    }
}