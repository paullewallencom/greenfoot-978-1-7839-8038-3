import greenfoot.*;  

public class Cloud3 extends SideScrollingActor {
    private static final int SPEEDRANGE = 5;
    public Cloud3() {
        speed = -(Greenfoot.getRandomNumber(SPEEDRANGE) + 1);
    }
}
