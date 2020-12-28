import greenfoot.*;  

public class Avatar extends Actor {
    private static final float MIN_STRENGTH = 0.5F;
    private int health = 3;  
    private int hitDelay = 0;
    private int stunDelay = -1;
    private int lagDelay = -1;
    private int nextImage = 0;
    private Eye leftEye;
    private Eye rightEye;
    private GamePad pad;
    private boolean useGamepad = true;
    private int gpStepX = 3;
    private int gpStepY = 3;
    private int gpLagStepX = 1;
    private int gpLagStepY = 1;

    public Avatar( GamePad p ) {
        pad = p;
        if( pad == null ) {
            useGamepad = false;
        }
    }

    protected void addedToWorld(World w) {
        leftEye = new Eye();
        rightEye = new Eye();
        w.addObject(leftEye, getX()-10, getY()-8);
        w.addObject(rightEye, getX()+10, getY()-8);        
    }

    public void act() {
        userControls();
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

    private void userControls() {
        if( stunDelay < 0 ) {

            if( lagDelay > 0 ) {
                if( useGamepad ) {
                    moveViaGamepad(true);
                } else {
                    moveViaMouse(true);
                }
                --lagDelay;
            } else {
                if( useGamepad ) {
                    moveViaGamepad(false);
                } else {
                    moveViaMouse(false);
                }
            }

            leftEye.setLocation(getX()-10, getY()-8);
            rightEye.setLocation(getX()+10, getY()-8);
        } else {
            stunDelay--;
        }
    }

    private void moveViaGamepad(boolean lag) {
        int stepX = lag ? gpLagStepX : gpStepX;
        int stepY = lag ? gpLagStepY : gpStepY;

        // This give the user the option of using the Dpad or left
        // analog stick.
        Direction dir = pad.getAxis( GamePad.Axis.DPAD );
        if ( dir.getStrength() == 0 ) {
            dir = pad.getAxis( GamePad.Axis.LEFT );
        }

        // gamepad controls
        if ( dir.getStrength() > MIN_STRENGTH ) {
            final int angle = dir.getAngle();

            if ( angle > 315 || angle <= 45 ) {
                setLocation(getX()+stepX, getY());
            } else if ( angle > 45 && angle <= 135 ) {
                setLocation(getX(), getY()+stepY);
            } else if ( angle > 135 && angle <= 225 ) {
                setLocation(getX()-stepX, getY());
            } else {
                setLocation(getX(), getY()-stepY);
            }
        }
    }

    private void moveViaMouse(boolean lag) {
        MouseInfo mi = Greenfoot.getMouseInfo();

        if( mi != null ) {
            if( lag ) {
                int stepX = (mi.getX() - getX())/40;
                int stepY = (mi.getY() - getY())/40;
                setLocation(stepX + getX(), stepY + getY());
            } else {
                setLocation(mi.getX(), mi.getY());
            }
        }
    }
}
