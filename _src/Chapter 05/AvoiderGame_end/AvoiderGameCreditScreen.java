import greenfoot.*; 

public class AvoiderGameCreditScreen extends World
{
    public AvoiderGameCreditScreen() {    
        super(600, 400, 1); 
    }
    
    public void act() {
        if( Greenfoot.mouseClicked(this) ) {
            World w = new AvoiderGameIntroScreen();
            Greenfoot.setWorld(w);
        }
    }
}
