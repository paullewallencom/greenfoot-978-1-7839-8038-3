import greenfoot.*; 

public class Hiker extends Actor
{
    private int speedX = 1;
    private static final int SPEED = 2;
    private static final int BOUNDARY = 40;
    
    public void act() {
        handleKeyPresses();
        boundedMove();
        checkAtLake();
    }
    
    private void handleKeyPresses() {
        handleArrowKey("left", -SPEED);
        handleArrowKey("right", SPEED);
    }
    
    private void handleArrowKey(String k, int sX) {
        if( Greenfoot.isKeyDown(k) ) {
            speedX = sX;
        }
    }
    
    private void boundedMove() {
        if( speedX+getX() <= BOUNDARY ) {
            setLocation(BOUNDARY, getY());
            ((HikingWorld)getWorld()).shiftWorld(-speedX);
        } else if( speedX+getX() >= getWorld().getWidth()-BOUNDARY ) {
            setLocation(getWorld().getWidth()-BOUNDARY, getY());
            ((HikingWorld)getWorld()).shiftWorld(-speedX);
        } else {
            setLocation(getX()+speedX, getY());
        }
        speedX = 0;
    }
    
    private void checkAtLake() {
        // Do something cool if make it to the lake...
    }
}
