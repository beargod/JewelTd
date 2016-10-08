package com.example.administrator.monster;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/31.
 */
public class MonsterBuilderControl{
    MonsterBuilder mb;
    public Monster construct(int id){
        mb = new MonsterBuilder();
        ArrayList skills =new ArrayList();
        mb.buildName("测试怪");
        mb.buildWay_length(0);
        mb.buildX(500);
        mb.buildY(500);
        mb.buildHp(15);
        mb.buildCurrent_hp(15);
        mb.buildMk(10);
        mb.buildDefaultMk(10);
        mb.buildId(id);
        mb.buildIsAlive();
        mb.buildIsFirstAttack(false);
        mb.buildSpeed(500);
        mb.buildDefalutSpeed(500);
        mb.buildDefaultArmor(2);
        mb.buildArmor(2);
        mb.buildSkills(skills);
        return mb.creatMonster();
    }
}
