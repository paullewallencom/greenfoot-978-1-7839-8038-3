import greenfoot.*;  

public class Avatar extends Actor {
    private int health = 3;  
    private int hitDelay = 0;
    private int stunDelay = -1;
    private int lagDelay = -1;
    private int nextImage = 0;
    private Eye leftEye;
    private Eye rightEye;

    protected void addedToWorld(World w) {
        leftEye = new Eye();
        rightEye = new Eye();
        w.addObject(leftEye, getX()-10, getY()-8);
        w.addObject(rightEye, getX()+10, getY()-8);        
    }

    public void act() {
        followMouse();
        checkForCollisions();
    }    

    public void addHealth() {
        if( health < 3 ) {
            health++;
            if( --nextImage == 0 ) {
                setImage("skull.png");
            } else {
                setImage("skull" + nextImage + ".png");
            }
        }
    }

    public void lagControls() {
        lagDelay = 150;
    }

    public void stun() {
        stunDelay = 50;
    }

    private void checkForCollisions() {
        Actor enemy = getOneIntersectingObject(Enemy.class);
        if( hitDelay == 0 && enemy != null ) {  // If not empty, we hit an Enemy
            if( health == 0 ) {
                AvoiderWorld world = (AvoiderWorld) getWorld();
                world.endGame();
            }
            else {
                health--;
                setImage("skull" + ++nextImage + ".png");
                hitDelay = 50;
            }
        }
        if( hitDelay > 0 ) hitDelay--;
    }

    private void followMouse() {
        MouseInfo mi = Greenfoot.getMouseInfo();
        // Check for null in case the mouse is off the screen
        if( stunDelay < 0 ) {
            if( mi != null ) {
                if( lagDelay > 0 ) {
                    int stepX = (mi.getX() - getX())/40;
                    int stepY = (mi.getY() - getY())/40;
                    setLocation(stepX + getX(), stepY + getY());
                    --lagDelay;
                } else {
                    setLocation(mi.getX(), mi.getY());
                }
                
                leftEye.setLocation(getX()-10, getY()-8);
                rightEye.setLocation(getX()+10, getY()-8);
            }
        } else {
            stunDelay--;
        }
    }
}
