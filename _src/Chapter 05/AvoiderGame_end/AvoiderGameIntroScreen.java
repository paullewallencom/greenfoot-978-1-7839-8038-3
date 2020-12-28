import greenfoot.*; 

public class AvoiderGameIntroScreen extends World
{
    Actor startButton, creditButton, storyButton;

    public AvoiderGameIntroScreen()
    {    
        super(600, 400, 1); 
        startButton = addButton("Start Game", getWidth()/2, getHeight()*2/3);
        creditButton = addButton("Credits Screen", getWidth()/2, (getHeight()*2/3) + 40);
        storyButton = addButton("Story Screen", getWidth()/2, (getHeight()*2/3) + 80);
    }

    public void act() {
        // Start the game if the user clicks the mouse anywhere 
        if( Greenfoot.mouseClicked(startButton) ) {
            AvoiderWorld world = new AvoiderWorld();
            Greenfoot.setWorld(world);
        } else if( Greenfoot.mouseClicked(creditButton) ) {
            AvoiderGameCreditScreen world = new AvoiderGameCreditScreen();
            Greenfoot.setWorld(world);
        } else if( Greenfoot.mouseClicked(storyButton) ) {
            AvoiderGameStoryScreen world = new AvoiderGameStoryScreen();
            Greenfoot.setWorld(world);
        }
    }
    
    private Actor addButton(String s, int x, int y) {
        Actor button = new Label(s, 24);
        addObject(button, x, y);
        return button;
    }

}
