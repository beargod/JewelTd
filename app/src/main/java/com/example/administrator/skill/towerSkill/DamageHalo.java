package com.example.administrator.skill.towerSkill;

/**
 * Created by Administrator on 2016/4/14.
 */
public class DamageHalo extends Halo {
    private int damagePct;
    public DamageHalo(int haloLevel){
        super.setSkillLevel(haloLevel);
        super.setHaloNumber(1);
        setSkillName("攻击光环");
        setSkillType(2);
        setHaloRange(500);
        switch (haloLevel){
            case 1:damagePct=10;break;
            case 2:damagePct=20;break;
            case 3:damagePct=25;break;
            case 4:damagePct=30;break;
            case 5:damagePct=40;break;
            case 6:damagePct=50;break;
            case 7:damagePct=200;break;
        }
    }

    public int getDamagePct() {
        return damagePct;
    }


}
