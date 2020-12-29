package BattleCity.Terrains;

import BattleCity.Game.GameUtil;

import java.awt.*;

public class Brick extends Terrain {
    private static final Image img= GameUtil.getImage("Resources/brick.png");
    private static final boolean moveable=false;
    private static final boolean destructible=true;
    private static final boolean penetrable=false;
    public Brick(int x,int y){
        super(img, moveable, destructible,penetrable, x, y);
    }
}
