package BattleCity.Terrains;

import BattleCity.Game.GameUtil;

import java.awt.*;

public class Steel extends Terrain {
    private static final Image img= GameUtil.getImage("Resources/steel.png");
    private static final boolean moveable=false;
    private static final boolean destructible=false;
    private static final boolean penetrable=false;
    public Steel(int x,int y){
        super(img, moveable, destructible,penetrable, x, y);
    }
}
