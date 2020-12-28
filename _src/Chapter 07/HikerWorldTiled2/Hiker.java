import greenfoot.*; 

public class Hiker extends Actor
{
    private static final int SPEED = 2;
    private static final int BOUNDARY = 40;
    private int speedX = SPEED;
    private int speedY = SPEED;
    
    public void act() {
        handleKeyPresses();
        handleCollisions();
        boundedMove();
    }
    
    /* private methods */
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
    
    private void handleCollisions() {
        if( isTouching(ScrollingEnemy.class) ) {
            Greenfoot.stop();  // Game Over
        }
    }
    
    private void boundedMove() {
        setLocation(getX()+speedX, getY()+speedY);
        if( isTouching(ScrollingObstacle.class) ) {
            setLocation(getX()-speedX, getY()-speedY);
        } else if( isTouching(GoldBlock.class) ) {
            Greenfoot.stop();  // Game over...you Win!!
        }else if( getX() > getWorld().getWidth() - BOUNDARY ) {
            ((MazeWorld)getWorld()).shiftWorld(-speedX);
            setLocation(getX()-speedX, getY()-speedY);
        } else if( getX() < BOUNDARY ) {
            ((MazeWorld)getWorld()).shiftWorld(-speedX);
            setLocation(getX()-speedX, getY()-speedY);            
        }
        speedX = 0;
        speedY = 0;
    }
}
