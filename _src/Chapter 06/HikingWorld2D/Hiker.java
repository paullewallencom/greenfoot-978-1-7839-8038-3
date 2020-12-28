import greenfoot.*; 

public class Hiker extends Actor
{
    private int speedX = 1;
    private int speedY = 1;
    private static final int SPEED = 2;
    private static final int BOUNDARY = 40;
    
    public void act() {
        handleKeyPresses();
        boundedMove();
        checkAtLake();
    }
    
    private void handleKeyPresses() {
        handleArrowKey("left", -SPEED, 0);
        handleArrowKey("right", SPEED, 0);
        handleArrowKey("up", 0, -SPEED);
        handleArrowKey("down", 0, SPEED);
    }
    
    private void handleArrowKey(String k, int sX, int sY) {
        if( Greenfoot.isKeyDown(k) ) {
            speedX = sX;
            speedY = sY;
        }
    }
    
    private void boundedMove() {
        
        if( speedX+getX() <= BOUNDARY ) {
            setLocation(BOUNDARY, getY());
            ((HikingWorld)getWorld()).shiftWorld(-speedX, 0);
        } else if( speedX+getX() >= getWorld().getWidth()-BOUNDARY ) {
            setLocation(getWorld().getWidth()-BOUNDARY, getY());
            ((HikingWorld)getWorld()).shiftWorld(-speedX, 0);
        } else {
            setLocation(getX()+speedX, getY());
        }
        
        if( speedY+getY() <= BOUNDARY ) {
            setLocation(getX(), BOUNDARY);
            ((HikingWorld)getWorld()).shiftWorld(0, -speedY);
        } else if( speedY+getY() >= getWorld().getHeight()-BOUNDARY ) {
            setLocation(getX(), getWorld().getHeight()-BOUNDARY);
            ((HikingWorld)getWorld()).shiftWorld(0, -speedY);
        } else {
            setLocation(getX(), getY()+speedY);
        }
        speedX = 0;
        speedY = 0;
    }
    
    private void checkAtLake() {
    }
}
