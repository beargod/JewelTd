package com.example.administrator.jewelshow;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.ExitApplication;
import com.example.administrator.Memento;
import com.example.administrator.jewel.CompositeList;
import com.example.administrator.jewel.Jewel;
import com.example.administrator.jewel.Stone;
import com.example.administrator.jewel.basicJewel.BasicJewel;
import com.example.administrator.jewel.basicJewel.BasicJewelBuilderControl;
import com.example.administrator.monster.Monster;
import com.example.administrator.player.Chapter;
import com.example.administrator.player.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 地图绘制类
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        new Thread(new MyThread()).start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
    private boolean isGameThreadRun;
    private boolean isUiThreadRun;
    final byte[] lock;//用来处理ontouchevent的帧数
    public final int TIME = 20;//多久触发一次
    private ExecutorService threadPool;//处理宝石是否可以放置的线程池
    //备忘录
    private Memento memento;
    //玩家
    private Player player;
    //基础宝石建造者控制类
    private BasicJewelBuilderControl bjbc;
    private boolean isShowWay;
    //选中怪物的下标
    private int selectMonster;
    //显示的图片
    private Bitmap map;
    private Bitmap showMap;
    //图片资源
    private int[][] map_grid;//地图块
    private ImageResourse ir ;
    private CompositeList cl;
    private Paint paint;
    private Handler handler;
    //宝石类
    //屏幕的长和宽
    private int screenWidth;
    private int screenHeight;
    //背景开始的坐标
    private float startX;
    private float startY;
    //格子的边长
    private float grid_length;
    //格子的数量
    public final int GRID_NUM = 39;
    public final int FAULT_LENGTH=100;
    //是否选择怪物
    private boolean isSelectMonster;
    //按下点的数量
    private int mode;
    //开始时两点间的距离
    private float oldDist;
    //移动前的坐标
    private float first_x;
    private float first_y;
    //判断是否已经触发过放大缩小事件
    private boolean flag_touch;
    //判断是否已经触发过移动事件
    private boolean flag_touch2;
    //当前选定的位置
    private int speedUpNum = 1;
    private float selected_x;
    private float selected_y;
    private int  select_x;
    private int  select_y;
    private Jewel selected_jewel;//当前选中的宝石
    private Thread gameThread;
    private GameActivity game;
    private ArrayList<Bullet> bullets;

    private JewelDetailWindow jd_window;//宝石细节显示窗口
    private MonsterDetailWindow mdWindow;
    private Context context;//上下文
    private SurfaceHolder holder;
    //关卡类
    private Chapter chapter;
    public GameView(Context context,AttributeSet attrs) {
        super(context,attrs);
        this.context=context;
        bullets = new ArrayList<Bullet>();
        DisplayMetrics dm2 = getResources().getDisplayMetrics();
        screenWidth = dm2.heightPixels;
        screenHeight = dm2.widthPixels;
        isGameThreadRun = false;
        isUiThreadRun = true;
        isSelectMonster=false;
        lock = new byte[0];
        startX=0;
        startY=0;
        handler=new Handler();
        //格子的边长
        grid_length=100;
        isShowWay=false;
        selectMonster=-1;
        map_grid= new int[][]{
                {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0},
                {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
                {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0},
                {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
                {0, 1, 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0},
                {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
                {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0},
                {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
                {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0},
                {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
                {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0},
                {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
                {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0},
                {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
                {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0},
                {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
                {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0},
                {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
                {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0},
                {1, 0, 1, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1, 0, 1},
                {0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 2, 1, 0, 1, 0},
                {1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1},
                {0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 2, 1, 0, 1, 0},
                {1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1},
                {0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 2, 1, 0, 1, 0},
                {1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1},
                {0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 2, 1, 0, 1, 0},
                {1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1},
                {0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 2, 1, 0, 1, 0},
                {1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1},
                {0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 2, 1, 0, 1, 0},
                {1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1},
                {0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 2, 1, 0, 1, 0},
                {1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1},
                {0, 1, 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 2, 1, 0, 1, 0},
                {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
                {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0},
                {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
                {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0},
        };
        holder = this.getHolder();
        cl = new CompositeList();
        holder.addCallback(this);
        game = (GameActivity)context;
        startGame();
    }
    //结束游戏
    public void finishGame(){

        Dialog overDialog=new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.gameover_dialog,null);
        Button restartButton = (Button)view.findViewById(R.id.restart);
        Button quitButton=(Button)view.findViewById(R.id.quit);
        game.setSaveMemento(false);
        restartButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                game.restartGame();
            }
        });
        quitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ExitApplication.getInstance().exit();
            }
        });
        overDialog.setContentView(view);
        overDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        overDialog.show();
    }
    //开始游戏

    public CompositeList getCl() {
        return cl;
    }

    public ImageResourse getIr() {
        return ir;
    }

    public void startGame(){
        threadPool = Executors.newSingleThreadExecutor();
        ir = new ImageResourse(context);
        bjbc = new BasicJewelBuilderControl();
        player = new Player(this);
        chapter = new Chapter();
        memento = new Memento(player,chapter);
        chapter.getWay();
        initPaint();
        repaintMap();//初始化背景
    }
    private void showMonsterDetail(Monster monster){
        //自定义的单击事件
        mdWindow = new MonsterDetailWindow(context,monster,ir,this,screenHeight/5, screenWidth/5*4-10);
        //监听窗口的焦点事件，点击窗口外面则取消显示
        mdWindow.setTouchable(true);
        mdWindow.setTouchInterceptor(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        mdWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        //设置默认获取焦点
        mdWindow.setFocusable(true);
        //设置位置
        mdWindow.showAtLocation(this,Gravity.RIGHT|Gravity.TOP,0,screenWidth/5);
    }
    //展示宝石细节和具体操作界面
    public void showJewelDetail(Jewel jewel){
        jd_window = new JewelDetailWindow(context,jewel,ir,this,cl);
        if (jd_window == null) {
            //自定义的单击事件

            //监听窗口的焦点事件，点击窗口外面则取消显示
            jd_window.setTouchable(true);
            jd_window.setTouchInterceptor(new OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                    // 这里如果返回true的话，touch事件将被拦截
                    // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                }
            });

        }
        jd_window.setBackgroundDrawable(new ColorDrawable(0x00000000));
        //设置默认获取焦点
        jd_window.setFocusable(true);
        //设置位置
        jd_window.showAtLocation(this,Gravity.RIGHT|Gravity.TOP,0,screenWidth/5);
        //如果窗口存在，则更新
        jd_window.update(screenHeight/5, screenWidth/5*4-10);
    }
    public boolean isShowWay(){
        return isShowWay;
    }
    public void setShowWay(boolean is){
        isShowWay=is;
        chapter.quickGetWay();
        repaintMap();
    }
    //重画map
    public void repaintMap(){
        drawFirstMapGrid();
        drawSelectedRect();
        if(isShowWay)
            drawWay();
        for(int i=0;i<player.getJewels().size();i++) {
            drawJewel((Jewel)player.getJewels().get(i));
        }
        for(int i=0;i<player.getStones().size();i++) {
            drawStone((Stone)player.getStones().get(i));
        }
        showMap = Bitmap.createBitmap(map);
    }
    //放置宝石
    public void drawWay(){
        chapter.showWay(map);
    }
    public void putJewel(){
        final int[][] move_point = chapter.getMove_point();
        boolean canPut = true;
        for(int i=0;i<move_point.length;i++){
            if(select_x-1==move_point[i][0]&&select_y-1==move_point[i][1]){
                canPut=false;
            }
        }

        if(canPut) {
            if (chapter.getJudgeStage() == 1 && chapter.getJewel().size() < 5) {
                Jewel jewel = bjbc.constructByLevel(player.getLevel(), select_x, select_y,player.getJewels());
                switch (chapter.getJewel().size()){
                    case 1:jewel = bjbc.constructByQuality(5,1, select_x, select_y,player.getJewels());break;
                    case 2:jewel = bjbc.constructByQuality(6,1, select_x, select_y,player.getJewels());break;
                    case 3:jewel = bjbc.constructByQuality(8,1, select_x, select_y,player.getJewels());break;

                }
                chapter.addLocation(select_x-1,select_y-1);
                final int index1=player.getJewels().size();
                final int index2 = chapter.getJewel().size();
                player.addJewel(jewel);
                chapter.addJewel(jewel);
                threadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        if(chapter.isClose(select_x-1,select_y-1)) {
                            chapter.removeLocation(select_x - 1, select_y - 1);
                            if (index1 == player.getJewels().size() - 1)
                                player.removeJewel(index1);
                            else
                                player.removeJewel(player.getJewels().size() - 1);
                            if (index2 == chapter.getJewel().size() - 1)
                                chapter.removeJewel(index2);
                            else
                                chapter.removeJewel(chapter.getJewel().size() - 1);
                            repaintMap();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context,"放置的石头不能堵塞路线",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else{
                            memento.resetMemento(player,chapter);
                        }
                    }
                });
                drawJewel(jewel);
                showMap = Bitmap.createBitmap(map);
            }
        }
        else{
            Toast.makeText(context,"不能堵塞路线",Toast.LENGTH_SHORT).show();
        }
        Log.i("showJewels",player.getJewels().toString());
    }
    //移除宝石
    public void removeStone(){
        ArrayList stones = player.getStones();
        for(int i=0;i<stones.size();i++){
            Stone selected_stone = (Stone) stones.get(i);
            if(selected_stone.getX()==select_x&&selected_stone.getY()==select_y){
                player.removeStone(i);
                chapter.removeLocation(select_x,select_y);
                repaintMap();
                break;
            }
        }
    }
    //覆盖选定的位置
    private Monster getSelectMonster(){
        ArrayList monsters = chapter.getMonsters();
        for(int i=0;i<monsters.size();i++){
            Monster selected_monster = (Monster)monsters.get(i);
            float x=selected_monster.getX()-selected_x;
            float y = selected_monster.getY()-selected_y;
            if(x*x+y*y<5000){
                selectMonster=i;
                return selected_monster;
            }
        }
        return null;
    }
    //判断选中区域有没有宝石存在
    private Jewel isExistJewel(){
        ArrayList jewels = player.getJewels();
        for(int i=0;i<jewels.size();i++){
            Jewel selected_jewel = (Jewel)jewels.get(i);
            int x = select_x;
            int y = select_y;
            if(selected_jewel.getX()==x&&selected_jewel.getY()==y){
                return selected_jewel;
            }
        }
        return null;
    }
    //判断选中区域有没有石头存在
    private boolean isExistStone(){
        ArrayList stones = player.getStones();
        for(int i=0;i<stones.size();i++){
            Stone selected_stone = (Stone) stones.get(i);
            int x = select_x;
            int y = select_y;
            if(selected_stone.getX()==x&&selected_stone.getY()==y){
                return true;
            }
        }
        return false;
    }
    //绘制宝石
    public void drawJewel(Jewel jewel){
        Canvas canvas = new Canvas(map);
        Bitmap jewel_pic = getJewelPicture(jewel);
        //图片的长和宽
        float pic_width = jewel_pic.getWidth();
        float pic_height = jewel_pic.getHeight();
        float x = (jewel.getX()-1)*FAULT_LENGTH+(FAULT_LENGTH-pic_width)/2;
        float y = (jewel.getY()-1)*FAULT_LENGTH+(FAULT_LENGTH-pic_height)/2;
        canvas.drawBitmap(jewel_pic,x,y,paint);
    }
    //绘制石头
    public void drawStone(Stone stone){
        Canvas canvas = new Canvas(map);
        Bitmap stone_pic = getStonePicture();
        //图片的长和宽
        float pic_width = stone_pic.getWidth();
        float pic_height = stone_pic.getHeight();
        float x = (stone.getX()-1)*FAULT_LENGTH+(FAULT_LENGTH-pic_width)/2;
        float y = (stone.getY()-1)*FAULT_LENGTH+(FAULT_LENGTH-pic_height)/2;
        canvas.drawBitmap(stone_pic,x,y,paint);
    }
    //绘制怪物
    private Bitmap drawMonsters(){
        Bitmap bitmap;
        bitmap=Bitmap.createBitmap(showMap);
        Canvas canvas = new Canvas(bitmap);
        ArrayList monsters = chapter.getMonsters();

        for(int i =0;i<monsters.size();i++) {
            Monster monster = (Monster) monsters.get(i);
            Bitmap monster_pic = getMonsterPicture(monster);
            if (monster.isAlive()) {
                int[] monster_location = chapter.getMonsterLocation(monster.getWay_length());
                float x = monster_location[0];
                float y = monster_location[1];
                monster.setX((int) x);
                monster.setY((int) y);
                synchronized (paint) {
                    canvas.drawBitmap(monster_pic, x, y, paint);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setColor(Color.RED);
                    canvas.drawRect(x, y - 30, x + FAULT_LENGTH, y - 10, paint);
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawRect(x, y - 30, x + FAULT_LENGTH * monster.getCurrent_hp() / monster.getHp(), y - 10, paint);
                    if (i == selectMonster) {
                        paint.setStyle(Paint.Style.STROKE);
                        canvas.drawCircle(x + 50, y + 50, FAULT_LENGTH, paint);
                    }
                }
            }
        }
        for(int i =0;i<bullets.size();i++){
            Bullet bullet=bullets.get(i);
            paint.setColor(Color.BLACK);
            bullet.drawAttackLine(canvas,paint);
        }
        return bitmap;
    }
    //绘制选定区域
    public void drawSelectedRect(){
        //选定位置处于地图的坐标
        Canvas canvas = new Canvas(map);
        if(startX<selected_x&&startY<selected_y) {
            //选定位置方格的坐标
            int grid_x = select_x;
            int grid_y = select_y;
            //选定位置方格的起始坐标
            float rect_x = (grid_x - 1) * FAULT_LENGTH;
            float rect_y = (grid_y - 1) * FAULT_LENGTH;
            //绘制选定图形
            synchronized (paint) {
                paint.setColor(Color.WHITE);
                paint.setStrokeWidth(6);
                float line_length = FAULT_LENGTH / 4;
                canvas.drawLine(rect_x, rect_y, rect_x + line_length, rect_y, paint);
                canvas.drawLine(rect_x, rect_y, rect_x, rect_y + line_length, paint);
                canvas.drawLine(rect_x + line_length * 3, rect_y, rect_x + line_length * 4, rect_y, paint);
                canvas.drawLine(rect_x, rect_y + line_length * 3, rect_x, rect_y + line_length * 4, paint);
                canvas.drawLine(rect_x + line_length * 4, rect_y, rect_x + line_length * 4, rect_y + line_length, paint);
                canvas.drawLine(rect_x, rect_y + line_length * 4, rect_x + line_length, rect_y + line_length * 4, paint);
                canvas.drawLine(rect_x + line_length * 3, rect_y + line_length * 4, rect_x + line_length * 4, rect_y + line_length * 4, paint);
                canvas.drawLine(rect_x + line_length * 4, rect_y + line_length * 3, rect_x + line_length * 4, rect_y + line_length * 4, paint);
                Jewel selected_jewel = isExistJewel();//选中的宝石
                if (selected_jewel != null) {
                    float x = (grid_x - 1) * FAULT_LENGTH + FAULT_LENGTH / 2;
                    float y = (grid_y - 1) * FAULT_LENGTH + FAULT_LENGTH / 2;
                    paint.setStyle(Paint.Style.STROKE);
                    canvas.drawCircle(x, y, selected_jewel.getRange() / (FAULT_LENGTH / 100), paint);
                }
            }
        }

    }
    //获取宝石图片
    private Bitmap getJewelPicture(Jewel jewel){
        int id = jewel.getId();
        Bitmap picture = ir.getImageById(id);
        int newWidth = picture.getWidth();
        int newHeight = picture.getHeight();
        float scaleWidth;
        float scaleHeight;
        if(id>0&&id<9){
            BasicJewel bj = (BasicJewel)jewel;
            int quality = bj.getQuality();
            int towerSize = 100;
            switch (quality){
                case 1:towerSize = 40;break;
                case 2:towerSize = 50;break;
                case 3:towerSize = 60;break;
                case 4:towerSize = 70;break;
                case 5:towerSize = 80;break;
                case 6:towerSize = 90;break;
            }
            scaleWidth =  (towerSize)*(FAULT_LENGTH)/((float) newWidth)/100;
            scaleHeight = (towerSize)*(FAULT_LENGTH)/((float) newHeight)/100;
        }
        else {
            scaleWidth =  (FAULT_LENGTH)/((float) newWidth);
            scaleHeight = (FAULT_LENGTH)/((float) newHeight);
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        picture = Bitmap.createBitmap(picture,0,0,newHeight,newWidth,matrix,true);
        return picture;
    }
    private Bitmap getMonsterPicture(Monster monster){
        Bitmap picture = ir.getMonsterById(monster.getId());
        int newWidth = picture.getWidth();
        int newHeight = picture.getHeight();
        float scaleWidth =  (FAULT_LENGTH)/((float) newWidth);
        float scaleHeight = (FAULT_LENGTH)/((float) newHeight);
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        picture = Bitmap.createBitmap(picture,0,0,newHeight,newWidth,matrix,true);
        return picture;
    }
    private Bitmap getStonePicture(){
        Bitmap picture = ir.getImageById(-1);
        int newWidth = picture.getWidth();
        int newHeight = picture.getHeight();
        float scaleWidth =  (FAULT_LENGTH)/((float) newWidth);
        float scaleHeight = (FAULT_LENGTH)/((float) newHeight);
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        picture = Bitmap.createBitmap(picture,0,0,newHeight,newWidth,matrix,true);
        return picture;
    }
    //初始化地图
    private void drawFirstMapGrid(){
        if(map == null)
            map = Bitmap.createBitmap(GRID_NUM*FAULT_LENGTH,GRID_NUM*FAULT_LENGTH, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(map);
        for(int a = 0;a<GRID_NUM;a++){
            for(int b=0;b<GRID_NUM;b++) {
                if (a < 39 && b < 39 && a >= 0 && b >= 0) {
                    switch (map_grid[a][b]) {
                        case 0:
                            paint.setColor(Color.LTGRAY);
                            break;
                        case 1:
                            paint.setColor(Color.DKGRAY);
                            break;
                        case 2:
                            paint.setColor(Color.GREEN);
                            break;
                    }
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawRect(FAULT_LENGTH * a, FAULT_LENGTH * b, FAULT_LENGTH * (a + 1), FAULT_LENGTH * (b + 1), paint);
                }
            }
        }
    }
    private Bitmap getMap(){
        Bitmap backgroud = ir.getImageById(0);
        int newWidth = backgroud.getWidth();
        int newHeight = backgroud.getHeight();

        float grid_num_x = screenWidth/FAULT_LENGTH;
        float grid_num_y = screenHeight/FAULT_LENGTH;
        float new_length = newWidth/GRID_NUM;
        float scaleWidth =  (FAULT_LENGTH*GRID_NUM)/((float) newWidth) ;
        float scaleHeight =  (FAULT_LENGTH*GRID_NUM)/((float) newHeight);
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        if((startX+screenHeight)/scaleHeight>newWidth) {
            startX=(newWidth*scaleHeight)-screenHeight;
        }
        if((startY+screenWidth)/scaleHeight>newHeight){
            startY=(newWidth*scaleHeight)-screenWidth;
        }
        backgroud = Bitmap.createBitmap(backgroud,(int)(startX/scaleHeight), (int)(startY/scaleHeight), (int)(grid_num_y*new_length),
                (int)(grid_num_x*new_length),matrix,true);//截取显示在屏幕上的图片
        return backgroud;
    }
    //初始化paint
    private void initPaint(){
        paint = new Paint();
        //不使用抗锯齿
        paint.setAntiAlias(false);
        //过滤对bitmap的优化操作提高显示速度
        paint.setFilterBitmap(true);
        //使图片显示更加清晰
        paint.setDither(true);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //触摸事件的行为
        int action = event.getAction();
        switch (action&MotionEvent.ACTION_MASK){
            //按下
            case MotionEvent.ACTION_DOWN:{
                flag_touch = false;
                flag_touch2 = false;
                first_x = event.getX();
                first_y = event.getY();
                mode = 1;
                break;
            }
            //抬起
            case MotionEvent.ACTION_UP:{
                mode = 0;
                if(!flag_touch&&!flag_touch2){
                    selected_x = (event.getX()+startX)*FAULT_LENGTH/grid_length;
                    selected_y = (event.getY()+startY)*FAULT_LENGTH/grid_length;
                    select_x =(int)(selected_x/FAULT_LENGTH+1);
                    select_y =(int)(selected_y/FAULT_LENGTH+1);
                    selected_jewel = isExistJewel();//选中的宝石
                    boolean existStone = isExistStone();
                    isSelectMonster=false;
                    if (!existStone&&selected_jewel==null&&chapter.getJewel().size()<5&&chapter.getJudgeStage()==1) {
                        game.showPut_btn();
                    }
                    else if (existStone&&chapter.getJewel().size()<5&&chapter.getJudgeStage()==1){
                        game.showRemove_btn();
                    }
                    else if(selected_jewel!=null){
                        showJewelDetail(selected_jewel);
                    }else{
                        Monster selectMonster =getSelectMonster();
                        if(selectMonster!=null&&selectMonster.isAlive()) {
                            showMonsterDetail(selectMonster);
                            isSelectMonster=true;
                        }
                    }
                    if(!isSelectMonster)
                        repaintMap();
                }
                break;
            }
            //移动
            case MotionEvent.ACTION_MOVE:{
                //当前的坐标

                float current_x = event.getX();
                float current_y = event.getY();
                //处理移动事件
                if (mode == 1&&!flag_touch){
                    float x = current_x-first_x;
                    float y = current_y-first_y;
                    float spacing = (float)Math.sqrt(x * x + y * y);
                    if (spacing>1) {
                        moveMap(first_x, first_y, current_x, current_y);
                        first_x = current_x;
                        first_y = current_y;
                        flag_touch2=true;
                    }
                }
                //处理放大缩小事件
                if (mode >= 2) {
                    float newDist = getSpacing(event);
                    if (newDist > oldDist + 1) {
                        zoom(newDist / oldDist);
                        oldDist = newDist;
                    }
                    if (newDist < oldDist - 1) {
                        zoom(newDist / oldDist);
                        oldDist = newDist;
                    }
                    flag_touch=true;
                }
                break;
            }
            //按下第一个点后按下其他点
            case MotionEvent.ACTION_POINTER_DOWN:{
                mode+=1;
                oldDist = getSpacing(event);
                break;
            }
            //按下多个点后抬起一个点
            case MotionEvent.ACTION_POINTER_UP:{
                mode-=1;
                break;
            }
        }
        synchronized (lock){
            try {
                lock.wait(TIME);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        return true;
    }
    //获得两点间的距离
    private float getSpacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float)Math.sqrt(x * x + y * y);
    }
    //改变地图大小
    private void zoom(float f) {
        float new_width = grid_length*f;
        if(new_width<200&&new_width>80){
            grid_length = new_width;
            moveMap(0,0,0,0);
        }
    }
    //移动地图
    private void moveMap(float firstX,float firstY,float lastX,float lastY){

        float move_y=firstY-lastY;
        float move = startY+move_y;
        if(move>=0&&move<(GRID_NUM)*grid_length-screenWidth) {
            startY = move;
        }
        else if(move>=(GRID_NUM)*grid_length-screenWidth){
            startY = (GRID_NUM)*grid_length-screenWidth;
        }
        float move_x=firstX-lastX;
        move = startX+move_x;
        if(move>=0&&move<(GRID_NUM)*grid_length-screenHeight) {
            startX = move;
        }
        else if(move>=(GRID_NUM)*grid_length-screenHeight){
            startX = (GRID_NUM)*grid_length-screenHeight;
        }
    }
    public Chapter getChapter() {
        return chapter;
    }
    //判断是否为当前选中的宝石

    public Player getPlayer() {
        return player;
    }
    public void startChapter(){
        memento.resetMemento(player,chapter);
        Toast.makeText(context,"第"+chapter.getStageNum()+"关",Toast.LENGTH_SHORT).show();
        chapter.start_chapter();
        repaintMonsterNumber();
        isGameThreadRun = true;
        gameThread=new Thread(new GameThread());
        gameThread.start();
    }
    //判定宝石攻击对象的算法返回0则为没有攻击对象
    public boolean getAttackGoal(Jewel jewel){
        jewel.clearAttackPriority();
        for (int i = 0; i < chapter.getMonsterSize(); i++) {
            Monster monster = (Monster) chapter.getMonsters().get(i);
            if (isInRange(jewel, monster)&&monster.isAlive()) {
                if(monster.isFirstAttack()){
                    jewel.addPriority(0,i);
                }
                else
                    jewel.addPriority(i);
            }
        }
        return jewel.getAttack_priority().size()!=0;

    }
    //判断怪物是否在宝石攻击范围内
    public boolean isInRange(Jewel jewel,Monster monster){
        if(jewel==null||monster==null) {
            return false;
        }
        else {
            float x = jewel.getY()*FAULT_LENGTH-monster.getY();
            float y = jewel.getX()*FAULT_LENGTH-monster.getX();
            if(x*x+y*y<jewel.getRange()*jewel.getRange()){
                return true;
            }
            else
                return false;
        }
    }
    //重新绘制关卡属性
    public void repaintChapter(){
        game.setChapter();
        handler.post(new Runnable() {
            @Override
            public void run(){
                game.setChapterNumber();
                game.setMonsterNumber();
            }
        });
    }
    public void repaintMonsterNumber(){
        game.setChapter();
        handler.post(new Runnable() {
            @Override
            public void run(){
                game.setMonsterNumber();
            }
        });
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public void setMemento(Memento memento) {
        this.memento = memento;
    }

    //数据处理线程
    private class GameThread implements Runnable{
        @Override
        public void run() {
            int time_count = 0;
            Date d;
            while (isGameThreadRun) {
                try {
                    Thread.sleep(TIME);
                    //判断是否添加怪物
                    if (chapter.getMonsterSize() < 10) {
                        if (time_count >=
                                chapter.getSpacingOfMonster()*50/TIME/speedUpNum) {
                            chapter.addMonster();
                            repaintMonsterNumber();
                            time_count = 0;
                        } else {
                            time_count++;
                        }
                    } else {
                        //判断场上是否剩余怪物
                        if (chapter.getAliveSize()
                                == 0&&chapter.getMonsterSize()==10) {
                            selectMonster=-1;
                            isSelectMonster=false;
                            chapter.turnJudgeStage();
                            bullets.clear();
                            repaintChapter();
                            isGameThreadRun = false;
                            return;
                        }
                    }
                    ArrayList monsters = chapter.getMonsters();
                    //处理怪物的debuff和移动事件
                    for (int i = 0; i < monsters.size(); i++) {
                        Monster monster = (Monster) monsters.get(i);
                        boolean flag = monster.isAlive();
                        monster.handlerDebuffs(System.currentTimeMillis());
                        if(flag!=monster.isAlive())
                            chapter.deleteAliveSize();
                        monster.move(speedUpNum,TIME);
                        //判断怪物是否到达终点
                        if(chapter.isReachFinal(monster)&&monster.isAlive()) {
                            player.setCastleHP(player.getCastleHP() - 10);
                            monsterDie();
                            game.setPlayer(player);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    game.setPlayerHp();
                                }
                            });
                            if (player.getCastleHP() <= 0) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        finishGame();
                                    }
                                });
                                return;
                            }
                        }
                    }
                    d = new Date();
                    long time = d.getTime();
                    //处理宝石的攻击事件
                    for (int i = 0; i < player.getJewels().size(); i++) {
                        Jewel jewel = (Jewel) player.getJewels().get(i);
                        if (jewel.isAttack()&&getAttackGoal(jewel)) {
                            if ((time - jewel.getLast_attcktime())*speedUpNum / 10 > jewel.getSpeed()) {
                                jewel.setLast_attcktime(time);
                                bullets.addAll(jewel.makeBullet(monsters));
                            }
                        }
                    }
                    //确定子弹是否触发
                    for (int i = 0; i < bullets.size(); i++) {
                        Bullet bullet = bullets.get(i);
                        if ((time - bullet.getStart_time())*speedUpNum > bullet.getLast_time()) {
                            bullets.remove(i);
                            Monster monster = bullet.getGoalMonster();
                            Jewel jewel = bullet.getStartJewel();
                            if(monster.isAlive()) {
                                //处理怪物的死亡事件
                                if (bullet.handlerAttack(monsters)) {
                                    monsterDie();
                                    player.addExperience(2);
                                    game.setChapter();
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            game.setPlayerLevel();
                                        }
                                    });
                                    if (jewel.addExperience(10)) {

                                    }
                                    if (jd_window.isShowing()&&jd_window.isSame(jewel)) {
                                        jd_window.setJewel(jewel);
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                jd_window.updateDetail();
                                            }
                                        });
                                    }
                                    jewel.getAttack_priority().remove(0);
                                }
                            }
                            //处理怪物详细界面的更新
                            if((monsters.indexOf(monster))==selectMonster){
                                if(monster.isAlive()){
                                    mdWindow.repaintDetail(monster);
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run(){
                                            mdWindow.updateDetail();
                                        }
                                    });
                                }
                                else{
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run(){
                                            mdWindow.dismiss();
                                        }
                                    });
                                }
                            }
                        }
                    }
                } catch (Exception e) {

                }
            }
        }
    }
    public void speedUp(){
        speedUpNum=3;
    }
    public void speedDown(){
        speedUpNum=1;
    }

    public Memento getMemento() {
        return memento;
    }

    //判定路线是否堵塞的线程
    private void monsterDie(){
        selectMonster=-1;
        isSelectMonster=false;
        repaintMonsterNumber();
        chapter.deleteAliveSize();
    }
    public void closeUiThread(){
        isUiThreadRun=false;
    }
    //ui线程
    private class MyThread implements Runnable{
        @Override
        public void run(){
            Canvas canvas;
            Bitmap bitmap;
            while (isUiThreadRun){
                if (chapter.getJudgeStage()==2){
                    bitmap=drawMonsters();
                }else{
                    bitmap=showMap;
                }
                float scale=grid_length/FAULT_LENGTH;

                try {
                    Thread.sleep(TIME);
                    canvas = holder.lockCanvas();
                    Matrix matrix = new Matrix();
                    matrix.postScale(scale,scale);
                    bitmap = Bitmap.createBitmap(bitmap,(int)(startX/scale),(int)(startY/scale),(int)(screenHeight/scale),(int)(screenWidth/scale),matrix,true);
                    //绘制选定的位置
                    canvas.drawBitmap(bitmap,0,0,paint);
                    holder.unlockCanvasAndPost(canvas);
                }catch (Exception e){

                }
            }
        }
    }
}
