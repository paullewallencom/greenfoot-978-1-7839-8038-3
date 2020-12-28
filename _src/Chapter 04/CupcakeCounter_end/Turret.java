import greenfoot.*; 
import java.awt.Color;

public class Turret extends Actor {
	private GreenfootImage turret;
	private GreenfootImage gun;
	private GreenfootImage img;

	public Turret() {
		turret = new GreenfootImage(30,30);
		turret.setColor(Color.black);
		turret.fillOval(0,0,30,30);

		gun = new GreenfootImage(40,40);
		gun.setColor(Color.black);
		gun.fillRect(0,0,10,35);

		img = new GreenfootImage(60,60);
		img.drawImage(turret, 15, 15);
		img.drawImage(gun, 25, 30);
		img.rotate(0);

		setImage(img);
	}

    public void act() {
    }    
}
