package BattleCity.Units;

import BattleCity.Game.GameFrame;

import java.awt.*;

//子弹类
public class Bullet {
    private static int speed=3;
    public int x,y,width,height;
    public int direction;

    public Bullet(int x,int y,int direction){
        this.x=x;
        this.y=y;
        width=4;
        height=4;
        this.direction=direction;
    }

    public void draw(Graphics g){
        g.setColor(Color.white);
        g.fillRect(x,y,width,height);
        move();
    }

    public void move(){
        if(direction==1&&x>=7){
            x -= speed;
        }
        if(direction==3&&y>=30){
            y -= speed;
        }
        if(direction==2&&x<=800-width-7){
            x += speed;
        }
        if(direction==4&&y<= GameFrame.HEIGHT-height-7){
            y += speed;
        }
    }

    public boolean Hit(int x,int y,int width,int height){
        Rectangle bulletRect=new Rectangle(this.x,this.y,this.width,this.height);
        Rectangle tempRect=new Rectangle(x,y,width,height);
        if(bulletRect.intersects(tempRect)){
            return true;
        }
        return false;
    }
}
