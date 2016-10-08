package com.example.administrator.skill.towerSkill;

/**
 * Created by Administrator on 2016/9/24.
 */
public class SpeedUp extends Buff{
    private int effect;
    public SpeedUp(SpeedHalo halo){
        this.effect = halo.getSpeedPct();
        this.setId(halo.getHaloNumber());
        this.setLevel(halo.getSkillLevel());
    }

    public int getEffect() {
        return effect;
    }
}
