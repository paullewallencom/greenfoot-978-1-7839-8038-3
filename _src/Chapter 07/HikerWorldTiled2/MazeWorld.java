import greenfoot.*;
import java.util.List;
import java.util.Stack;

public class MazeWorld extends World {
    private int xOffset = 0;
    private Hiker hiker;
    private final static int SWIDTH = 600;
    private final static int SHEIGHT = 400;
    private final static int WWIDTH = 1200;
    private final static int TWIDTH = 25;
    private final static int THEIGHT = TWIDTH;
    private final static int TILEOFFSET = TWIDTH/2;
    private final static String validSpaces = "WG";
    
    private final static String[] WORLD = {
        "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB",
        "BWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWUWWWWB",
        "BWWWWWWWWWWWWWUUWWWWWWWWUUUUUUUWWWWWWWWWWWUWWWWB",
        "BWWWWWUUUUUWWWUUUWWWWWWWWWWWWWWWWWWWWWWWWWUWWWWB",
        "BWWWWWUUUUUWWWWWWWWWWWWWWWWWWWWWWWWWUWWWWUUUWWWB",
        "BWWWWWWWWWWWWWWWWWUUUUUWWWWWWWWUUUUUUWWWWWWWWWWB",
        "BWWWWWWWWWWWWWWWWWUUUUWWWWWWWWWUUUUUUUUWWWWWWWWB",
        "BWWWWUUUUUUUWWWUWWWWWWWWWWWWWWWUWWWWWWWWWWWWWWWB",
        "BWWWWWWWUUUWWWWUWWWWWWWWWWUWWWWUWWWWWWWWWWWWWWWB",
        "BWWWWWWWWWWWWWWWWWWWWWWWWWUWWWWWWWWWWWWWWWWWUWWB",
        "BWWWWWWWWWWWWWWWWWWWUUUUUUUWWWWWWWWWUUUUWWWWUWWB",
        "BWWWWWWWWWWWWWUUWWWWUWWWWWWWWWWWWWWWUUUUWWWWUWWB",
        "BWWWWWWWUUUUUUUUUWWWWWWWWWWWWWWWWWWWUUUUUUWWUWWB",
        "BWWWWWWWUUUUUUUUUWWWWWWWWWUUWWWWWWWWWWWWWWWWUWWB",
        "BWWWWWWWUWWWWWWWWWWWWWWWWWUUWWWWWWWWWWWWWWWWUWGB",
        "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB"
    };

    /* constructors */
    public MazeWorld() {    
        super(SWIDTH, SHEIGHT, 1, false); 
        createWorldFromTiles();
        shiftWorld(0);        
        prepare();
    }

    /* ability methods */
    public void shiftWorld(int dx) {
        if( (xOffset + dx) <= 0 && (xOffset + dx) >= SWIDTH - WWIDTH) {
            xOffset = xOffset+dx;
            shiftWorldActors(dx);
        }
    }
    
    /* accessor methods */
    public int getTileWidth() {
        return TWIDTH;
    }
    
    public int getTileHeight() {
        return THEIGHT;
    }
    
    public int getTileOffset() {
        return TILEOFFSET;
    }
    
    public String[] getStringWorld() {
        return WORLD;
    }
    
    public int getXHiker() {
        return hiker.getX()-xOffset;
    }
    
    public int getYHiker() {
        return hiker.getY();
    }
    
    public String getValidSpaces() {
        return validSpaces;
    }
    
    /* private methods */
    private void shiftWorldActors(int dx) {
        List<ScrollingActor> saList = getObjects(ScrollingActor.class);
        for( ScrollingActor a : saList ) {
            a.setAbsoluteLocation(dx);
        }
    }
    
    private void createWorldFromTiles() {
        for( int i=0; i < WORLD.length; i++ ) {
            for( int j=0; j < WORLD[i].length(); j++ ) {
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
            case 'G':
                tile = new GoldBlock();
                break;
        }
        if( tile != null) addObject(tile, TILEOFFSET+x*TWIDTH, TILEOFFSET+y*THEIGHT);
        
    }

    private void prepare()
    {
        hiker = new Hiker();
        addObject(hiker, 80, 200);
        addObject(new Mouse(), 60,40);
        addObject(new Spider(), 1000,40);
        addObject(new Spider(), 120,340);
        addObject(new Spider(), 1050,250);
        addObject(new Snake(), 1050,250);
        addObject(new Mouse(), 1000,200);
        addObject(new Snake(), 400,260);
    }
}
