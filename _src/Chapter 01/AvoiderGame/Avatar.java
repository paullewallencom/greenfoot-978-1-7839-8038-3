import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Avatar extends Actor {

    public void act() {
        followMouse();
        checkForCollisions();
    }    
    
    private void checkForCollisions() {
        Actor enemy = getOneIntersectingObject(Enemy.class);
        if( enemy != null ) {  // If not empty, we hit an Enemy
            AvoiderWorld world = (AvoiderWorld) getWorld();
            world.endGame();
        }
    }
    
    private void followMouse() {
        MouseInfo mi = Greenfoot.getMouseInfo();
        // Check for null in case the mouse is off the screen
        if( mi != null ) {
            setLocation(mi.getX(), mi.getY());
        }
    }
}
