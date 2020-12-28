import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class TiledWorldPathfinding  {
    private String []world;
    private String validSpaces;
    private int worldColumns;
    private int worldRows;
    private Tile[][] tiledWorld;

    /* constructors */
    public TiledWorldPathfinding(String []w, String vs) {
        world = w;  // This is a shallow copy...should be deep
        worldColumns = w[0].length();  // number of columns
        worldRows = w.length; // number of rows
        tiledWorld = new Tile[worldRows][worldColumns];
        validSpaces = vs;
        resetWorld();
    }

    /* ability methods */
    public void changeWorld( String []w ) {
        world = w;  // Should be a deep copy
        resetWorld();
    }

    public Stack<Point> findShortestFeasiblePath(Point start, Point end) {
        Queue<Tile> openList = new PriorityQueue<Tile>();
        Queue<Tile> closedList = new PriorityQueue<Tile>();
        Stack<Point> answer = new Stack<Point>();

        // Check for trivial case
        if( start.equals(end) ) {
            answer.push(start);
            return answer;
        }

        // Check that both start and end are walkable
        if( !tiledWorld[start.row][start.col].isWalkable() ) {
            return null;
        }
        if( !tiledWorld[end.row][end.col].isWalkable() ) {
            return null;
        }

        // Mark location of end point
        tiledWorld[end.row][end.col].setEndNode();

        // Add starting node to open list
        openList.add(tiledWorld[start.row][start.col]);

        // A* algorithm
        runAStar(openList, closedList, end);

        // Here..need to derive the answer area from the marked up TileWorld
        if( tiledWorld[end.row][end.col].getParent() == null ) {
            resetWorld();
            return null;
        } else {
            deriveWaypoints(answer, end);
        }

        // Prepare for next time
        resetWorld();

        // return result
        return answer;
    }

    /* private methods */
    private void runAStar(Queue<Tile> openList, Queue<Tile> closedList, Point end) {
        boolean done = false;
        Tile t;

        while( !openList.isEmpty() && !done ) {
            t = openList.remove();
            done = done || processNeighbor(t, t.getUp(), openList, end);
            done = done || processNeighbor(t, t.getDown(), openList, end);
            done = done || processNeighbor(t, t.getLeft(), openList, end);
            done = done || processNeighbor(t, t.getRight(), openList, end);
            t.setDone();
            closedList.add(t); // Since mark "Done", may not need a closed list
        }
    }

    private boolean processNeighbor( Tile parent, Tile node, Queue<Tile> openList, Point end) {
        boolean retval = false;

        if( node != null && !node.isDone() && node.isWalkable() ) {
            if( node.isEndNode() ) {  // Are we done?
                node.setParent(parent);
                retval = true;  // FOUND THE END NODE
            } else {
                node.setParent(parent);
                node.setG(1 + parent.getG());
                node.setH(calculateManhattenDistance(node.getPoint(), end));
                openList.add(node);
            }
        }    
        return retval;
    }

    private int calculateManhattenDistance(Point start, Point end) {
        return Math.abs(start.row - end.row) + Math.abs(start.col - end.col);
    }

    private void deriveWaypoints(Stack<Point> a, Point end) {
        Tile tp = tiledWorld[end.row][end.col];

        while( tp != null ) {
            a.push(tp.getPoint());
            tp = tp.getParent();
        }
    }
    
    private void resetWorld() {
        for( int i = 0; i<worldRows; i++ ) {
            for(int j = 0; j<worldColumns; j++) {
                tiledWorld[i][j] = new Tile();
                tiledWorld[i][j].setPoint(i,j);
            }
        }        
        for( int i = 0; i<worldRows; i++ ) {
            for(int j = 0; j<worldColumns; j++) {
                Tile t = tiledWorld[i][j];;
                if( validSpaces.indexOf(world[i].charAt(j)) == -1) {
                    t.setNotWalkable();
                } else {
                    if( i == 0 ) {
                        t.setUp(null);
                    } else {
                        t.setUp(tiledWorld[i-1][j]);
                    }
                    if( i == worldRows-1 ) {
                        t.setDown(null);
                    } else {
                        t.setDown(tiledWorld[i+1][j]);
                    }
                    if( j == 0 ) {
                        t.setLeft(null);
                    } else {
                        t.setLeft(tiledWorld[i][j-1]);
                    }
                    if( j == worldColumns-1 ) {
                        t.setRight(null);
                    } else {
                        t.setRight(tiledWorld[i][j+1]);
                    }
                }
            }
        }        
    }

}
