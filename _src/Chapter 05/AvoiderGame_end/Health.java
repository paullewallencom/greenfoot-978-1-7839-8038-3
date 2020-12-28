import greenfoot.*;  

public class Health extends PowerItems
{
    private BadgeCenter bc;
    
    public Health( int tX, int tY, int eT ) {
        super(tX, tY, eT);
        bc = BadgeCenter.getInstance();
    }
    
    protected double curveX(double f) {
        return f;
    }
    
    protected double curveY(double f) {
        // return Math.sin(f);  // Best ease-in evah!
        return f * f * f;
    }
    
    protected void checkHitAvatar() {
        Avatar a = (Avatar) getOneIntersectingObject(Avatar.class);
        if( a != null ) {
            bc.hitRock();
            a.sayAhhh();
            a.addHealth();
            getWorld().removeObject(this);
        }
    }
}
