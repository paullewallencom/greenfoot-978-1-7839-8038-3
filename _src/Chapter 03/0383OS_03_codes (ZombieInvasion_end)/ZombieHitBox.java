import greenfoot.*;  
import java.awt.Color;
import java.util.*;

public class ZombieHitBox extends Actor
{
    GreenfootImage body;
    int offsetX;
    int offsetY;
    Actor host;

    public ZombieHitBox() {
        this(null, 20, 20, 0, 0, true);
    }

    public ZombieHitBox(Actor a, int w, int h, int dx, int dy, boolean visible) {
        host = a;
        offsetX = dx;
        offsetY = dy;
        body = new GreenfootImage(w, h);
        if( visible ) {
            body.setColor(Color.red);
            body.setTransparency(100);
            body.fill();
        }
        setImage(body);
    }

    public void act() {
        if( host.getWorld() != null ) {
            setLocation(host.getX()+offsetX, host.getY()+offsetY);
        } else {
            getWorld().removeObject(this);
        }
    }

    public List getHitBoxIntersections() {
        return getIntersectingObjects(Actor.class);
    }

}
