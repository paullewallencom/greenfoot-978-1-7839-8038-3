import greenfoot.*; 

public class AvoiderGameStoryScreen extends World
{
    public AvoiderGameStoryScreen() {    
        super(600, 400, 1); 
    }
    
    public void act() {
        if( Greenfoot.mouseClicked(this) ) {
            World w = new AvoiderGameIntroScreen();
            Greenfoot.setWorld(w);
        }
    }
}

