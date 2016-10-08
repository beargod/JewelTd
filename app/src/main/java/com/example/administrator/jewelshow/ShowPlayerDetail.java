package com.example.administrator.jewelshow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.player.Chapter;
import com.example.administrator.player.Player;

/**
 * Created by Administrator on 2016/9/8.
 */
public class ShowPlayerDetail extends PopupWindow{
    private TextView playerName;
    private TextView playerLevel;
    private TextView playerHp;
    private TextView playerMoney;
    private TextView chapterNumber;
    private Player player;
    private View playerDetailView;
    private Chapter chapter;
    public ShowPlayerDetail(Context context, Player player,int width, int height,Chapter chapter) {
        super(context);
        this.player=player;
        this.setWidth(width);
        this.setHeight(height);
        this.chapter=chapter;
        playerDetailView = LayoutInflater.from(context).inflate(R.layout.player_detail,null);
        playerName = (TextView)playerDetailView.findViewById(R.id.playerName);
        playerHp = (TextView)playerDetailView.findViewById(R.id.playerHp);
        playerLevel = (TextView)playerDetailView.findViewById(R.id.playerLevel);
        playerMoney = (TextView)playerDetailView.findViewById(R.id.playerMoney);
        chapterNumber = (TextView)playerDetailView.findViewById(R.id.chapterNumber);
        setChapterNumber();
        setPlayerHp();
        setPlayerLevel();
        setPlayerMoney();
        setPlayerName();
        this.setContentView(playerDetailView);
    }
    public void setPlayer(Player player){
        this.player=player;
    }
    public void setPlayerHp() {
        playerHp.setText("血量: "+player.getCastleHP()+" / "+player.getHpUpper());
    }

    public void setPlayerLevel() {
        playerLevel.setText("等级: "+player.getLevel()+"( "+player.getExperience()+"% )");
    }

    public void setPlayerMoney() {
        this.playerMoney.setText("金钱: "+player.getMoney());
    }

    public void setPlayerName() {
        playerName.setText("昵称: "+player.getName());
    }

    public void setChapterNumber() {
        if(chapter.getJudgeStage()==1)
            chapterNumber.setText("当前关卡: "+chapter.getStageNum()+"  准备阶段");
        else
            chapterNumber.setText("当前关卡: "+chapter.getStageNum()+"  防守阶段");
    }
}
