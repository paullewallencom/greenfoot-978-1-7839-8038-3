package gamepadlib;

public class PollData
{
    private final int component;
    private final boolean isPositive;
    private final float force;
    
    public PollData( int component, float force )
    {
        this.component = component;
        this.force = force;
        this.isPositive = force >= 0.0f;
    }
    
    public float getForce()
    {
        return this.force;
    }
    
    public int getComponent()
    {
        return this.component;
    }
    
    public boolean isPositive()
    {
        return this.isPositive;
    }
    
    public boolean equals( PollData other )
    {
        return other != null && other.getComponent() == getComponent() && other.isPositive() == isPositive();
    }
}