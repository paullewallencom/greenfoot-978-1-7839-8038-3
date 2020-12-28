import greenfoot.*;
import java.awt.Color;

public class TextBox extends Actor {
    private GreenfootImage img;
    private boolean border = false;
    private int fontSize;
    private Color foreground;
    private Color background;

    public TextBox(String s, int fs, boolean b, Color fg, Color bg) {
        super();
        fontSize = fs;
        foreground = fg;
        background = bg;
        img = new GreenfootImage(s, fontSize, foreground, background);
        border = b;
        display();
    }  

    public void setText(String s) {
        img = new GreenfootImage(s, fontSize, foreground, background);
        display();
    }

    private void display() {
        setImage(img);
        if( border ) {
            img.setColor(Color.BLACK);
            img.drawRect(0, 0, img.getWidth()-1, img.getHeight()-1);
            setImage(img);
        }
    }
}
