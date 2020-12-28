/**
 * <p>An immutable class that describes a returned axis direction from the joypad.
 * This includes the direction and it's strength.</p>
 * 
 * <p>The user should never need to create these themselves, instead they will be
 * created and returned for you by the GamePad class.</p>
 * 
 * @author Joseph Lenton
 */
public class Direction  
{
    private final int angle;
    private final float strength;

    /**
     * <p>Creates a new direction. These are typically created for you by the GamePad
     * class.</p>
     * 
     * @param The angle for this direction in degrees.
     * @param How strong the direction is moving in the given angle.
     */
    public Direction(int angle, float strength)
    {
        // ensure: 0 <= angle <= 360
        while ( angle < 0 ) {
            angle += 360;
        }
        while ( angle > 360 ) {
            angle -= 360;
        }
        
        this.angle = angle;
        this.strength = strength;
    }
    
    /**
     * <p>Strength represents how far the axis is pushed in the given direction.
     * 0 is that it is not being pushed at all, and 1 is that it is fully exerted
     * as far as it can move. 0.5 would be moving the axis half way of the given
     * direction.</p>
     * 
     * @return The strength of the direction from 0 to 1.
     */
    public float getStrength()
    {
        return strength;
    }
    
    /**
     * <p>Angles are in degrees from 0 to 359, with 0 pointing to the east.</p>
     * 
     * @return The angle that this is pointing in.
     */
    public int getAngle()
    {
        return angle;
    }
    
    /**
     * <p>Returns the same value as the getAngle method,
     * however this is in radians instead of degrees.</p>
     * 
     * <p>This is useful for when you are using the
     * static trigonomic methods in the Math class, like
     * Math.cos() and Math.sin().</p>
     * 
     * @return The angle this is pointing in, in radians.
     */
    public double getAngleRadians()
    {
        return Math.toRadians( getAngle() );
    }
}
