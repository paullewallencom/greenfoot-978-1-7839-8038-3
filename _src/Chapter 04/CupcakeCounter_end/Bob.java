/**
 * Note, the images for this actor were acquired at www.wikia.com.  The 
 * sprite sheet was submitted by the user "Mecha Mario".  The direct url
 * to it is: http://smbz.wikia.com/wiki/File:Dawson_Sprite_Sheet.PNG
 * 
 * All assets at www.wikia.com are licensed under the Creative Commons 
 * Attribution-Share Alike License 3.0 (Unported) (CC-BY-SA).
 */
import greenfoot.*;

public class Bob extends Jumper {
    private int speed = 3;
    private int animationDelay = 0;
    private int frame = 0;
    private GreenfootImage[] leftImages;
    private GreenfootImage[] rightImages;
    private int actorWidth;

    private static final int DELAY = 3;
    private static final int SIDEHIT = 10;

    public Bob() {
        super();

        rightImages = new GreenfootImage[5];
        leftImages = new GreenfootImage[5];

        for( int i=0; i<5; i++ ) {
            rightImages[i] = new GreenfootImage("images/Dawson_Sprite_Sheet_0" + Integer.toString(3+i) + ".png");
            leftImages[i] = new GreenfootImage(rightImages[i]);
            leftImages[i].mirrorHorizontally();
        }

        actorWidth = getImage().getWidth();
    }

    public void act() {
        super.act();
        checkDead();
        eatReward();
    }

    private void checkDead() {
        Actor enemy = getOneIntersectingObject(Enemy.class);
        if( enemy != null ) {
            endGame();
        }
        if( getY() > getWorld().getHeight() + 300 ) {
            endGame();
        }
    }
    
    private void endGame() {
            Greenfoot.stop();        
    }
    
    private void eatReward() {
        Cupcake c = (Cupcake) getOneIntersectingObject(Cupcake.class);
        if( c != null ) {
        	CupcakeWorld rw = (CupcakeWorld) getWorld();
            rw.removeObject(c);
        	rw.addCupcakeCount(1);
        }
    }

    // Called by superclass
    protected void handleKeyPresses() {
        super.handleKeyPresses();

        if( Greenfoot.isKeyDown("left") ) {
            if( canMoveLeft() ) {moveLeft();}
        }
        if( Greenfoot.isKeyDown("right") ) {
            if( canMoveRight() ) {moveRight();}
        }
    }

    private boolean canMoveLeft() {
        if( getX() < 5 ) return false;
        return true;
    }

    private void moveLeft() {
        setLocation(getX() - speed, getY());
        if( animationDelay % DELAY == 0 ) { 
            animateLeft();
            animationDelay = 0;
        }
        animationDelay++;
    }

    private void animateLeft() {
        setImage( leftImages[frame++]);
        frame = frame % 5;
        actorWidth = getImage().getWidth();
    }

    private boolean canMoveRight() {
        if( getX() > getWorld().getWidth() - 5) return false;
        return true;
    }

    private void moveRight() {
        setLocation(getX() + speed, getY());
        if( animationDelay % DELAY == 0 )  { 
            animateRight();
            animationDelay = 0;
        }
        animationDelay++;
    }

    private void animateRight() {
        setImage( rightImages[frame++]);
        frame = frame % 5;
        actorWidth = getImage().getWidth();
    }
}
