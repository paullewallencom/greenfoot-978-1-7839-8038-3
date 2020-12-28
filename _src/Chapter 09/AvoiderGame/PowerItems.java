import greenfoot.*;  

public abstract class PowerItems extends SmoothMover
{
    protected double targetX, targetY, expireTime;
    protected double origX, origY;
    protected double duration;
    protected int counter;
    
    public PowerItems( int tX, int tY, int eT ) {
        targetX = tX;
        targetY = tY;
        expireTime = eT;
        counter = 0;
        duration = expireTime;
    }
    
    protected void addedToWorld(World w) {
        origX = getX();
        origY = getY();
    }
    
    public void act() {
        easing();
        checkHitAvatar();
        checkExpire();
    } 
    
    protected abstract double curveX(double f);
    
    protected abstract double curveY(double f);
    
    protected abstract void checkHitAvatar();
    
    protected void easing() {
        double fX = ++counter/duration;
        double fY = counter/duration;
        fX = curveX(fX);
        fY = curveY(fY);
        setLocation((targetX * fX) + (origX * (1-fX)),
                    (targetY * fY) + (origY * (1-fY)));
    }
    
    private void checkExpire() {
        if( expireTime-- < 0 ) {
            getWorld().removeObject(this);
        }
    }
}
