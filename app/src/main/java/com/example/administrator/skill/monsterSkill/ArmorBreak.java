package com.example.administrator.skill.monsterSkill;

import com.example.administrator.jewel.Jewel;
import com.example.administrator.monster.Monster;
import com.example.administrator.skill.towerSkill.ArmorBreakSkill;

/**
 * Created by Administrator on 2016/9/22.
 */
public class ArmorBreak extends DeBuff {
    private int armorBreakEffect;//减甲效果
    private boolean haveBreak;//判断是否已经减甲
    public ArmorBreak(ArmorBreakSkill abs, Jewel jewel){
        this.id = 2;
        haveBreak=false;
        this.setJewel(jewel);
        this.startTime = System.currentTimeMillis();
        this.remainTime =abs.getArmorBreak_time();
        this.armorBreakEffect = abs.getArmorBreak_number();
        this.level = abs.getSkillLevel();
    }

    public int getArmorBreakEffect() {
        return armorBreakEffect;
    }

    public void setArmorBreakEffect(int decelerationEffect) {
        this.armorBreakEffect = decelerationEffect;
    }
    public void handlerDebuff(Monster monster) {
        if(!haveBreak){
            int armor = monster.getArmor()-armorBreakEffect;
            monster.setArmor(armor);
            haveBreak=true;
        }
    }
    public void removeDebuff(Monster monster) {
        if(haveBreak){
            int armor = monster.getArmor()+armorBreakEffect;
            monster.setArmor(armor);
        }
    }
}
