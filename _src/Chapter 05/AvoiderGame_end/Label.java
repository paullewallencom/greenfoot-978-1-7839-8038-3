import greenfoot.*;  
import java.awt.Color;

public class Label extends Actor
{
	GreenfootImage msg;

	public Label(String s, int size) {
		this(s, size, Color.white);
	}  
	
	public Label(String s, int size, Color c) {
		msg = new GreenfootImage(s, size, c, null);
		setImage(msg);
	}  
}
