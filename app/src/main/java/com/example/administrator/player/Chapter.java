package com.example.administrator.player;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.administrator.jewel.Jewel;
import com.example.administrator.monster.Monster;
import com.example.administrator.monster.MonsterBuilderControl;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 关卡类
 */
public class Chapter implements Serializable{
    private int judgeStage;//游戏阶段   1；放置石头 2；抵挡怪物
    private transient ArrayList monsters;//怪物
    private int numOfMonster;//已经产生怪物的数量
    private ArrayList[] partWay;//部分的路线
    private int[][] jewel_location ;
    private int stageNum;//关卡编号
    private ArrayList way;//怪物的行走总路线
    private long way_length;//路线长度
    private long game_time;//回合开始的时间

    private int[][] move_point;//移动的点
    private transient MonsterBuilderControl mbc;
    private long spacingOfMonster;//怪物间隔
    private ArrayList jewels;//当前关卡放置的宝石
    private int aliveSize;//活着的怪物数量
    public Chapter(){
        jewels = new ArrayList<Jewel>();
        monsters = new ArrayList();
        stageNum = 1;
        judgeStage = 1;
        aliveSize=0;
        partWay = new ArrayList[6];
        way=new ArrayList();
        move_point=new int[][]{{4,4},{4,19},{34,19},{34,4},{19,4},{19,34},{34,34}};
        mbc = new MonsterBuilderControl();
        jewel_location = new int[39][39];
        for(int i=0;i<39;i++){
            for(int j=0;j<39;j++){
                jewel_location[i][j]=0;
            }
        }
    }
    public void reset(){
        monsters = new ArrayList();
        mbc = new MonsterBuilderControl();
    }
    public void addAliveSize(){
        aliveSize++;
    }
    public void deleteAliveSize(){
        aliveSize--;
    }
    public int getAliveSize(){
        return aliveSize;
    }
    //关卡开始
    public void finishChapter(){
        monsters.clear();
        numOfMonster=0;
        aliveSize=0;
    }
    public void start_chapter(){
        if (monsters==null)
            monsters=new ArrayList();
        spacingOfMonster = 20;
        numOfMonster=0;
        aliveSize=0;
        addMonster();
        getWay();
        way_length=way.size()*100;
        game_time=0;
    }
    //是否到达终点
    public boolean isReachFinal(Monster monster){
        return monster.getWay_length()>=way_length;
    }
    public int getMonsterSize(){
       return numOfMonster;
    }
    public ArrayList getMonsters() {
        return monsters;
    }


