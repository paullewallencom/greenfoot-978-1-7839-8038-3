import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

public class UIMainWorld extends World {

    public UIMainWorld() {    
        super(600, 400, 1); 
        testActors();
    }
    
    private void testActors() {
        TextBox t1 = new TextBox(" This is a question? \n Yes, it is! ", 24, true, Color.BLUE, Color.YELLOW);
        addObject(t1, 150, 50);
        TextBox t2 = new TextBox("This is one line", 18, false, Color.BLACK, Color.WHITE);
        addObject(t2, 150, 120);
        Button b1 = new Button("button-blue.png", "button-green.png");
        addObject(b1, 450, 50);
        Menu m1 = new Menu(" Destroy Everything? ", "Are you sure?", 18, 
                                Color.BLUE, Color.WHITE, 
                                Color.BLACK, Color.WHITE, new DestroyCommands());
        addObject(m1, 450, 120);
        Menu m2 = new Menu(" File ", "New\nOpen\nSave\nClose\nExit", 18,  
                                Color.BLACK, Color.lightGray, 
                                Color.WHITE, Color.BLUE, new FileCommands());
        addObject(m2, 450, 180);
        HUD h = new HUD();
        addObject(h, 300, 310);
        Label l = new Label("This is a label", 18);
        addObject(l, 150, 180);
    }
}
