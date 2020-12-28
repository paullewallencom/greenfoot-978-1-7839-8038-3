import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class HUD extends Actor {
    private TransparentRectangle home;
    private TransparentRectangle favorite;
    private TransparentRectangle print;
    private TransparentRectangle cart;
    private static final int W = 70;
    private static final int H = 70;
    
    protected void addedToWorld(World w) {
        home = new TransparentRectangle(W,H);
        w.addObject(home, getX()-getImage().getWidth()/2+W/2, getY());
        favorite = new TransparentRectangle(W,H);
        w.addObject(favorite, getX()-W+20, getY());
        print = new TransparentRectangle(W,H);
        w.addObject(print, getX()+W-10, getY());
        cart = new TransparentRectangle(W,H);
        w.addObject(cart, getX()+getImage().getWidth()/2-W/2, getY());        
    }
    
    private class TransparentRectangle extends Actor {
        public TransparentRectangle(int w, int h) {
            GreenfootImage img = new GreenfootImage(w,h);
            setImage(img);
        }
    }
    
    public void act() {
        handleMouseClicks();
    }  
    
    private void handleMouseClicks() {
        if( Greenfoot.mouseClicked(home) ) {
            System.out.println("Clicked Home");
        }
        if( Greenfoot.mouseClicked(favorite) ) {
            System.out.println("Clicked Favorite");
        }
        if( Greenfoot.mouseClicked(print) ) {
            System.out.println("Clicked Print");
        }
        if( Greenfoot.mouseClicked(cart) ) {
            System.out.println("Clicked Cart");
        }
    }
}
