package BattleCity.Game;

import BattleCity.Terrains.*;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//地图类，文件生成地图
//地图信息以txt文件保存在src/Maps中，每一行代表一个地形对象
//第一个数字代表种类 1=砖块 2=铁块 3=草地 4=水
//第二个数字代表x坐标 第三个数字代表y坐标
public class Map {
    public ArrayList<Terrain> terrains=new ArrayList<>();

    public Map(String filePath){
        File file=new File(filePath);
        Scanner scanner = null;

        {
            try {
                scanner = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        while(scanner.hasNext()){
            String s=scanner.nextLine();
            String[]arr=s.split(" ");
            int x=Integer.parseInt(arr[1]);
            int y=Integer.parseInt(arr[2]);
            switch (Integer.parseInt(arr[0])){
                case 1:
                    terrains.add(new Brick(x,y));
                    break;
                case 2:
                    terrains.add(new Steel(x,y));
                    break;
                case 3:
                    terrains.add(new Grass(x,y));
                    break;
                case 4:
                    terrains.add(new Water(x,y));
                    break;
            }
        }
    }

    public void draw(Graphics g){
        for(int i=0;i<terrains.size();i++){
            terrains.get(i).draw(g);
        }
    }
}
