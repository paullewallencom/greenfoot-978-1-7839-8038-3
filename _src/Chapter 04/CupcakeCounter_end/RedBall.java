import greenfoot.*; 

public class RedBall extends Particles {
    public RedBall(int tr, int s, int l, int scaleX, int scaleY) {
        super(tr, s, l);
        getImage().scale(scaleX, scaleY);
    }
}
