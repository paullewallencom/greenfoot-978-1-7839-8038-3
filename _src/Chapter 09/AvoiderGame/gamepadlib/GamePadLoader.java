
package gamepadlib;

import net.java.games.input.*;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import java.util.List;
import java.util.ArrayList;

import java.io.File;

public class GamePadLoader
{
    public static void initialize()
    {
        // I'm calling this to just 'touch' J-Input.
        // That's so it does it's stuff and loads the native libs we need.
        ControllerEnvironment.getDefaultEnvironment().getControllers();
    }
    
    /**
     * Analog devices typically never give 0 when they are not in use, so the dead zone
     * refers to the range of values around 0 which should be ignored.
     * If a value is greater then negative DEAD_ZONE and less then positive DEAD_ZONE,
     * then the controller should be presumed to be in a state of rest.
     */
    /* The components themselves offer their own dead zone values, but in my testing these
     * these typically were not correct. */
    private static final float DEAD_ZONE = 0.15f;
    
    // this is to ensure buttons have to be fully pressed to be detected
    private static final float POLL_DEAD_ZONE = 0.75f;
    
    /**
     * @return True if the value given is considered to be too small to be noticed, and should be ignored.
     */
    public static boolean isDead(final float val)
    {
        return Math.abs(val) < DEAD_ZONE;
    }
    
    private static boolean fltEqual(final float a, final float b, final float error)
    {
        return (a-error) < b && (a+error) > b;
    }
    
    /**
     * Methods that return an angle often also need to return that there is no angle at all.
     * When they do this they will return this value.
     */
    public static final int NO_ANGLE = -1;
    
    /**
     * The number of constants within the Axis enum.
     */
    private static final int NUM_AXIS = 3;
    
    public static final int NO_COMP = -1;
    
    /**
     * If no game pad is plugged in then this will throw an exception.
     * This will always return the same first GamePad it has found.
     * @return The first GamePad found.
     * @throws IllegalStateException if there is no real game pad found.
     */
    public static GamePadLoader getGamePad()
    {
        return getGamePad( 0 );
    }
    
    /**
     * The index given should be from 0 to the number of game pads on this
     * machine, exclusive.
     * @param index The index of the GamePad to find.
     * @return The GamePad stored against the given index.
     */
    public static GamePadLoader getGamePad(int index)
    {
        final GamePadLoader[] pads = getGamePads();
        
        if ( pads.length == 0 ) {
            throw new IllegalArgumentException( "No gamepad found, are you sure it's plugged in?" );
        } else if ( index < 0 ) {
            throw new IllegalArgumentException( "Index must be 0 or greater, was given: " + index );
        } else if ( pads.length <= index ) {
            throw new IllegalArgumentException( "Pad not found for given index: " + index );
        } else {
            return pads[ index ];
        }
    }
    
    /**
     * @return The number of game pads available on this PC, 0 for no pads.
     */
    public static int getNumGamePads()
    {
        return getGamePads().length;
    }
    
    /**
     * If no pads are found then an empty array is returned.
     * @return An array containing all of the GamePads found connected to this machine.
     */
    public static GamePadLoader[] getGamePads()
    {
        final List<GamePadLoader> pads = new ArrayList<GamePadLoader>();
        
        final ControllerEnvironment ce = ControllerEnvironment.getDefaultEnvironment();
        final Controller[] controllers = ce.getControllers();
        for ( Controller controller : controllers ) {
            if ( isControllerGamePad(controller) ) {
                pads.add( new GamePadLoader(controller) );
            }
        }
        
        final GamePadLoader[] gamePads = new GamePadLoader[ pads.size() ];
        pads.toArray( gamePads );
        
        return gamePads;
    }
    
    /**
     * @return True if the given controller is a stick or gamepad.
     */
    private static boolean isControllerGamePad(Controller controller)
    {
        final Controller.Type type = controller.getType();
        return ( type == Controller.Type.GAMEPAD ) || ( type == Controller.Type.STICK );
    }
    
    private static Component[] filterComponents( Component[] components )
    {
        final Component[] temp = new Component[ components.length ];
        
        // only keep components we want
        int numComps = 0;
        for ( int i = 0; i < components.length; i++ ) {
            final Component c = components[i];
            
            if ( isComponent(c) ) {
                temp[numComps++] = c;
            }
        }
        
        // compact the array down
        final Component[] returnComps = new Component[ numComps ];
        System.arraycopy( temp, 0, returnComps, 0, numComps );
        return returnComps;
    }
    
    private static Component findAxis( Component[] components, Component.Identifier.Axis axis )
    {
        for ( Component c : components ) {
            if ( c.getIdentifier() == axis ) {
                return c;
            }
        }
        
        return null;
    }
    
    private static boolean isComponent( final Component component )
    {
        return isButton(component) || isAxis(component);
    }
    
    private static boolean isButton( final Component component )
    {
        return isComponentNameEndsWith( component, "button" );
    }
    
    private static boolean isAxis( final Component component )
    {
        return isComponentNameEndsWith( component, "axis" ) || isComponentNameEndsWith( component, "rotation" );
    }
    
    private static boolean isComponentNameEndsWith( final Component component, final String endsName )
    {
        final String name = component.getName().toLowerCase();
        return name.startsWith( endsName ) || name.endsWith( endsName );
    }
    
    private final Controller controller;
    private final Component[] components;
    private final Component povAxis;
    
    /**
     * Creates a new GamePad listening to the first real game pad it can find.
     * @throws IllegalStateException if there is no real game pad found.
     */
    private GamePadLoader(Controller controller)
    {
        if ( controller == null ) {
            throw new IllegalStateException("Controller cannot be null.");
        }
        
        this.controller = controller;
        final Component[] comps = controller.getComponents();
        this.components = GamePadLoader.filterComponents( comps );
        
        this.povAxis = GamePadLoader.findAxis( comps, Component.Identifier.Axis.POV );
    }
    
    public String getName()
    {
        return controller.getName();
    }
    
    public boolean isSupported( int index )
    {
        return index >= 0 && index < components.length;
    }
    
    public boolean isPOVSupported()
    {
        return povAxis != null;
    }
    
    private Component getComponent( int index )
    {
        if ( isSupported(index) ) {
            return components[index];
        } else {
            return null;
        }
    }
    
    public float poll( int index )
    {
        final Component c = getComponent( index );
        
        if ( c != null ) {
            controller.poll();
            final float val = c.getPollData();
            
            if ( !GamePadLoader.isDead(val) ) {
                return val;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }
    
    public float pollPov()
    {
        if ( povAxis != null ) {
            controller.poll();
            return povAxis.getPollData();
        } else {
            return 0;
        }
    }
    
    /**
     * Checks all buttons and returns the first one that is currently pressed.
     * 
     * @return The first button that is pressed, or null if no button.
     */
    public PollData pollDown()
    {
        controller.poll();
        
        float highestPoll = 0;
        int comp = -1;
        
        for ( int i = 0; i < components.length; i++ ) {
            final float poll = components[i].getPollData();
            
            if ( Math.abs(poll) > Math.abs(highestPoll) ) {
                highestPoll = poll;
                comp = i;
            }
        }
        
        if ( Math.abs(highestPoll) > POLL_DEAD_ZONE ) {
            return new PollData( comp, highestPoll );
        } else {
            return null;
        }
    }
}