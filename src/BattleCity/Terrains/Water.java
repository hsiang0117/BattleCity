package BattleCity.Terrains;

import BattleCity.Game.GameUtil;

import java.awt.*;

public class Water extends Terrain {
    private static final Image img= GameUtil.getImage("Resources/water.png");
    private static final boolean moveable=false;
    private static final boolean destructible=false;
    private static final boolean penetrable=true;
    public Water(int x,int y){
        super(img, moveable, destructible,penetrable, x, y);
    }
}
