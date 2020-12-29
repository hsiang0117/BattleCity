package BattleCity.Game;

import BattleCity.Terrains.Terrain;
import BattleCity.Units.Base;
import BattleCity.Units.Bullet;
import BattleCity.Units.Enemy;
import BattleCity.Units.Tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

public class GameFrame extends Frame {

    static int level=1;
    static int enemyNum=15;
    static int enemyLeft=enemyNum;
    static int enemyDestroied=0;
    static String mapPath="src/Maps/map"+level+".txt";
    public static final int WIDTH = 800;
    public static final int HEIGHT = 850;
    public PaintThread paintThread;
    public static ArrayList<Enemy> enemies=new ArrayList<>();
    public static Tank tank=new Tank("Resources/left_tank.png","Resources/up_tank.png",
            "Resources/right_tank.png","Resources/down_tank.png",300,750);
    public static Base base=new Base(380,750);
    public static Map map=new Map(mapPath);

    int gameState=0;
    int score=0;
    int life=2;


    public static void main(String[] args) {
        new GameFrame().launch();
    }

    public void launch(){
        this.setTitle("BattleCity");
        this.setSize(WIDTH, HEIGHT);
        this.setLocation(0,0);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setResizable(false);
        this.setVisible(true);
        this.setBackground(Color.BLACK);
        this.addKeyListener(new KeyMonitor());
        paintThread=new PaintThread();
        paintThread.start();
    }

