package com.example.administrator.skill.towerSkill;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/13.
 */
public abstract class JewelSkill implements Serializable{
    private int skillType;//1：攻击类技能2：光环类技能3.分裂箭
    private int skillLevel;//技能等级
    private String skillName;//技能名字

    public String getSkillName() {
        return skillName;
    }

    public int getSkillType() {
        return skillType;
    }

    public void setSkillType(int skillType) {
        this.skillType = skillType;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public int getSkillLevel() {
        return skillLevel;
    }
    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }
    /*
    1.溅射 2.分裂 3.减速 4.破甲 5.毒液
     */
}
