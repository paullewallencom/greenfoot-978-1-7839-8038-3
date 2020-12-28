import greenfoot.*;
import java.util.Stack;

public class Mouse extends ScrollingEnemy {
    private TiledWorldPathfinding twp;
    private Stack<Point> apath;
    private int walkDelay = -1;
    private final static int WALKDELAY = 40;
    private int searchDelay = -1;
    private final static int SEARCHDELAY = 130;
    private int prevRow = 0;
    private int prevCol = 0;
    
    /* initilization */
    protected void addedToWorld(World w) {
        MazeWorld mw = (MazeWorld) w;
        super.addedToWorld(w);
        twp = new TiledWorldPathfinding(mw.getStringWorld(), mw.getValidSpaces());
        prevRow = getY()/mw.getTileWidth();
        prevCol = getX()/mw.getTileWidth();
        setLocation(prevCol*mw.getTileWidth()+mw.getTileWidth()/2, 
                    prevRow*mw.getTileWidth()+mw.getTileWidth()/2);
    }

    /* ability methods */
    protected void sense() {
        // A* pathfinding determines direction
        if( --searchDelay < 0) {
            MazeWorld w = (MazeWorld) getWorld();
            int hikerCol = w.getXHiker()/w.getTileWidth();
            int hikerRow = w.getYHiker()/w.getTileWidth();
            apath = twp.findShortestFeasiblePath(new Point(prevRow,prevCol), new Point(hikerRow,hikerCol));
            if( apath != null && !apath.isEmpty() ) apath.pop();
            searchDelay = SEARCHDELAY;
        }
    }

    protected void reaction() {
        // Move in direction chosen by A* pathfinding
        if( --walkDelay < 0 ) {
            walkDelay = WALKDELAY;
            if( apath != null && !apath.isEmpty() ) {
                Point p = apath.pop();
                MazeWorld w = (MazeWorld) getWorld();
                speedX = (p.col-prevCol) * w.getTileWidth();
                speedY = (p.row-prevRow) * w.getTileWidth();
                prevCol = p.col;
                prevRow = p.row;
            }
        } else {
            speedX = 0;
            speedY = 0;
        }
    }

    /* unit testing */
    public void unitTestPathFinding() {
        // Below is solely for testing
        twp = new TiledWorldPathfinding(((MazeWorld)getWorld()).getStringWorld(), "WG"); 
        apath = twp.findShortestFeasiblePath(new Point(0,1), new Point(0,1));
        if( apath != null && apath.size() != 1) System.out.println("Test 1 Failed.");
        apath = twp.findShortestFeasiblePath(new Point(0,1), new Point(1,7));
        if( apath != null ) System.out.println("Test 2 Failed.");
        apath = twp.findShortestFeasiblePath(new Point(1,7), new Point(1,47));
        if( apath != null ) System.out.println("Test 3 Failed.");
        apath = twp.findShortestFeasiblePath(new Point(1,7), new Point(1,8));
        if( apath == null ) System.out.println("Test 4 Failed.");
        apath = twp.findShortestFeasiblePath(new Point(1,7), new Point(1,8));
        if( apath != null  && apath.size() != 2  ) System.out.println("Test 5 Failed.");
        apath = twp.findShortestFeasiblePath(new Point(1,7), new Point(1,9));
        if( (apath == null) ||  (apath.size() != 3 &&
            (new Point(1,9)).equals(apath.pop())) ) System.out.println("Test 6 Failed.");
        apath = twp.findShortestFeasiblePath(new Point(5,2), new Point(5,15));
        if( (apath == null) ||  (apath.size() != 14 &&
            (new Point(5,15)).equals(apath.pop())) ) System.out.println("Test 7 Failed.");
        apath = twp.findShortestFeasiblePath(new Point(6,29), new Point(5,38));
        if( (apath == null) ||  (apath.size() < 15 &&
            (new Point(5,38)).equals(apath.pop())) ) System.out.println("Test 8 Failed.");        
        apath = twp.findShortestFeasiblePath(new Point(2,4), new Point(13,46));
        if( (apath == null) ||  (apath.size() < 64 &&
            (new Point(13,46)).equals(apath.peek())) ) System.out.println("Test 9 Failed.");        
        apath = twp.findShortestFeasiblePath(new Point(6,6), new Point(14,37));
        if( (apath == null) ||  (apath.size() < 64 &&
            (new Point(14,37)).equals(apath.pop())) ) System.out.println("Test 10 Failed.");        
        System.out.println("Done");           
    }

}
