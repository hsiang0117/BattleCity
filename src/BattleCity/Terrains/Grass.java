package BattleCity.Terrains;

import BattleCity.Game.GameUtil;

import java.awt.*;

public class Grass extends Terrain {
    private static final Image img= GameUtil.getImage("Resources/grass.png");
    private static final boolean moveable=true;
    private static final boolean destructible=false;
    private static final boolean penetrable=true;
    public Grass(int x,int y){
        super(img, moveable, destructible,penetrable, x, y);
    }
}
