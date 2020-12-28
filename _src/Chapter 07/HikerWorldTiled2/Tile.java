import java.util.Comparator;

public class Tile implements Comparable<Tile>  {
    private int g = 0, h = 0;
    private Tile up, down, left, right, parent;
    private Point location;
    private boolean walkable = true;
    private boolean done = false;
    private boolean isEndNode = false;
    
    /* constructors */
    public Tile() {
        parent = up = down = left = right = null;
        location = new Point(0,0);
    }

    public Tile(Tile u, Tile d, Tile l, Tile r) {
        up = u;
        down = d;
        left = l;
        right = r;
        parent = null;
        location = new Point(0,0);
    }
    
    /* state methods */
    public boolean isWalkable() {
        return walkable;
    }
    
    public void setNotWalkable() {
        walkable = false;
    }
    
    public boolean isDone() {
        return done;
    }
    
    public void setDone() {
        done = true;
    }
    
    public boolean isEndNode() {
        return isEndNode;
    }
    
    public void setEndNode() {
        isEndNode = true;
    }
    
    /* neighbors */
    public void setParent(Tile t) {
        parent = t;
    }
    
    public Tile getParent() {
        return parent;
    }

    public void setUp(Tile t) {
        up = t;
    }
    
    public Tile getUp() {
        return up;
    }

    public void setDown(Tile t) {
        down = t;
    }
    
    public Tile getDown() {
        return down;
    }

    public void setRight(Tile t) {
        right = t;
    }
    
    public Tile getRight() {
        return right;
    }

    public void setLeft(Tile t) {
        left = t;
    }
    
    public Tile getLeft() {
        return left;
    }

    /* accessor methods */
    public void setPoint(int _row, int _col) {
        location.row = _row;
        location.col = _col;
    }
    
    public Point getPoint() {
        return location;
    }
    
    public void setG(int n) {
        g = n;
    }
    
    public int getG() {
        return g;
    }
    
    public void setH( int n) {
        h = n;
    }
    
    public int getH() {
        return h;
    }
    
    public int getF() {
        return g+h;
    }
    
    // needed for Comparable interface
    public int compareTo(Tile t) {
        return getF()-t.getF();
    }
    
}
