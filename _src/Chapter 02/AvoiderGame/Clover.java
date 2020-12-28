import greenfoot.*;  
import java.lang.Math;

public class Clover extends PowerItems
{
    public Clover(int tX, int tY, int eT) {
        super(tX, tY, eT);
    }
    
    protected double curveX(double f) { 
        return f; 
    }
    
    protected double curveY(double f) { 
        return Math.sin(4*f); 
    }
    
    protected void checkHitAvatar() {
        Avatar a = (Avatar) getOneIntersectingObject(Avatar.class);
        if( a != null ) {
            a.lagControls();
            getWorld().removeObject(this);
        }
    }

}
