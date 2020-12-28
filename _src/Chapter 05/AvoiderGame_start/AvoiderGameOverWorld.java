import greenfoot.*; 

public class AvoiderGameOverWorld extends World {

    public AvoiderGameOverWorld() {    
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
