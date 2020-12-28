import greenfoot.*;
import java.util.List;

public class HikingWorld extends World
{
    private int xOffset = 0;
    private final static int SWIDTH = 600;
    private final static int SHEIGHT = 400;
    private final static int WWIDTH = 2400;
    private GreenfootImage bimg;

    public HikingWorld()
    {    
        super(SWIDTH, SHEIGHT, 1, false); 
        bimg = new GreenfootImage("HikingWorldBackground.png");
        shiftWorld(0);        
        prepare();
    }

    public void shiftWorld(int dx) {
        if( (xOffset + dx) <= 0 && (xOffset + dx) >= SWIDTH - WWIDTH) {
            xOffset = xOffset + dx;
            shiftWorldBackground(dx);
            shiftWorldActors(dx);
        }
    }
    
    private void shiftWorldBackground(int dx) {
            GreenfootImage bkgd = new GreenfootImage(SWIDTH, SHEIGHT);
            bkgd.drawImage(bimg, xOffset, 0);
            setBackground(bkgd);
    }
    
    private void shiftWorldActors(int dx) {
        List<ScrollingActor> saList = getObjects(ScrollingActor.class);
        for( ScrollingActor a : saList ) {
            a.setAbsoluteLocation(dx);
        }
    }

    private void prepare()
    {
        HedgeHog hh1 = new HedgeHog();
        addObject(hh1, 900, 250);
        Lemur l = new Lemur();
        addObject(l, 1200, 300);
        HedgeHog hh2 = new HedgeHog();
        addObject(hh2, 1500, 250);
        Lake lake = new Lake();
        addObject(lake, 2100, 300);
        Hiker hiker = new Hiker();
        addObject(hiker, 90, 275);
    }
}
