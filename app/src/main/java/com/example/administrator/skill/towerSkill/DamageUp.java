package com.example.administrator.skill.towerSkill;

/**
 * Created by Administrator on 2016/9/24.
 */
public class DamageUp extends Buff{
    private int effect;
    public DamageUp(DamageHalo halo){
        this.effect = halo.getDamagePct();
        this.setId(halo.getHaloNumber());
        this.setLevel(halo.getSkillLevel());
    }

    public int getEffect() {
        return effect;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }
}
