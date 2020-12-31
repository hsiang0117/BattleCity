package BattleCity.Terrains;

import java.awt.*;

//地形类，派生出四个子类
public class Terrain {
    private Image img;
    public boolean moveable;
    public boolean destructible;
    public boolean penetrable;
    public int x,y,width=40,height=40;

    public Terrain(Image img,boolean moveable,boolean destructible,boolean penetrable,int x,int y){
        this.img=img;
        this.moveable=moveable;
        this.destructible=destructible;
        this.penetrable=penetrable;
        this.x=x;
        this.y=y;
    }

    public void draw(Graphics g){
        g.drawImage(img,x,y,width,height,null);
    }
}
