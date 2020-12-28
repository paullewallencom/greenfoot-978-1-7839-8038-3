import greenfoot.*; 

public class Enemy extends Actor
{
    private int speed = 1;
    private int timeToChange = 1;

    public void act() {
        setLocation(getX(), getY() + speed);
        changeDisposition();
        // Remove this enemy if it goes off the screen
        checkRemove();
    }    

    public void setSpeed( int s) {
        speed = s;
    }

    private void changeDisposition() {
        int ypos = getY();
        int worldHeight = getWorld().getHeight();
        int marker1 = (int) (worldHeight * 0.5);
        int marker2 = (int) (worldHeight * 0.75);
        int marker3 = (int) (worldHeight * 0.90);
        if( timeToChange == 1 && ypos > marker1) {
            setImage("smiley4.png");
            timeToChange++;
        }
        else if( timeToChange == 2 && ypos > marker2) {
            setImage("smiley3.png");
            timeToChange++;
        }
        else if( timeToChange == 3 && ypos > marker3) {
            setImage("smiley5.png");
            timeToChange++;
        }
    }

    private void checkRemove() {
        World w = getWorld();
        if( getY() > w.getHeight() + 30 ) {
            w.removeObject(this);
        }
    }
}
