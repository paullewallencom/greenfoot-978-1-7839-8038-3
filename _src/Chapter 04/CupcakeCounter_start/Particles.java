import greenfoot.*;

public class Particles extends Enemy {
    private int turnRate = 2;
    private int speed = 5;
    private int lifeSpan = 50;
    
    public Particles(int tr, int s, int l) {
        turnRate = tr;
        speed = s;
        lifeSpan = l;
        setRotation(-90);
    }
    
    public void act() {
        move();
        remove();
    }
    
    private void move() {
        move(speed);
        turn(turnRate);
    }
    
    private void remove() {
        lifeSpan--;
        if( lifeSpan < 0 ) {
            getWorld().removeObject(this);
        }
    }
}
