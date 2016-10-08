package com.example.administrator.jewelshow;

/**
 * 游戏工具类
 */
public class GameTools {

    public String getNameById(int id){
        String name = "";
        //1.红宝石 2.黄宝石 3.蓝宝石 4.紫晶 5.翡翠 6.蛋白石 7.钻石 8.海蓝石 9.孔雀 10.白银 11.星彩
        switch (id){
            case 1:name+="巨型的红宝石";break;
            case 2:name+="巨型的黄宝石";break;
            case 3:name+="巨型的蓝宝石";break;
            case 4:name+="巨型的紫晶";break;
            case 5:name+="巨型的翡翠";break;
            case 6:name+="巨型的蛋白石";break;
            case 7:name+="巨型的钻石";break;
            case 8:name+="巨型的海蓝石";break;
            case 9:name+="孔雀";break;
            case 10:name+="白银";break;
            case 11:name+="星彩";break;
        }
        return name;
    }
    public String getNameById(int id,int quality){
        String name = "";
        //1.红宝石 2.黄宝石 3.蓝宝石 4.紫晶 5.翡翠 6.蛋白石 7.钻石 8.海蓝石 9.孔雀 10.白银 11.星彩
        switch (quality){
            case 1:name+= "破碎的";break;
            case 2:name+= "瑕疵的";break;
            case 3:name+= "普通的";break;
            case 4:name+= "无暇的";break;
            case 5:name+= "完美的";break;
            case 6:name+= "巨型的";break;
        }
        switch (id){
            case 1:name+="红宝石";break;
            case 2:name+="黄宝石";break;
            case 3:name+="蓝宝石";break;
            case 4:name+="紫晶";break;
            case 5:name+="翡翠";break;
            case 6:name+="蛋白石";break;
            case 7:name+="钻石";break;
            case 8:name+="海蓝石";break;
        }
        return name;
    }
    public String getShortById(int id,int quality){
        String name = "";
        //1.红宝石 2.黄宝石 3.蓝宝石 4.紫晶 5.翡翠 6.蛋白石 7.钻石 8.海蓝石 9.孔雀 10.白银 11.星彩
        switch (id){
            case 1:name+="R";break;
            case 2:name+="Y";break;
            case 3:name+="B";break;
            case 4:name+="P";break;
            case 5:name+="G";break;
            case 6:name+="E";break;
            case 7:name+="D";break;
            case 8:name+="Q";break;
        }
        name+=quality;
        return name;
    }
    public int toInt(String s){
        return Integer.parseInt(s);
    }
}
