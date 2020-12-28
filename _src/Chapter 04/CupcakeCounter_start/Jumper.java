import greenfoot.*;

public abstract class Jumper extends Actor
{
    protected int actorHeight;
    
    public Jumper() {
        actorHeight = getImage().getHeight();
    }
    
    public void act() {
        handleKeyPresses();
    }
    
    protected void handleKeyPresses() {
    }
    
}
