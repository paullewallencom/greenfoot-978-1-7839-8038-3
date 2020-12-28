import greenfoot.*;  
import java.awt.Color;
import java.util.List;

public class Boom extends Actor {
    private static final int BOOMLIFE = 50;
    private static final int BOOMRADIUS = 50;
    int boomCounter = BOOMLIFE;
    
    public Boom() {
        GreenfootImage me = new GreenfootImage(BOOMRADIUS*2,BOOMRADIUS*2);
        me.setColor(Color.RED);
        me.setTransparency(125);
        me.fillOval(0 , 0, BOOMRADIUS * 2, BOOMRADIUS*2);
        setImage(me);
    }
    
    public void act() {
        if( boomCounter == BOOMLIFE) destroyEverything(BOOMRADIUS);
        if( boomCounter-- == 0 ) {
            World w = getWorld();
            w.removeObject(this);
        }
    }  
    
    private void destroyEverything(int x) {
    } 
}
