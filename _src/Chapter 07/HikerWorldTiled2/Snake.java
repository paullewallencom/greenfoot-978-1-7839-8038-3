import greenfoot.*;
import java.util.List;

public class Snake extends ScrollingEnemy {
    private static final int PATHLENGTH = 200;
    private static final int INRANGE = 100;
    private int pathCounter = PATHLENGTH;
    private boolean pathing = false;
    private int rememberSpeedX = 0;
    private List<Hiker> lse;
    
    /* constructors */
    public Snake() {
        speedX = rememberSpeedX = SPEED;
        speedY = 0;
    }
    
    /* ability methods */
    protected void sense() {
        // If near, move towards enemy
        lse = getObjectsInRange(INRANGE,Hiker.class);
        pathing = lse.isEmpty();
    }
    
    protected void reaction() {
        if( pathing ) {
            speedX = rememberSpeedX;
            speedY = 0;
            if( --pathCounter == 0 ) {
                pathCounter = PATHLENGTH;
                speedX = rememberSpeedX = -speedX;
            }
        } else {
            speedX = lse.get(0).getX() > getX() ? 1 : -1;
            speedY = lse.get(0).getY() > getY() ? 1 : -1;
        }
    }
}
