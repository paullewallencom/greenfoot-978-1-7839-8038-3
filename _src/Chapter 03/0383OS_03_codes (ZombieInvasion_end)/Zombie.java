import greenfoot.*;  
import java.util.*;

public class Zombie extends Actor {
    private int counter, stationaryX, amplitude;
    private int step = 4;
    private ZombieHitBox zbh;

    protected void addedToWorld(World w) {
        // initial settings
        stationaryX = getX();
        amplitude = Greenfoot.getRandomNumber(6) + 2;
        // hitbox (hidden sprite)
        zbh = new ZombieHitBox(this, 10, 25, 10, 5, true);
        getWorld().addObject(zbh, getX(), getY());
    }

    public void act() {
        shake();
        if( canMarch() ) {
            stationaryX = stationaryX + step;
        }
    }  

    public void shake() {
        counter++;
        setLocation((int)(stationaryX + amplitude*Math.sin(counter/2)), getY());
    }

    // Initial version
    /*private boolean canMarch() {
    List<Actor> things = getIntersectingObjects(Actor.class);
    for( int i = 0; i < things.size(); i++ ) {
    if( things.get(i).getX() > getX() + 20 ) {
    return false;
    }
    }
    return true;
    }*/

    // getOneObjectAtOffset version
    /*private boolean canMarch() {
    int i=0;
    while(i<=step) {
    int front = getImage().getWidth()/2;
    if( getOneObjectAtOffset(i+front, 0, Actor.class) != null ) {
    return false;
    }
    i++;
    }
    return true;
    }*/

    // getObjectsAtOffset version
    /*private boolean canMarch() {
    int front = getImage().getWidth()/2;
    int i = 1;
    while(i<=step) {
    List<Actor> things = getObjectsAtOffset(front + i, 0, Actor.class);
    if( things.size() > 0 )  {
    for(int j=0; j < things.size(); j++ ) {
    if( things.get(j) instanceof Zombie ) {
    int toss = Greenfoot.getRandomNumber(100) < 50 ? 1 : -1;
    Zombie z = (Zombie) things.get(j);
    z.setLocation(z.getX(),z.getY()+toss);
    }
    }
    return false;
    }
    i++;
    }
    return true;
    }*/

    // hidden sprite version (Note...these are dumb zombies..no path finding
    // in future chapter will do pathfinding
    private boolean canMarch() {
        if( zbh.getWorld() != null ) {
            List<Actor> things = zbh.getHitBoxIntersections();
            if( things.size() > 1 )  {
                int infront = 0;
                for(int i=0; i < things.size(); i++ ) {
                    Actor a = things.get(i);
                    if( a == this || a instanceof ZombieHitBox) continue;
                    if( a instanceof Zombie) {
                        int toss = Greenfoot.getRandomNumber(100) < 50 ? 1 : -1;
                        infront += (a.getX() > getX()) ? 1 : 0;
                        if( a.getX() >= getX() ) 
                            a.setLocation(a.getX(),a.getY()+toss);
                    } else {
                        return false;
                    }
                }
                if( infront > 0 ) {
                    return false;
                } else { 
                    return true;
                }
            }
            return true;
        } else {
            getWorld().removeObject(this);
        }
        return false;
    }
}
