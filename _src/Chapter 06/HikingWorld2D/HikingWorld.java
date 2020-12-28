import greenfoot.*;
import java.util.List;

public class HikingWorld extends World
{
    private int xOffset = 0;
    private int yOffset = 0;
    private final static int SWIDTH = 600;
    private final static int SHEIGHT = 400;
    private final static int WWIDTH = 1200;
    private final static int WHEIGHT = 1200;
    private GreenfootImage bimg;

    public HikingWorld()
    {    
        super(SWIDTH, SHEIGHT, 1, false); 
        bimg = new GreenfootImage("HikingWorldBackground2D.png");
        shiftWorld(0,0);        
        prepare();
    }

    public void shiftWorld(int dx, int dy) {
        if( (xOffset + dx) <= 0 && (xOffset + dx) >= SWIDTH - WWIDTH) {
            xOffset = xOffset + dx;
            shiftWorldBackground(dx, 0);
            shiftWorldActors(dx, 0);
        }
        if( (yOffset + dy) <= 0 && (yOffset + dy) >= SHEIGHT - WHEIGHT) {
            yOffset = yOffset + dy;
            shiftWorldBackground(0, dy);
            shiftWorldActors(0, dy);
        }
    }
    
    private void shiftWorldBackground(int dx, int dy) {
            GreenfootImage bkgd = new GreenfootImage(SWIDTH, SHEIGHT);
            bkgd.drawImage(bimg, xOffset, yOffset);
            setBackground(bkgd);
    }
    
    private void shiftWorldActors(int dx, int dy) {
        List<ScrollingActor> saList = getObjects(ScrollingActor.class);
        for( ScrollingActor a : saList ) {
            a.setAbsoluteLocation(dx, dy);
        }
    }

    private void prepare()
    {
        HedgeHog hh1 = new HedgeHog();
        addObject(hh1, 600, 600);
        Lemur l = new Lemur();
        addObject(l, 300, 900);
        HedgeHog hh2 = new HedgeHog();
        addObject(hh2, 900, 300);
        Lake lake = new Lake();
        addObject(lake, 900, 1100);
        Hiker hiker = new Hiker();
        addObject(hiker, 90, 275);
    }
}
