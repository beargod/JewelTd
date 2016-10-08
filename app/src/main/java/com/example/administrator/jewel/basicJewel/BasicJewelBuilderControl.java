package com.example.administrator.jewel.basicJewel;

import java.util.ArrayList;

/**
 * 基础宝石建造者控制类
 */
public class BasicJewelBuilderControl {
    private int quality;//品质
    private int basicjewel_type;//基础宝石类型
    private int player_level;//玩家等级
    public BasicJewelBuilderControl(){
        this.player_level=1;
    }
    public BasicJewel construct(int x, int y, ArrayList jewels){
        BasicJewelBuilder bjb;
        BasicJewel bj;
        bjb = new RedJewelBuilder(quality);
        switch (basicjewel_type){
            case 1:bjb = new RedJewelBuilder(quality);break;
            case 2:bjb = new YellowJewelBuilder(quality);break;
            case 3:bjb = new BlueJewelBuilder(quality);break;
            case 4:bjb = new PurpleJewelBuilder(quality);break;
            case 5:bjb = new GreenJewelBuilder(quality);break;
            case 6:bjb = new EggJewelBuilder(quality);break;
            case 7:bjb = new DiamondsBuilder(quality);break;
            case 8:bjb = new AquamarineBuilder(quality);break;
        }
        bjb.buildDamage();
        bjb.buildID();
        bjb.buildLevel();
        bjb.buildName();
        bjb.buildSkill();
        bjb.buildSpeed();
        bjb.bulidX(x);
        bjb.bulidY(y);
        bjb.buildExperience();
        bjb.buildRange();
        bjb.buildShortName();
        bjb.buildAttack_priority();
        bjb.buildLastAttackTime(0);
        bjb.buildExtraDamage();
        bjb.buildIsAttack();
        bjb.buildExtraSpeed();
        bjb.buildBuffs();
        bj = bjb.createBasicJewel();
        return bj;
    }
    public BasicJewel constructByLevel(int player_level,int x,int y,ArrayList jewels) {
        this.player_level=player_level;
        setQuality();
        setType();
        return construct(x, y,jewels);
    }
    public BasicJewel constructByQuality(int id,int quality,int x,int y,ArrayList jewels) {
        this.basicjewel_type = id;
        this.quality=quality;
        return construct(x, y,jewels);
    }
    //设置基础宝石的品质
    public void setPlayer_level(int level){
        player_level=level;
    }
    private void setType() {
        int type_num = 8;//基础宝石的种类数
        basicjewel_type = (int)(Math.random()*(type_num-1)+1);
    }
    //设置基础宝石类型
    private void setQuality(){
        quality = 0;
        int chance_level1 = 0;
        int chance_level2 = 0;
        int chance_level3 = 0;
        int chance_level4 = 0;
        int chance_level5 = 0;
        switch (player_level){
            case 1:chance_level1=100;break;
            case 2:chance_level1=60;chance_level2=40;break;
            case 3:chance_level1 = 20;chance_level2=50;chance_level3=30;break;
            case 4:chance_level1 = 20;chance_level2=30;chance_level3=30;chance_level4=20;break;
            case 5:chance_level1 = 10;chance_level2=25;chance_level3=30;chance_level4=25;chance_level5=10;break;
        }
        double random = Math.random()*100;
        if(random>=0&&random<chance_level1){
            quality = 1;
        }
        else if (random>=chance_level1&&random<chance_level1+chance_level2){
            quality = 2;
        }
        else if (random>=chance_level1+chance_level2&&random<chance_level1+chance_level2+chance_level3){
            quality = 3;
        }
        else if (random>=chance_level1+chance_level2+chance_level3&&random<chance_level1+chance_level2+chance_level3+chance_level4){
            quality = 4;
        }else if (random>=chance_level1+chance_level2+chance_level3+chance_level4&&random<chance_level1+chance_level2+chance_level3+chance_level4+chance_level5){
            quality= 5 ;
        }
    }
}

