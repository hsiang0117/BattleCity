package BattleCity.Units;

import BattleCity.Game.GameFrame;
import BattleCity.Game.GameUtil;
import BattleCity.Terrains.Terrain;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Tank {
    public int direction=3;
    boolean left,right,up,down;
    private Image left_img,up_img,right_img,down_img,img;
    public int x,y,width,height;
    public ArrayList<Bullet> bullets=new ArrayList<>();
    private static int speed=2;

    public Tank(String left_imgPath,String up_imgPath,String right_imgPath,String down_imgPath,int x,int y){
        this.left_img= GameUtil.getImage(left_imgPath);
        this.up_img=GameUtil.getImage(up_imgPath);
        this.right_img=GameUtil.getImage(right_imgPath);
        this.down_img=GameUtil.getImage(down_imgPath);
        this.img=up_img;
        this.x=x;
        this.y=y;
        this.width=40;
        this.height=40;
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
        if(left&&x>20){
            movedx=x - speed;
            movedy=y;
        }
        if(up&&y>30){
            movedx=x;
            movedy=y - speed;
        }
        if(right&&x<780-width){
            movedx=x + speed;
            movedy=y;
        }
        if(down&&y<790-height){
            movedx=x;
            movedy=y + speed;
        }
        for(int i = 0; i< GameFrame.map.terrains.size(); i++){
            Terrain terrain=GameFrame.map.terrains.get(i);
            if(Collide(movedx,movedy,width,height, terrain.x, terrain.y, terrain.width, terrain.height )&&!terrain.moveable){
                flag=false;
            }
        }
        for(int i=0;i<GameFrame.enemies.size();i++){
            Enemy enemy=GameFrame.enemies.get(i);{
                if(Collide(movedx,movedy,width,height,enemy.x,enemy.y,enemy.width,enemy.height)){
                    flag=false;
                }
            }
        }
        Base base=GameFrame.base;
        if(Collide(movedx,movedy,width,height,base.x,base.y,base.width,base.height)){
            flag=false;
        }
        if(flag){
            x=movedx;
            y=movedy;
        }
    }

    public void KeyPressedControlDirection(KeyEvent e){
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_A:
                left=true;
                direction=1;
                img=left_img;
                break;
            case KeyEvent.VK_W:
                up=true;
                direction=3;
                img=up_img;
                break;
            case KeyEvent.VK_D:
                right=true;
                direction=2;
                img=right_img;
                break;
            case KeyEvent.VK_S:
                down=true;
                direction=4;
                img=down_img;
                break;
            case KeyEvent.VK_SPACE:
                Fire();
                break;
        }
    }

    public void KeyReleasedControlDirection(KeyEvent e){
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_A:
                left=false;
                break;
            case KeyEvent.VK_W:
                up=false;
                break;
            case KeyEvent.VK_D:
                right=false;
                break;
            case KeyEvent.VK_S:
                down=false;
                break;
        }
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
        if(bullets.size()<2){
            bullets.add(bullet);
        }
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
