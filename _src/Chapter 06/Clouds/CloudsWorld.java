import greenfoot.*; 

public class CloudsWorld extends World {

    public CloudsWorld() {    
        super(600, 400, 1, false); 
        prepare();
    }

    public void act() {
        generateBackgroundClouds();
        generateWalls();
    }

    private void generateBackgroundClouds() {
        generateActor(5, new Cloud1());
        generateActor(4, new Cloud2());
        generateActor(3, new Cloud3());
    }
    
    private void generateWalls() {
        generateActor(5, new Wall());
    }
    
    private void generateActor(int chance, Actor a) {
        if( Greenfoot.getRandomNumber(1000) < chance) {
            int randY = Greenfoot.getRandomNumber(300) + 50;
            addObject(a, getWidth()+20, randY);
        }
    }

    private void prepare(){
        Rocket rocket = new Rocket();
        addObject(rocket, 90, 200);
    }
}
