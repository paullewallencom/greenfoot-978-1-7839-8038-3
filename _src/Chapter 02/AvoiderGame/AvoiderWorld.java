import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class AvoiderWorld extends World
{
    private GreenfootSound bkgMusic;
    private Counter scoreBoard;
    private int enemySpawnRate = 20;
    private int enemySpeed = 1;
    private int nextLevel = 50;
    private int cupcakeFrequency = 10;
    private int cloverFrequency = 10;
    private int rockFrequency = 1;

    public AvoiderWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1, false); 

        // Initialize the music
        bkgMusic = new GreenfootSound("sounds/UFO_T-Balt.mp3");
        // Music Credit:  http://www.newgrounds.com/audio/listen/504436 by T-balt
        bkgMusic.playLoop(); // Play the music

        setPaintOrder(Eye.class, Avatar.class, Enemy.class, PowerItems.class, 
                        Counter.class);
        prepare();
        generateInitialStarField();
    }

    public void act() {
        generateEnemies();
        generateStars(-1);
        generatePowerItems();
        increaseLevel();
    }

    private void generateEnemies() {
        // Randomly add enemies to the world
        if( Greenfoot.getRandomNumber(1000) < enemySpawnRate) {
            Enemy e = new Enemy();
            e.setSpeed(enemySpeed);
            addObject( e, Greenfoot.getRandomNumber(getWidth()-20)+10, -30);
            // Give us some points for facing yet another enemy
            scoreBoard.setValue(scoreBoard.getValue() + 1);
        }        
    }

    private void generateStars(int yLoc) {
        // Create a moving background star field
        if( Greenfoot.getRandomNumber(1000) < 350) {
            Star s = new Star();
            GreenfootImage image = s.getImage();
            if( Greenfoot.getRandomNumber(1000) < 300) {
                // this is a close bright star
                s.setSpeed(3);  
                image.setTransparency(Greenfoot.getRandomNumber(25) + 225);
                image.scale(4,4); 
            } else {
                // this is a further dim star
                s.setSpeed(2);
                image.setTransparency(Greenfoot.getRandomNumber(50) + 100);
                image.scale(2,2);
            }
            s.setImage(image);
            addObject( s, Greenfoot.getRandomNumber(getWidth()-20)+10, yLoc);
        }        
    }
    
    private void generatePowerItems() {
        generatePowerItem(0, cupcakeFrequency); // new Cupcake
        generatePowerItem(1, cloverFrequency); // new Clover
        generatePowerItem(2, rockFrequency); // new Health
    }
    
    private Actor createPowerItem(int type, int targetX, int targetY, int expireTime) {
        switch(type) {
            case 0:  return new Cupcake(targetX, targetY, expireTime);
            case 1:  return new Clover(targetX, targetY, expireTime);
            case 2:  return new Rock(targetX, targetY, expireTime);
        }
        return null;
    }

    private void generatePowerItem(int type, int freq) {
        if( Greenfoot.getRandomNumber(1000) < freq ) {
            int targetX = Greenfoot.getRandomNumber(getWidth() -80) + 40;
            int targetY = Greenfoot.getRandomNumber(getHeight()/2) + 20;
            Actor a = createPowerItem(type, targetX, targetY, 100);
            if( Greenfoot.getRandomNumber(100) < 50) {
                addObject(a, getWidth() + 20, 
                            Greenfoot.getRandomNumber(getHeight()/2) + 30);
            } else {
                addObject(a, -20,  
                            Greenfoot.getRandomNumber(getHeight()/2) + 30);
            }
        }
    }

    private void increaseLevel() {
        int score = scoreBoard.getValue();

        if( score > nextLevel ) {
            enemySpawnRate += 3;
            enemySpeed++;
            cupcakeFrequency += 3;
            cloverFrequency += 3;
            rockFrequency += 2;
            nextLevel += 50;
        }
    }

    public void endGame() {
        bkgMusic.stop();
        AvoiderGameOverWorld go = new AvoiderGameOverWorld();
        Greenfoot.setWorld(go);
    }

    private void prepare()
    {
        Avatar avatar = new Avatar();
        addObject(avatar, 287, 232);
        scoreBoard = new Counter("Score: ");
        addObject(scoreBoard, 70, 20);
    }
    
    private void generateInitialStarField() {
        int i = 0;
        
        for( i=0; i<getHeight(); i++ ) {
            generateStars(i);
        }
    }
}