    public void addMonster(){
        if(numOfMonster<10)
            monsters.add(mbc.construct(1));
        numOfMonster++;
        addAliveSize();
    }
    public void addLocation(int x,int y){
        jewel_location[x][y] = 1;
    }
    public void removeLocation(int x,int y){
        jewel_location[x][y]=0;
    }
    public int isMix(Jewel selectJewel){//0是不能融合1是能融合2是能高级融合
        int i = 0;
        if(jewels.size()<5)
            return 0;
        else {

            for (int j =0;j<jewels.size();j++) {
                Jewel jewel = (Jewel)jewels.get(j);
                String name = selectJewel.getName();
                if(jewel==null);
                else if(name.equals(jewel.getName())){
                    i++;
                }
            }
        }
        if(i<2){
            return 1;
        }
        else if(i<4){
            return 2;
        }
        else
            return 3;
    }
    public ArrayList getJewel() {
        return jewels;
    }
    public void addJewel(Jewel jewel) {
        jewels.add(jewel);
    }
    public int getJudgeStage() {
        return judgeStage;
    }
    //转换阶段
    public void turnJudgeStage() {
        if(judgeStage==1){
            judgeStage=2;
            jewels.clear();
        }
        else if(judgeStage==2) {
            judgeStage = 1;
            stageNum++;
            finishChapter();
        }
    }
    //判断是否是通路
    public boolean isClose(int x,int y){
        boolean isClose = false;
        for(int i =0;i<partWay.length;i++){
            if(partWay[i]==null){
                isClose = findWay(i);
            }
            else {
                for (int j = 0; j < partWay[i].size(); j++) {
                    int[] location = (int[]) partWay[i].get(j);
                    if (location[0] == x && location[1] == y) {
                        isClose = findWay(i);
                        break;
                    }
                }
            }
            if(isClose) {
                break;
            }
        }
        return isClose;
    }
    public void showWay(Bitmap bitmap){
        if(bitmap==null)
            return;
        Canvas canvas = new Canvas(bitmap);
        for(int i =0;i<way.size();i++){
            int[] location = (int[])way.get(i);
            Paint paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(100 * location[0], 100 * location[1], 100 * (location[0] + 1), 100 * (location[1] + 1), paint);
        }
    }
    public void quickGetWay(){
        way = new ArrayList();
        for(int i =0;i<partWay.length;i++) {
            for(int j = 0;j<partWay[i].size();j++){
                way.add(partWay[i].get(j));
            }
        }
    }
    public void getWay(){
        way = new ArrayList();
        for(int i =0;i<partWay.length;i++) {
            findWay(i);
            for(int j = 0;j<partWay[i].size();j++){
                way.add(partWay[i].get(j));
            }
        }
    }
    public boolean findWay(int i){
        return findWay(i,new Point(move_point[i][0], move_point[i][1]), new Point(move_point[i + 1][0], move_point[i + 1][1]));
    }
    public void removeJewel(int index){
        jewels.remove(index);
    }
    public boolean findWay(int partWayIndex,Point start,Point finish){
        ArrayList<Point> open_list = new ArrayList<Point>();
        ArrayList<Point> close_list = new ArrayList<Point>();
        boolean isClose = true;
        start.setG(getG(start));
        start.setH(getH(start,finish));
        start.calcF();
        Point current_p;
        open_list.add(start);
        while(open_list.size()!=0){
            if(isContain(open_list,finish)){
                isClose = false;
                break;
            }
            current_p = open_list.get(0);
            open_list.remove(0);
            open_list.remove(current_p);
            close_list.add(current_p);
            for(int i = -1;i<=1;i++){
                for(int j = -1;j<=1;j++){

                    if(current_p.getX()+i<0||current_p.getY()+j<0||(i!=0&&j!=0)||current_p.getX()+i>=jewel_location.length||current_p.getY()+j>=jewel_location[0].length);
                    else{

                        Point neibor = new Point(current_p.getX()+i,current_p.getY()+j);
                        if(jewel_location[neibor.getX()][neibor.getY()]==1);
                        else if(!isContain(open_list,neibor)&&!isContain(close_list,neibor)){
                            neibor.setG(getG(neibor));
                            neibor.setH(getH(neibor,finish));
                            neibor.calcF();
                            neibor.setParent(current_p);
                            open_list.add(neibor);
                        }
                        else if(isContain(close_list,neibor)){
                            Point p = findPoint(close_list,neibor);
                            neibor.setG(getG(neibor));
                            neibor.setH(getH(neibor,finish));
                            neibor.calcF();
                            neibor.setParent(current_p);
                            if(p.getG()>neibor.getG()){
                                close_list.remove(p);
                                open_list.add(neibor);
                            }
                        }
                        else if(isContain(open_list,neibor)){
                            Point p = findPoint(open_list,neibor);
                            neibor.setG(getG(neibor));
                            neibor.setH(getH(neibor,finish));
                            neibor.calcF();
                            neibor.setParent(current_p);
                            if(p.getG()>neibor.getG()){
                                open_list.remove(p);
                                open_list.add(neibor);

                            }
                        }
                    }
                }
            }
        }
        if(isClose)
            return true;
        else {
            addWay(open_list, finish, start,partWayIndex);
            return false;
        }
    }
    //添加路线
    public void addWay(ArrayList<Point> list,Point finish,Point start,int index){
        Point p = findPoint(list,finish);
        partWay[index] = new ArrayList();
        while(!(p.getX()==start.getX()&&p.getY()==start.getY())){

            int[] location_p={p.getX(),p.getY()};

            partWay[index].add(0,location_p);
            p=p.getParent();
        }
        for(int i =0;i<partWay[index].size();i++){
            int[] location = (int[])partWay[index].get(i);
        }
    }
    public boolean isContain(ArrayList<Point> list,Point p){
        for(Point p1:list){
            if(p.getX()==p1.getX()&&p.getY()==p1.getY()){
                return true;
            }
        }
        return false;
    }
    public Point findPoint(ArrayList<Point> list,Point p){
        for(Point p1:list){
            if(p.getX()==p1.getX()&&p.getY()==p1.getY()){
                return p1;
            }
        }
        return null;
    }
    public int getG(Point current_p){
        if(current_p.getParent()==null)
            return 10;
        else
            return current_p.getParent().getG()+10;
    }
    public int getH(Point current_p,Point finish){
        int x = Math.abs(current_p.getX()-finish.getX());
        int y = Math.abs(current_p.getY()-finish.getY());
        return (x+y)*10;
    }
    public long getSpacingOfMonster() {
        return spacingOfMonster;
    }

    public void setSpacingOfMonster(long spacingOfMonster) {
        this.spacingOfMonster = spacingOfMonster;
    }
    public int[] getMonsterLocation(float length1){
        int[] location = {0, 0};
        int length = (int)length1;
        int index = length/100;//当前怪物所处的位置
        int over_length = length%100;//当前怪物超过默认位置的距离
        if(index+1<way.size()){
            int[] location1 = (int[])way.get(index);
            int[] location2 = (int[])way.get(index+1);
            location[0] = location1[0]*100;
            location[1] = location1[1]*100;
            if (location1[0]<location2[0]){
                location[0]+=over_length;
            }
            else if (location1[0]>location2[0]){
                location[0]-=over_length;
            }
            if (location1[1]<location2[1]){
                location[1]+=over_length;
            }
            else if (location1[1]>location2[1]){
                location[1]-=over_length;
            }
        }
        return location;
    }


    public int getStageNum() {
        return stageNum;
    }

    public int[][] getMove_point() {
        return move_point;
    }

    public void setStageNum(int stageNum) {
        this.stageNum = stageNum;
    }

}
