import greenfoot.*;  
import java.util.*;

public class Zombie extends Actor {
    int counter, stationaryX, amplitude;
    
   protected void addedToWorld(World w) {
        stationaryX = getX();
        amplitude = Greenfoot.getRandomNumber(6) + 2;
    }
    
    public void act() {
        shake();
        if( canMarch() ) {
            stationaryX = stationaryX + 2;
        }
    }  
    
    public void shake() {
        counter++;
        setLocation((int)(stationaryX + amplitude*Math.sin(counter/2)), getY());
    }
    
    private boolean canMarch() {
        return false;
    }
}
