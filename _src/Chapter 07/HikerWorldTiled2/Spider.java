import greenfoot.*;

public class Spider extends ScrollingEnemy {
    private final static int SPEEDVARIATION = 3;
    private final static int SPEEDCHANGECHANCE = 20;
    
    /* ability methods */
    protected void reaction() {
        speedX = Greenfoot.getRandomNumber(1000) < SPEEDCHANGECHANCE ? 
                Greenfoot.getRandomNumber(SPEEDVARIATION)-1 : speedX;
        speedY = Greenfoot.getRandomNumber(1000) < SPEEDCHANGECHANCE ? 
                Greenfoot.getRandomNumber(SPEEDVARIATION)-1 : speedY;
    }
}
