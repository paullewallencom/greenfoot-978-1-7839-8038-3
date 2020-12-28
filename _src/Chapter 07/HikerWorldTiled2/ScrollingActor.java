import greenfoot.*;

public class ScrollingActor extends Actor {
    /* state methods */
    public void setAbsoluteLocation(int dx) {
        setLocation(getX()+dx, getY());
    }
}
