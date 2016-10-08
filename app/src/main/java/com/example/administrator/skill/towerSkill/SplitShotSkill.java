package com.example.administrator.skill.towerSkill;

import com.example.administrator.jewel.Jewel;
import com.example.administrator.jewelshow.Bullet;
import com.example.administrator.monster.Monster;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/1.
 */
public class SplitShotSkill extends JewelSkill {
    private int arrow_number;
    private int arrow_damage;

    public int getArrow_number() {
        return arrow_number;
    }


    public SplitShotSkill(int skillLevel){
        super.setSkillLevel(skillLevel);
        super.setSkillType(3);
        super.setSkillName("分裂箭");
        switch (skillLevel){
            case 1:arrow_damage=80;arrow_number=3;break;
            case 2:arrow_damage=90;arrow_number=3;break;
            case 3:arrow_damage=90;arrow_number=3;break;
        }
    }
    public ArrayList handlerSplitShotSkill(ArrayList monsters, Jewel jewel,int damage) {
        ArrayList bullets = new ArrayList();
        ArrayList list = jewel.getAttack_priority();
        for(int i=0;i<list.size();i++){
            if(i==arrow_number)
                break;
            int monsterIndex = (int)list.get(i);
            Monster monster = (Monster) monsters.get(monsterIndex);
            if(i!=0) {
                damage = damage * arrow_damage / 100;
                Bullet bullet = new Bullet(damage,jewel,monster,System.currentTimeMillis());
                bullets.add(bullet);
            }
            else{
                Bullet bullet = new Bullet(damage,jewel,monster,System.currentTimeMillis());
                bullets.add(bullet);
            }
        }
        return bullets;
    }
}
