import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.IllegalArgumentException;

public class AvoiderGameIntroScreen extends World
{
    private GamePad pad;

    /**
     * Constructor for objects of class AvoiderGameIntroScreen.
     * 
     */
    public AvoiderGameIntroScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1); 

        try {
            pad = GamePad.getGamePad();
            pad.runConfigurePad();
        } catch(IllegalArgumentException e) {
            System.out.println( "Exception caught: " + e.getMessage() );
            pad = null;
        }
    }

    public void act() {
        // Start the game if the user clicks the mouse anywhere 
        if( Greenfoot.mouseClicked(this) ) {
            AvoiderWorld world = new AvoiderWorld(pad);
            Greenfoot.setWorld(world);
        }
    }
}
