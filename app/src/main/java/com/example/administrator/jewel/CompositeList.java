package com.example.administrator.jewel;

import java.util.ArrayList;

/**
 * 合成表类
 */
public class CompositeList {
    private ArrayList<CompositeWay> compositeWays;
    //1.红宝石2.黄宝石3.蓝宝石4.紫晶5.翡翠6.蛋白石7.钻石8.海蓝石9.孔雀 10.白银 11.星彩
    private String[] composite_strs={
            "1 1 1 2 4 1 11",//星彩
            "2 1 3 1 7 1 10",//白银
            "5 1 6 1 8 1 9"//孔雀
    };
    public CompositeList() {
        compositeWays = new ArrayList<CompositeWay>();
       for (String s:composite_strs){
           setCompositeWays(s);
       }
    }
    public ArrayList findWay(Jewel Jewel){
        ArrayList ways = new ArrayList<CompositeWay>();
        for(CompositeWay cw:compositeWays){
            if(cw.isContainJewel(Jewel))
                ways.add(cw);
        }
        return ways;
    }
    public void setCompositeWays(String composite_str){
        String[] composites = composite_str.split(" ");
        int length = composites.length;
        int result_ID =Integer.parseInt(composites[length-1]);
        String[] materials = new String[length-1];
        for (int i =0;i<length-1;i++){
            materials[i] = composites[i];
        }
        CompositeWay cw = new CompositeWay(materials,result_ID);
        compositeWays.add(cw);
    }

    public ArrayList<CompositeWay> getCompositeWays() {
        return compositeWays;
    }
}
