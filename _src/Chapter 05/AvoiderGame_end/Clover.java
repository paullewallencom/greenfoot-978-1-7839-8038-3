import greenfoot.*;  
import java.lang.Math;

public class Clover extends PowerItems
{
    private BadgeCenter bc;
    
    public Clover(int tX, int tY, int eT) {
        super(tX, tY, eT);
        bc = BadgeCenter.getInstance();
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
            bc.hitClover();
            a.sayWoot();
            a.lagControls();
            getWorld().removeObject(this);
        }
    }

}
