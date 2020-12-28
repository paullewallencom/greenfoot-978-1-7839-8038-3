import greenfoot.*; 

public class Enemy extends Actor
{
    private int speed;

    public void act() {
        setLocation(getX(), getY() + speed);
        // Remove this enemy if it goes off the screen
        checkRemove();
    }    
    
    public void setSpeed( int s) {
        speed = s;
    }
    
    private void checkRemove() {
        World w = getWorld();
        if( getY() > w.getHeight() + 30 ) {
            w.removeObject(this);
        }
    }
}
