import greenfoot.*;

public class ZombieInvasionWorld extends World {
    private static final int DELAY = 200;
    int bombDelayCounter = 0;  // Controls the rate of bombs

    public ZombieInvasionWorld() {    
        super(600, 400, 1); 
        prepare();
    }

    public void act() {
        if( bombDelayCounter > 0 ) bombDelayCounter--;
        if( Greenfoot.mouseClicked(null) && (bombDelayCounter == 0) ) {
            MouseInfo mi = Greenfoot.getMouseInfo();
            Boom pow = new Boom();
            addObject(pow, mi.getX(), mi.getY());
            bombDelayCounter = DELAY;
        }
    }

    private void prepare() {
        int i,j;
        for( i=0; i<5; i++) {
            Wall w = new Wall();
            addObject(w, 270, w.getImage().getHeight() * i);
        }
        for( i=0; i<2; i++) {
            for( j=0; j<8; j++) {
                House h = new House();
                addObject(h, 400 + i*60, (12 +h.getImage().getHeight()) * j);
            }
        }
        for( i=0; i<2; i++) {
            for( j=0; j<8; j++) {
                Zombie1 e = new Zombie1();
                addObject(e, 80 + i*60, 15 + (2 +e.getImage().getHeight()) * j);
            }
        }
        for( i=0; i<2; i++) {
            for( j=0; j<7; j++) {
                Zombie2 e = new Zombie2();
                addObject(e, 50 + i*60, 30 + (3 +e.getImage().getHeight()) * j);
            }
        }
    }
}
