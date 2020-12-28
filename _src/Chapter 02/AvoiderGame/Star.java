import greenfoot.*;  
import java.awt.Color;

public class Star extends Actor
{
    int speed = 1;
    int twinkleTime = 0;
    int currentTransparency = 0;

    public Star() {
        GreenfootImage img = new GreenfootImage(10,10);
        img.setColor(Color.white);
        img.fillOval(0,0,10,10);
        setImage(img);
    }

    public void act() {
        setLocation(getX(), getY()+speed);
        checkRemove();
        checkTwinkle();
    } 

    public void setSpeed( int s) {
        speed = s;
    }

    private void checkRemove() {
        World w = getWorld();
        if( getY() > w.getHeight() + 30 ) {
            w.removeObject(this);
        }
    }  
    
    private void checkTwinkle() {
		GreenfootImage img = getImage();
        if( twinkleTime > 0 ) {
            if( twinkleTime == 1) {
                img.setTransparency(currentTransparency);
            }
            twinkleTime--;
        } else {
            if( Greenfoot.getRandomNumber(10000) < 10) {
                twinkleTime = 10;
                currentTransparency = img.getTransparency();
                img.setTransparency(0);
            }
        }
    }

}
