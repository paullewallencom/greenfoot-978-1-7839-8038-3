import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class AvoiderGameIntroScreen extends World
{

    /**
     * Constructor for objects of class AvoiderGameIntroScreen.
     * 
     */
    public AvoiderGameIntroScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1); 
    }

    public void act() {
        // Start the game if the user clicks the mouse anywhere 
        if( Greenfoot.mouseClicked(this) ) {
            AvoiderWorld world = new AvoiderWorld();
            Greenfoot.setWorld(world);
        }
    }

}
