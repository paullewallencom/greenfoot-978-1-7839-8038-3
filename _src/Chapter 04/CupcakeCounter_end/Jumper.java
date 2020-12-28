import greenfoot.*;

public abstract class Jumper extends Actor
{
    protected int actorHeight;
    private int fallSpeed = 0;
    private boolean jumping = false;
    
    // Class Constants
    protected static final int GRAVITY = 1; 
    protected static final int JUMPSTRENGTH = 12;
    
    public Jumper() {
        actorHeight = getImage().getHeight();
    }
    
    public void act() {
        handleKeyPresses();
        standOrFall();
    }
    
    protected void handleKeyPresses() {
        if( (Greenfoot.isKeyDown("space") || Greenfoot.isKeyDown("up")) && !jumping) {
            jump();
        }
    }
    
    private void jump() {
        fallSpeed = -JUMPSTRENGTH;
        jumping = true;
        fall();
    }
    
    private void standOrFall() {
        if( inAir() ) {
            checkHead();
            fall();
            checkLanding();
        } else {
            fallSpeed = 0;
            jumping = false;
        }
    }
    
    private void checkHead() {
        int actorHead = -actorHeight/2;
        int step = 0;
        while( fallSpeed < 0 && step > fallSpeed 
            && getOneObjectAtOffset(0, actorHead + step, Platform.class) == null ) {
            step--;
        }
        if( fallSpeed < 0 ) {
            fallSpeed = step;
        }
    }
    
    private void checkLanding() {
        int actorFeet = actorHeight/2;
        int step = 0;
        while( fallSpeed > 0 && step < fallSpeed 
            && getOneObjectAtOffset(0, actorFeet + step, Platform.class) == null ) {
            step++;
        }
        if( fallSpeed > 0 ) {
            fallSpeed = step;
        }
    }
    
    private boolean inAir() {
        Actor platform = getOneObjectAtOffset(0, getImage().getHeight()/2, Platform.class);
        
        return platform == null;
    }
    
    private void fall() {
        setLocation(getX(), getY() + fallSpeed);
        fallSpeed = fallSpeed + GRAVITY;
    }
}
