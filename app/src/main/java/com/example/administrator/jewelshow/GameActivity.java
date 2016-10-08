package com.example.administrator.jewelshow;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.administrator.ExitApplication;
import com.example.administrator.Memento;
import com.example.administrator.player.Chapter;
import com.example.administrator.player.Player;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

public class GameActivity extends Activity {
    private GameView gameView ;
    private boolean isSaveMemento;
    //放置按钮
    private Context context;
    private Button put_btn;
    //移除按钮
    private Button remove_btn;
    private boolean isSpeedUp;//是否开启加速
    private TextView playerName;
    private TextView playerLevel;
    private TextView playerHp;
    private TextView playerMoney;
    private TextView chapterNumber;
    private TextView monsterNumber;
    private Button setter;//设置按键
    private ToggleButton showWay;//展示路线按键
    private Button showCompiteWay;//展示合成表按键
    private ToggleButton speedUp;//加速按键
    private CompsiteWayWindow cww;
    private Player player;
    private Chapter chapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = GameActivity.this;
        setContentView(R.layout.activity_game);
        ExitApplication.getInstance().add(this);
        gameView = (GameView)findViewById(R.id.gameView);
        put_btn = (Button) findViewById(R.id.put_button);
        remove_btn = (Button) findViewById(R.id.remove_button);
        playerName = (TextView)findViewById(R.id.playerName);
        playerHp = (TextView)findViewById(R.id.playerHp);
        playerLevel = (TextView)findViewById(R.id.playerLevel);
        playerMoney = (TextView)findViewById(R.id.playerMoney);
        chapterNumber = (TextView)findViewById(R.id.chapterNumber);
        monsterNumber = (TextView)findViewById(R.id.monsterNumber);
        setter = (Button)findViewById(R.id.setter);
        showCompiteWay=(Button)findViewById(R.id.showCompiste);
        showWay = (ToggleButton)findViewById(R.id.showWay);
        speedUp = (ToggleButton)findViewById(R.id.speedUp);
        isSpeedUp = false;
        isSaveMemento = true;
        Memento memento = readMemento();
        if(readMemento()!=null){
            Log.i("memento","success read");
            gameView.setPlayer(memento.getPlayer());
            Chapter chapter = memento.getChapter();
            chapter.reset();
            gameView.setChapter(chapter);
            gameView.setMemento(memento);
            gameView.repaintChapter();
            if(chapter.getJudgeStage()==2){
                gameView.startChapter();
            }
            gameView.repaintMap();
            int saveTime = memento.getSaveTime();
            if(saveTime<=0){
                isSaveMemento=false;
            }
            Toast.makeText(context,"剩余 "+saveTime+" 次读档机会",Toast.LENGTH_SHORT).show();
        }
        setShowCompiteWay();
        setShowWay();
        setSpeedUp();
        setSetter();
        setPlayer();
        setChapter();
        setChapterNumber();
        setMonsterNumber();
        setPlayerHp();
        setPlayerLevel();
        setPlayerMoney();
        setPlayerName();
    }
    public void setSetter(){
        setter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishGame();
            }
        });
    }

    public void setShowWay() {
        showWay.setChecked(gameView.isShowWay());
        showWay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                gameView.setShowWay(isChecked);
            }
        });
    }

    public void setShowCompiteWay() {
        showCompiteWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cww ==null){
                    cww = new CompsiteWayWindow(GameActivity.this,gameView);
                    //监听窗口的焦点事件，点击窗口外面则取消显示
                    cww.setTouchable(true);
                    cww.setTouchInterceptor(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return false;
                            // 这里如果返回true的话，touch事件将被拦截
                            // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                        }
                    });
                    cww.setBackgroundDrawable(new ColorDrawable(0x00000000));
                    //设置默认获取焦点
                    cww.setFocusable(true);
                }
                cww.showAtLocation(gameView, Gravity.CENTER,0,0);
            }
        });
    }

    public void setSaveMemento(boolean saveMemento) {
        isSaveMemento = saveMemento;
    }

    public void setSpeedUp() {
        speedUp.setChecked(isSpeedUp);
        speedUp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isSpeedUp = isChecked;
                if(isChecked){
                    gameView.speedUp();
                }
                else{
                    gameView.speedDown();
                }
            }
        });
    }

    public void finishGame(){
        final Dialog overDialog=new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.gameover_dialog,null);
        Button restartButton = (Button)view.findViewById(R.id.restart);
        Button quitButton=(Button)view.findViewById(R.id.quit);
        TextView text=(TextView)view.findViewById(R.id.finish_text);
        text.setText("确定退出游戏");
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overDialog.dismiss();
                restartGame();
            }
        });
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overDialog.dismiss();
                if(isSaveMemento) {
                    saveMemento();
                }
                else{
                    clearMemento();
                }
                ExitApplication.getInstance().exit();
            }
        });
        overDialog.setContentView(view);
        overDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        overDialog.show();
    }
    public GameActivity() {
        super();
    }

    @Override
    protected void onDestroy() {
        setContentView(R.layout.view_null);
        gameView.closeUiThread();
        Log.i("testActivity","onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        if(isSaveMemento) {
            saveMemento();
        }
        else{
            clearMemento();
        }

        Log.i("testActivity","onPause");
        super.onPause();
    }

    @Override
    protected void onRestart() {
        Log.i("testActivity","onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i("testActivity","onResume");
        super.onResume();
    }
    public void finish_activity(){
        ExitApplication.getInstance().remove(this);
        finish();
    }
    @Override
    protected void onStart() {
        Log.i("testActivity","onStart");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.i("testActivity","onStop");
        super.onStop();
    }
    public void setPlayer(){
        this.player=gameView.getPlayer();
    }
    public void setChapter(){
        this.chapter=gameView.getChapter();
    }
    public void setPlayer(Player player){
        this.player=player;
    }
    public void setPlayerHp() {
        String text = "血量: "+player.getCastleHP()+" / "+player.getHpUpper();
        playerHp.setText(text);
    }
    public void setMonsterNumber() {
        String text = "怪物数量: "+chapter.getAliveSize()+" / "+chapter.getMonsterSize();
        monsterNumber.setText(text);
    }
    public void setPlayerLevel() {
        String text = "等级: "+player.getLevel()+"( "+player.getExperience()+"% )";
        playerLevel.setText(text);
    }

    public void setPlayerMoney() {
        String  text = "金钱: "+player.getMoney();
        this.playerMoney.setText(text);
    }

    public void setPlayerName() {
        String  text = "昵称: "+player.getName();
        playerName.setText(text);
    }

    public void setChapterNumber() {
        StringBuffer text = new StringBuffer();
        text.append("当前关卡: "+chapter.getStageNum());
        if(chapter.getJudgeStage()==1)
            text.append("  准备阶段");
        else
            text.append("  防守阶段");
        chapterNumber.setText(text);
    }
    public void onClick(View view){
        switch(view.getId()){
            case R.id.put_button:
                gameView.putJewel();
                put_btn.setVisibility(View.GONE);
                break;
            case R.id.remove_button:
                gameView.removeStone();
                remove_btn.setVisibility(View.GONE);
                break;
        }
    }
    public void restartGame(){
        isSaveMemento = false;
        finish_activity();
    }
    public void showPut_btn(){
        put_btn.setVisibility(View.VISIBLE);
    }
    public void showRemove_btn(){
        remove_btn.setVisibility(View.VISIBLE);
    }
    public void clearMemento(){
        SharedPreferences sp = context.getSharedPreferences("gameData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
    public void saveMemento(){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            SharedPreferences sp = context.getSharedPreferences("gameData", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            Memento memento = gameView.getMemento();
            memento.deleteSaveTime();
            oos.writeObject(memento);
            oos.flush();
            String byteString = bytesToHexString(baos.toByteArray());
            editor.putString("memento",byteString);
            editor.commit();
            oos.close();
            baos.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * desc:将数组转为16进制
     * @param bArray
     * @return
     * modified:
     */
    public static String bytesToHexString(byte[] bArray) {
        if(bArray == null){
            return null;
        }
        if(bArray.length == 0){
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
    /**
     * desc:获取保存的Object对象
     * @param context
     * @param key
     * @return
     * modified:
     */
    public Object readObject(Context context,String key ){
        try {
            SharedPreferences sharedata = context.getSharedPreferences("gameData", 0);
            if (sharedata.contains(key)) {
                String string = sharedata.getString(key, "");
                if(TextUtils.isEmpty(string)){
                    return null;
                }else{
                    //将16进制的数据转为数组，准备反序列化
                    byte[] stringToBytes = StringToBytes(string);
                    ByteArrayInputStream bis=new ByteArrayInputStream(stringToBytes);
                    ObjectInputStream is=new ObjectInputStream(bis);
                    //返回反序列化得到的对象
                    Object readObject = is.readObject();
                    bis.close();
                    is.close();
                    return readObject;
                }
            }
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //所有异常返回null
        return null;

    }
    /**
     * desc:将16进制的数据转为数组
     * @param data
     * @return
     * modified:
     */
    public static byte[] StringToBytes(String data){
        String hexString=data.toUpperCase().trim();
        if (hexString.length()%2!=0) {
            return null;
        }
        byte[] retData=new byte[hexString.length()/2];
        for(int i=0;i<hexString.length();i++)
        {
            int int_ch;  // 两位16进制数转化后的10进制数
            char hex_char1 = hexString.charAt(i); ////两位16进制数中的第一位(高位*16)
            int int_ch1;
            if(hex_char1 >= '0' && hex_char1 <='9')
                int_ch1 = (hex_char1-48)*16;   //// 0 的Ascll - 48
            else if(hex_char1 >= 'A' && hex_char1 <='F')
                int_ch1 = (hex_char1-55)*16; ////
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); ///两位16进制数中的第二位(低位)
            int int_ch2;
            if(hex_char2 >= '0' && hex_char2 <='9')
                int_ch2 = (hex_char2-48); //// 0 的Ascll - 48
            else if(hex_char2 >= 'A' && hex_char2 <='F')
                int_ch2 = hex_char2-55; ////
            else
                return null;
            int_ch = int_ch1+int_ch2;
            retData[i/2]=(byte) int_ch;//将转化后的数放入Byte里
        }
        return retData;
    }
    private Memento readMemento(){
        Memento memento= (Memento) readObject(context,"memento");
        return memento;
    }
    //service
}
