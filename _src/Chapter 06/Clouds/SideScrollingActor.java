import greenfoot.*;  

public abstract class SideScrollingActor extends Actor
{
    public int speed = -1;  // Moves right to left
    private static final int BOUNDARY = 100;
    
    public void act() 
    {
        move(speed);
        checkOffScreen();
    }  
    
    private void checkOffScreen() {
        if( getX() < -BOUNDARY || getX() > getWorld().getWidth() + BOUNDARY) {
            getWorld().removeObject(this);
        }
    }
}
