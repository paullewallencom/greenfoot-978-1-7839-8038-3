import greenfoot.*;
import java.awt.Color;

public class Badge extends Actor {
    GreenfootImage bkg;
    GreenfootImage msg;
    
    public Badge(String s) {
        bkg = getImage();
        msg = new GreenfootImage(s, 14, Color.white, null);
        bkg.drawImage(msg, 10, 20);
        setImage(bkg);
    }

}
