import greenfoot.*;  

public class Cupcake extends PowerItems
{    
    private BadgeCenter bc;
    
    public Cupcake( int tX, int tY, int eT) {
        super(tX, tY, eT);
        bc = BadgeCenter.getInstance();
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
            bc.hitCupcake();
            a.sayWoot();
            a.stun();
            getWorld().removeObject(this);
        }
    }

}
