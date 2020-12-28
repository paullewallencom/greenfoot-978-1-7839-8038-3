import greenfoot.*;  

public class Cupcake extends PowerItems
{    
    public Cupcake( int tX, int tY, int eT) {
        super(tX, tY, eT);
    }
            
    protected double curveX(double f) {
        return f;
    }

    protected double curveY(double f) {
        return f; 
    }

    protected void checkHitAvatar() {
        Avatar a = (Avatar) getOneIntersectingObject(Avatar.class);
        if( a != null ) {
            a.stun();
            getWorld().removeObject(this);
        }
    }

}
