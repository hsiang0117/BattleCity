package BattleCity.Units;

import BattleCity.Game.GameUtil;

import java.awt.*;

//碉堡类
public class Base {
    private Image img= GameUtil.getImage("Resources/base.png");
    public int x,y,width,height;
    public Base(int x,int y){
        this.x=x;
        this.y=y;
        this.width=40;
        this.height=40;
    }

    public void draw(Graphics g){
        g.drawImage(img,x,y,width,height,null);
    }
}
