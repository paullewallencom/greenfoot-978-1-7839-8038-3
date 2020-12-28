import greenfoot.*;

public class ScrollingActor extends Actor {
    public void setAbsoluteLocation(int dx, int dy) {
        setLocation(getX()+dx, getY()+dy);
    }
}
