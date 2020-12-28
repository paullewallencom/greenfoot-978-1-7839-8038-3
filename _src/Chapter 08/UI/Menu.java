import greenfoot.*; 
import java.awt.Color;

public class Menu extends Actor
{
    private TextBox titleBar;
    private TextBox menuItems;
    private MenuCommands menuCommands;
    private int fontSize = 24;
    private boolean visible = false;
    private Color mainFG;
    private Color mainBG;
    private Color secondFG;
    private Color secondBG;
    int th, mh;

    public Menu(String tb, String i, int fs, 
            Color fg1, Color bg1, 
            Color fg2, Color bg2, 
            MenuCommands mc) {
        mainFG = fg1;
        mainBG = bg1;
        secondFG = fg2;
        secondBG = bg2;
        titleBar = new TextBox(tb, fs, true, mainFG, mainBG);
        menuItems = new TextBox(i, fs, true, secondFG, secondBG);
        menuCommands = mc;
        fontSize = fs;
    }

    public Menu() {
        this("not initialized", "none", 24, 
                Color.BLACK, Color.lightGray, Color.BLACK, 
                Color.WHITE, null);
    }

    protected void addedToWorld(World w) {
        w.addObject(titleBar, getX(), getY());
        th = titleBar.getImage().getHeight();
        mh = menuItems.getImage().getHeight();
    }

    public void act() {
        handleMouse();
    }    

    private void handleMouse() {
        if( Greenfoot.mouseClicked(titleBar) ) {
            if( !visible ) {
                getWorld().addObject(menuItems, getX(), getY()+(th+mh)/2);
            } else {
                getWorld().removeObject(menuItems);
            }
            visible = !visible;
        }

        if( Greenfoot.mouseClicked(menuItems)) {
            MouseInfo mi = Greenfoot.getMouseInfo();
            int menuIndex = ((mi.getY()-menuItems.getY()+mh/2)-1)/fontSize;
            menuCommands.execute(menuIndex, getWorld());
            visible = !visible;
            getWorld().removeObject(menuItems);
        }
    }
}

