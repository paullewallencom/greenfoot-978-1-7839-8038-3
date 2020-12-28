import greenfoot.*;

public class ScrollingActor extends Actor {
    public void setAbsoluteLocation(int dx) {
        setLocation(getX()+dx, getY());
    }
}