    @Override
    public void paint(Graphics graphics) {
        if(gameState==0){
            StartPage(graphics);
            RefreshGame();
        }
        if(gameState==1) {
            PrintInfo(graphics,"得分"+score,20,650,820);
            PrintInfo(graphics,"命数"+life,20,20,820);
            PrintInfo(graphics,"第"+level+"关",20,370,820);
            tank.draw(graphics);
            base.draw(graphics);
            for (int i = 0; i < tank.bullets.size(); i++) {
                Bullet bullet = tank.bullets.get(i);
                for (int j = 0; j < map.terrains.size(); j++) {
                    Terrain terrain = map.terrains.get(j);
                    if(bullet.Hit(base.x,base.y,base.width,base.height)){
                        gameState=3;
                    }
                    if (bullet.Hit(terrain.x, terrain.y, terrain.width, terrain.height)) {
                        if (terrain.destructible) {
                            map.terrains.remove(j);
                            if (!tank.bullets.isEmpty()) {
                                tank.bullets.remove(i);
                            }
                        } else if (!terrain.penetrable) {
                            if (!tank.bullets.isEmpty()) {
                                tank.bullets.remove(i);
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < tank.bullets.size(); i++) {
                Bullet bullet = tank.bullets.get(i);
                for (int j = 0; j < enemies.size(); j++) {
                    Enemy enemy = enemies.get(j);
                    if (bullet.Hit(enemy.x, enemy.y, enemy.width, enemy.height)) {
                        if (!tank.bullets.isEmpty()) {
                            tank.bullets.remove(i);
                        }
                        enemies.remove(j);
                        score+=50;
                        enemyDestroied++;
                        if(enemyDestroied==enemyNum){
                            level++;
                            if(level<=3) {
                                mapPath = "src/Maps/map" + level + ".txt";
                                map = new Map(mapPath);
                                tank = new Tank("Resources/left_tank.png", "Resources/up_tank.png",
                                        "Resources/right_tank.png", "Resources/down_tank.png", 300, 750);
                                gameState = 2;
                            }
                            else{
                                gameState=4;
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = enemies.get(i);
                for (int j = 0; j < enemy.bullets.size(); j++) {
                    Bullet bullet = enemies.get(i).bullets.get(j);
                    if(bullet.Hit(base.x,base.y,base.width,base.height)){
                        gameState=3;
                    }
                    if (bullet.Hit(tank.x, tank.y, tank.width, tank.height)) {
                        if(life>0){
                            tank = new Tank("Resources/left_tank.png", "Resources/up_tank.png",
                                    "Resources/right_tank.png", "Resources/down_tank.png", 300, 750);
                            life--;
                            enemies.get(i).bullets.remove(j);
                        }
                        else{
                            gameState=3;
                        }
                    }
                    for (int k = 0; k < map.terrains.size(); k++) {
                        Terrain terrain = map.terrains.get(k);
                        if (bullet.Hit(terrain.x, terrain.y, terrain.width, terrain.height)) {
                            if (terrain.destructible) {
                                map.terrains.remove(k);
                                if (!enemies.get(i).bullets.isEmpty()) {
                                    enemies.get(i).bullets.remove(j);
                                }
                            } else if (!terrain.penetrable) {
                                if (!enemies.get(i).bullets.isEmpty()) {
                                    enemies.get(i).bullets.remove(j);
                                }
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = enemies.get(i);
                enemy.draw(graphics);
            }
            map.draw(graphics);
            GenerateEnemy();
        }
        if(gameState==2){
            ClearPage(graphics);
            enemyDestroied=0;
            enemyLeft=enemyNum;
        }
        if(gameState==3){
            GameOver(graphics);
            RefreshGame();
        }
        if(gameState==4){
            GamePassed(graphics);
            RefreshGame();
        }
    }

    private void RefreshGame(){
        level=1;
        life=2;
        score=0;
        mapPath="src/Maps/map"+level+".txt";
        map=new Map(mapPath);
        tank=new Tank("Resources/left_tank.png","Resources/up_tank.png",
                "Resources/right_tank.png","Resources/down_tank.png",300,750);
        enemies.clear();
        enemyDestroied=0;
        enemyLeft=enemyNum;
    }

    Image ImageBuffer = null;
    Graphics GraImage = null;
    @Override
    public void update(Graphics g){
        ImageBuffer = createImage(this.getWidth(), this.getHeight());
        GraImage = ImageBuffer.getGraphics();
        paint(GraImage);
        GraImage.dispose();
        g.drawImage(ImageBuffer, 0, 0, this);
    }

    int location=1;
    private void GenerateEnemy(){
        int x1=60,x2=280,x3=500,x4=720,y=80;
        if(enemies.size()<4&&enemyLeft>0){
            switch (location) {
                case 1:
                    Enemy enemy1 = new Enemy("Resources/left_enemy.png", "Resources/up_enemy.png",
                            "Resources/right_enemy.png", "Resources/down_enemy.png", x1, y, 4);
                    enemies.add(enemy1);
                    break;
                case 2:
                    Enemy enemy2 = new Enemy("Resources/left_enemy.png", "Resources/up_enemy.png",
                            "Resources/right_enemy.png", "Resources/down_enemy.png", x2, y, 4);
                    enemies.add(enemy2);
                    break;
                case 3:
                    Enemy enemy3 = new Enemy("Resources/left_enemy.png", "Resources/up_enemy.png",
                            "Resources/right_enemy.png", "Resources/down_enemy.png", x3, y, 4);
                    enemies.add(enemy3);
                    break;
                case 4:
                    Enemy enemy4 = new Enemy("Resources/left_enemy.png", "Resources/up_enemy.png",
                        "Resources/right_enemy.png", "Resources/down_enemy.png", x4, y, 4);
                    enemies.add(enemy4);
                    break;
            }
            if(location==4){
                location=1;
            }else location++;
            enemyLeft--;
        }
    }

    private void StartPage(Graphics g){
        PrintInfo(g,"坦克大战",80,220,300);
        PrintInfo(g,"by向建鑫",25,500, 340);
        PrintInfo(g,"操作说明：",30,240 ,400);
        PrintInfo(g,"WASD控制移动 空格开火",30,200,450);
        PrintInfo(g,"按回车键开始游戏",50,180,600);
    }

    private void ClearPage(Graphics g){
        PrintInfo(g,"恭喜过关",80,220,300);
        PrintInfo(g,"按回车键进入下一关",50,180,600);
    }

    private void GameOver(Graphics g){
        PrintInfo(g,"游戏结束",80,220,300);
        PrintInfo(g,"按回车键重新游戏",50,180,600);
    }

    private void GamePassed(Graphics g){
        PrintInfo(g,"你完成了全部3关",60,150,300);
        PrintInfo(g,"按ESC键返回首页",50,180,600);
    }



    class PaintThread extends Thread {
        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
                repaint();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    class KeyMonitor extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            int key=e.getKeyCode();
            if(key==KeyEvent.VK_ENTER){
                gameState=1;
            }else if(key==KeyEvent.VK_ESCAPE){
                gameState=0;
            }
            tank.KeyPressedControlDirection(e);
        }
        @Override
        public void keyReleased(KeyEvent e){
            tank.KeyReleasedControlDirection(e);
        }
    }

    public void PrintInfo(Graphics g,String message,int size,int x,int y){
        g.setColor(Color.white);
        Font f = new Font("宋体",Font.BOLD,size);
        g.setFont(f);
        g.drawString(message, x,y);
    }
}