package BattleCity.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

//用于加载图片的工具类
public class GameUtil {
    public static Image getImage(String path){
        URL url=GameUtil.class.getClassLoader().getResource(path);
        BufferedImage img=null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
}
