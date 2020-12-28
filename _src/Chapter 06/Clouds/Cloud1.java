import greenfoot.*;  

public class Cloud1 extends SideScrollingActor {
    private static final int SPEEDRANGE = 3;
    public Cloud1() {
        speed = -(Greenfoot.getRandomNumber(SPEEDRANGE) + 1);
    }
}
