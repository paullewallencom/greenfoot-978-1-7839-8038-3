import greenfoot.*;  

public class Rocket extends Actor{
    private int speedX = 1;
    private int speedY = 0;
    private static final int SPEED = 2;
    private static final int BOUNDARY = 20;
    
    public void act() {
        handleKeyPresses();
        boundedMove();
        checkForCrash();
    }
    
    private void handleKeyPresses() {
        handleArrowKey("down", 0, SPEED);
        handleArrowKey("up", 0, -SPEED);
        handleArrowKey("left", -SPEED, 0);
        handleArrowKey("right", SPEED, 0);
    }
    
    private void handleArrowKey(String k, int sX, int sY) {
        if( Greenfoot.isKeyDown(k) ) {
            speedX = sX;
            speedY = sY;
        }
    }
    
    private void boundedMove() {
        int newX = Math.max(BOUNDARY, speedX+getX());
        newX = Math.min(getWorld().getWidth()-BOUNDARY, newX);
        int newY = Math.max(BOUNDARY, speedY+getY());
        newY = Math.min(getWorld().getHeight()-BOUNDARY, newY);
        setLocation(newX,newY); 
    }
    
    private void checkForCrash() {
        Actor w = getOneIntersectingObject(Obstacle.class);
        if( w != null ) {
            Greenfoot.stop();
        }
    }
}
