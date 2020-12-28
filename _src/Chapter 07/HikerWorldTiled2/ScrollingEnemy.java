import greenfoot.*;

abstract public class ScrollingEnemy extends ScrollingActor {
    protected static final int SPEED = 1;
    private static final int BOUNDARY = 40;
    protected int speedX = SPEED;
    protected int speedY = SPEED;

    /* initialization */
    protected void addedToWorld(World w) {
        MazeWorld mw = (MazeWorld) w;
        GreenfootImage img = getImage();
        img.scale(mw.getTileWidth(),mw.getTileHeight());
        setImage(img);
    }

    /* ability methods */
    public void act() {
        sense();
        reaction();
        boundedMove();
    }    

    protected void sense() {
        // No smarts
    }

    protected void reaction() {
        // No reaction
    }

    protected void boundedMove() {
        setLocation(getX()+speedX, getY()+speedY);
        if( isTouching(ScrollingObstacle.class) ) {
            setLocation(getX()-speedX, getY()-speedY);
        }
    }
}
