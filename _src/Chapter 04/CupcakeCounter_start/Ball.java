import greenfoot.*; 

public class Ball extends Enemy {
    protected int actorHeight;
    private int speedX = 0;

    public Ball() {
        actorHeight = getImage().getHeight();
        speedX = Greenfoot.getRandomNumber(8) - 4;
        if( speedX == 0 ) {
            speedX = Greenfoot.getRandomNumber(100) < 50 ? -1 : 1;
        }
    }

    public void act() {
        checkOffScreen();
    }    

    public int getXVelocity() {
        return speedX;
    }

    private void checkOffScreen() {
        if( getX() < -20 || getX() > getWorld().getWidth() + 20 ) {
            getWorld().removeObject(this);
        } else if( getY() > getWorld().getHeight() + 20 ) {
            getWorld().removeObject(this);
        }
    }
}
