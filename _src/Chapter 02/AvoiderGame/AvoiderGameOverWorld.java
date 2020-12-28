import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class AvoiderGameOverWorld extends World
{

    /**
     * Constructor for objects of class AvoiderGameOverWorld.
     * 
     */
    public AvoiderGameOverWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1); 
    }

    public void act() {
        // Restart the game if the user clicks the mouse anywhere 
        if( Greenfoot.mouseClicked(this) ) {
            AvoiderWorld world = new AvoiderWorld();
            Greenfoot.setWorld(world);
        }
    }
}
