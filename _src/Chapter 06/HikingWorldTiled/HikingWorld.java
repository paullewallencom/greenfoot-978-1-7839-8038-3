import greenfoot.*;
import java.util.List;

public class HikingWorld extends World
{
    private int xOffset = 0;
    private final static int SWIDTH = 600;
    private final static int SHEIGHT = 400;
    private final static int WWIDTH = 1200;
    private final static int TWIDTH = 25;
    private final static int THEIGHT = 25;
    
    private final static String[] WORLD = {
        "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB",
        "BWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWB",
        "BWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWB",
        "BWWWWWWUUWWUUWWUUWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWB",
        "BWWWWWWUUWWUUWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWB",
        "BWWWWWWUUUUUUWWUUWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWB",
        "BWWWWWWUUWWUUWWUUWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWB",
        "BWWWWWWUUWWUUWWUUWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWB",
        "BWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWB",
        "BWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWB",
        "BWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWB",
        "BWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWB",
        "BWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWB",
        "BWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWB",
        "BWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWB",
        "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB"
    };

    public HikingWorld()
    {    
        super(SWIDTH, SHEIGHT, 1, false); 
        createWorldFromTiles();
        shiftWorld(0);        
        prepare();
    }

    public void shiftWorld(int dx) {
        if( (xOffset + dx) <= 0 && (xOffset + dx) >= SWIDTH - WWIDTH) {
            xOffset = xOffset+dx;
            shiftWorldActors(dx);
        }
    }
        
    private void shiftWorldActors(int dx) {
        List<ScrollingActor> saList = getObjects(ScrollingActor.class);
        for( ScrollingActor a : saList ) {
            a.setAbsoluteLocation(dx);
        }
    }
    
    private void createWorldFromTiles() {
        for( int i=0; i < WORLD.length; i++ ) {
            for( int j=0; j < WWIDTH/TWIDTH; j++ ) {
                addActorAtTileLocation(WORLD[i].charAt(j), j, i);
            }
        }
    }
    
    private void addActorAtTileLocation(char c, int x, int y) {
        Actor tile = null;
        switch(c) {
            case 'W':
                tile = new WhiteBlock();
                break;
            case 'B':
                tile = new BlackBlock();
                break;
            case 'U':
                tile = new BlueBlock();
                break;
        }
        if( tile != null) addObject(tile, 12+x*TWIDTH, 12+y*THEIGHT);
        
    }

    private void prepare()
    {
        Lake lake = new Lake();
        addObject(lake, WWIDTH-300, 300);
        Hiker hiker = new Hiker();
        addObject(hiker, 90, 275);
    }
}
