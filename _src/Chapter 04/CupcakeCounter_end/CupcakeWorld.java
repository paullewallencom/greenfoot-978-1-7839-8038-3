import greenfoot.*; 
import java.util.List;

public class CupcakeWorld extends World {
    private Counter score;
    private Turret turret;
    public int BCOUNT = 200;
    private int ballCounter = BCOUNT;
    public int FCOUNT = 400;
    private int fountainCounter = FCOUNT;
    private int level = 0;

    public CupcakeWorld() {
        super(600, 400, 1, false); 
        setPaintOrder(Counter.class, Turret.class, Fountain.class, 
            Jumper.class, Enemy.class, Reward.class, Platform.class); 
        prepare();
    }

    public void act() {
        checkLevel();
    }

    private void checkLevel() {
        if( level > 1 ) generateBalls();
        if( level > 4 ) generateFountains();
        if( level % 3 == 0 ) {
            FCOUNT--;
            BCOUNT--;
            fountainCounter = Math.max(fountainCounter-50, 0);
            ballCounter = Math.max(ballCounter-50, 0);
            level++;
        }
        if( level < score.getValue()) ++level;	
    }

    private void generateFountains() {
        fountainCounter--;
        if( fountainCounter < 0 ) {
            List<Brick> bricks = getObjects(Brick.class);
            int idx = Greenfoot.getRandomNumber(bricks.size());
            Fountain f = new Fountain();
            int top = f.getImage().getHeight()/2 + bricks.get(idx).getImage().getHeight()/2;
            addObject(f, bricks.get(idx).getX(), bricks.get(idx).getY()-top);
            fountainCounter = FCOUNT;
        }
    }

    private void generateBalls() {
        ballCounter--;
        if( ballCounter < 0 ) {
            Ball b = new Ball();
            turret.setRotation(15 * -b.getXVelocity());
            addObject(b, getWidth()/2, 0);
            ballCounter = BCOUNT;
        }
    }

    public void addCupcakeCount(int num) {
        score.setValue(score.getValue() + num);
        generateNewCupcake();
    }

    private void generateNewCupcake() {
        List<Brick> bricks = getObjects(Brick.class);
        int idx = Greenfoot.getRandomNumber(bricks.size());
        Cupcake cake = new Cupcake();
        int top = cake.getImage().getHeight()/2 + bricks.get(idx).getImage().getHeight()/2;
        addObject(cake, bricks.get(idx).getX(), bricks.get(idx).getY()-top);
    }

    public void addObjectNudge(Actor a, int x, int y) {
        int nudge = Greenfoot.getRandomNumber(8) - 4;
        super.addObject(a, x + nudge, y + nudge);
    }

    private void prepare()
    {
        // Add Bob
        Bob bob = new Bob();
        addObject(bob, 43, 340);
        // Add floor
        BrickWall brickwall = new BrickWall();
        addObject(brickwall, 184, 400);
        BrickWall brickwall2 = new BrickWall();
        addObject(brickwall2, 567, 400);
        // Add Score
        score = new Counter();
        addObject(score, 62, 27);
        // Add turret
        turret = new Turret();
        addObject(turret, getWidth()/2, 0);
        // Add cupcake
        Cupcake cupcake = new Cupcake();
        addObject(cupcake, 450, 30);
        // Add platforms
        for(int i=0; i<5; i++) {
            for(int j=0; j<6; j++) {
                int stagger = (i % 2 == 0 ) ? 24 : -24;
                Brick brick = new Brick();
                addObjectNudge(brick, stagger + (j+1)*85, (i+1)*62);
            }
        }
    }
}
