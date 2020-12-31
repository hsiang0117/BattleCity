package BattleCity.Units;

import BattleCity.Game.GameFrame;
import BattleCity.Game.GameUtil;
import BattleCity.Terrains.Terrain;

import java.awt.*;
import java.util.*;

//敌人类
public class Enemy {
    private Image left_img,right_img,up_img,down_img,img;
    private static int speed=1;
    private int direction;
    public ArrayList<Bullet> bullets=new ArrayList<>();
    public int x,y,width,height;
    TimerTask changeDirection=new TimerTask() {
        @Override
        public void run() {
            ChangeDirection();
        }
    };
    TimerTask fire=new TimerTask() {
        @Override
        public void run() {
            Fire();
        }
    };

    public Enemy(String left_imgPath,String up_imgPath,String right_imgPath,String down_imgPath,int x,int y,int direction){
        this.left_img= GameUtil.getImage(left_imgPath);
        this.right_img=GameUtil.getImage(right_imgPath);
        this.up_img=GameUtil.getImage(up_imgPath);
        this.down_img=GameUtil.getImage(down_imgPath);
        this.x=x;
        this.y=y;
        this.width=40;
        this.height=40;
        this.direction=direction;
        Timer timer=new Timer();
        timer.schedule(changeDirection,1500,2500);
        timer.schedule(fire,1000,2000);
    }

    public void draw(Graphics g){
        g.drawImage(img,x,y,width,height,null);
        drawbullets(g);
        move();
    }

    public void drawbullets(Graphics g){
        for(int i=0;i<bullets.size();i++){
            Bullet bullet=bullets.get(i);
            bullet.draw(g);
            if(CheckBullets(bullet)){
                bullets.remove(i);
            }
        }
    }

    public void move(){
        int movedx=x,movedy=y;
        boolean flag=true;
        if(direction==1&&x>20){
            img=left_img;
            movedx=x - speed;
            movedy=y;
        }
        if(direction==3&&y>30){
            img=up_img;
            movedx=x;
            movedy=y - speed;
        }
        if(direction==2&&x<780-width){
            img=right_img;
            movedx=x + speed;
            movedy=y;
        }
        if(direction==4&&y<790-height){
            img=down_img;
            movedx=x;
            movedy=y + speed;
        }
        for(int i = 0; i< GameFrame.map.terrains.size(); i++){
            Terrain terrain=GameFrame.map.terrains.get(i);
            if(Collide(movedx,movedy,width,height, terrain.x, terrain.y, terrain.width, terrain.height )&&!terrain.moveable){
                flag=false;
            }
        }
        Tank tank=GameFrame.tank;
        if(Collide(movedx,movedy,width,height, tank.x, tank.y,tank.width,tank.height)){
            flag=false;
        }
        Base base=GameFrame.base;
        if(Collide(movedx,movedy,width,height, base.x, base.y, base.width, base.height)){
            flag=false;
        }
        if(flag){
            x=movedx;
            y=movedy;
        }
    }

    public void ChangeDirection(){
        Random r=new Random();
        direction=r.nextInt(4)+1;
    }

    public void Fire(){
        int x=0,y=0;
        switch (direction){
            case 1:
                x=this.x-2;
                y=this.y+this.height/2-2;
                break;
            case 2:
                x=this.x+this.width-2;
                y=this.y+this.height/2-2;
                break;
            case 3:
                x=this.x+this.width/2-2;
                y=this.y-2;
                break;
            case 4:
                x=this.x+this.width/2-2;
                y=this.y+this.height-2;
                break;
        }
        Bullet bullet=new Bullet(x,y,direction);
        bullets.add(bullet);
    }

    public boolean CheckBullets(Bullet bullet){
        if(bullet.x<=20||bullet.y<=30||bullet.x+bullet.width>=780||bullet.y+bullet.height>=790){
            return true;
        }
        return false;
    }

    public boolean Collide(int x1,int y1,int width1,int height1,int x2,int y2,int width2,int height2){
        Rectangle tempRect1=new Rectangle(x1,y1,width1,height1);
        Rectangle tempRect2=new Rectangle(x2,y2,width2,height2);
        if(tempRect1.intersects(tempRect2)){
            return true;
        }
        return false;
    }
}
