import greenfoot.*; 
import java.awt.Color;
import java.util.List;

public class Eye extends Actor {
    
    public Eye() {
        drawEye(2,2);
    }
    
    public void act() {
        lookAtEnemies();
    }    
    
    public void lookAtEnemies() {
        List<Enemy> eList = getObjectsInRange(120, Enemy.class);
        if( !eList.isEmpty() ) {
            Enemy e = eList.get(0);
            if( e.getX() < getX() ) {
                if( e.getY() < getY() ) {
                    drawEye(1,1);
                } else {
                    drawEye(1,3);
                }                
            } else {
                if( e.getY() < getY() ) {
                    drawEye(3,1);
                } else {
                    drawEye(3,3);
                }                                
            }
        }
    }
    
    private void drawEye(int dx, int dy) {
        GreenfootImage img = new GreenfootImage(10,10);
        img.setColor(Color.white);
        img.fillOval(0,0,10,10);
        img.setColor(Color.black);
        img.fillOval(dx,dy,6,6);
        setImage(img);        
    }
}
