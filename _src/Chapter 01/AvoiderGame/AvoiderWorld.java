import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class AvoiderWorld extends World
{
    private GreenfootSound bkgMusic;
    private Counter scoreBoard;
    private int enemySpawnRate = 20;
    private int enemySpeed = 1;
    private int nextLevel = 100;

    public AvoiderWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1, false); 

        // Initialize the music
        bkgMusic = new GreenfootSound("sounds/UFO_T-Balt.mp3");
        // Music Credit:  http://www.newgrounds.com/audio/listen/504436 by T-balt
        bkgMusic.playLoop(); // Play the music

        prepare();
    }

    public void act() {
        // Randomly add enemies to the world
        if( Greenfoot.getRandomNumber(1000) < enemySpawnRate) {
            Enemy e = new Enemy();
            e.setSpeed(enemySpeed);
            addObject( e, Greenfoot.getRandomNumber(getWidth()-20)+10, -30);
            // Give us some points for facing yet another enemy
            scoreBoard.setValue(scoreBoard.getValue() + 1);
        }
        increaseLevel();
    }

    private void increaseLevel() {
        int score = scoreBoard.getValue();

        if( score > nextLevel ) {
            enemySpawnRate += 2;
            enemySpeed++;
            nextLevel += 100;
        }
    }

    public void endGame() {
        bkgMusic.stop();
        AvoiderGameOverWorld go = new AvoiderGameOverWorld();
        Greenfoot.setWorld(go);
    }

    /**
     * Prepare the world for the start of the program. That is: create the initial
     * objects and add them to the world.
     */
    private void prepare()
    {
        Avatar avatar = new Avatar();
        addObject(avatar, 287, 232);
        scoreBoard = new Counter("Score: ");
        addObject(scoreBoard, 70, 20);
    }
}
