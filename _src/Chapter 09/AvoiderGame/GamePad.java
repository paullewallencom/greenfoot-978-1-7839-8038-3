import bluej.Config;

import gamepadlib.PollData;
import gamepadlib.GamePadLoader;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.net.URL;
import java.net.URLClassLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.IOException;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Map;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

/**
 * <p>A high-level wrapper to a controller. This is a singleton class that returns all
 * of the controllers found connected to this machine.</p>
 * 
 * <p>Note that this is designed to work with DualShock Playstation-like controllers (but without
 * rumble).</p>
 * 
 * <p>These are controllers with 4 action buttons, 6 L and R buttons (3 for left and 3 for right),
 * 2 middle buttons (select and start), a d-pad (the direction buttons) and 2 analogue sticks.</p>
 * 
 * @author Joseph Lenton
 */
public class GamePad
{
    private static void loadLib( File klassFile, ClassLoader cl, Method defineClass )
            throws Exception
    {
        if ( ! klassFile.isFile() ) {
            throw new IllegalArgumentException("Given klassFile must be a file");
        }
        
        // load the class from disk and convert to a byte array
        final byte[] klassBytes = new byte[ (int)klassFile.length() ];
        
        final FileInputStream input = new FileInputStream( klassFile );
        int offset = 0;
        int numRead = 0;
        while (
                offset < klassBytes.length &&
                ( numRead = input.read(klassBytes, offset, klassBytes.length-offset) ) >= 0
        ) {
            offset += numRead;
        }
        input.close();
        
        defineClass.invoke( cl, null, klassBytes, 0, klassBytes.length );
    }
    
    static {
        // find the top parent class loader
        ClassLoader parentCL = null;
        for ( ClassLoader cl = GamePad.class.getClassLoader(); cl != null; cl = cl.getParent() ) {
            parentCL = cl;
        }
        
        // This try is essentially an if. It's either already loaded (and so we do nothing),
        // or it's not loaded in which case exception is thrown and we load and initialize
        // the class.
        try {
            parentCL.loadClass( "gamepadlib.GamePadLoader" );
        } catch ( ClassNotFoundException unusedEx ) {
            try {
                /* First the 'gamepadlib' folder must be added to the parent class loader so
                 * it can find the 'net' folder containing the JInput files.
                 * 
                 * Note that this is NOT for finding all of the GamePad Loader classes, their
                 * root classpath is still the project folder because of how they are structured!
                 * 
                 * You cannot add the project folder (.) as it causes Greenfoot to freeze on
                 * a recompile. So a sub-folder is used instead*/
                // ClassLoader hack as it's actually an URLClassLoader
                final URLClassLoader cl = (URLClassLoader) parentCL;
                try {
                    Method method = URLClassLoader.class.getDeclaredMethod(
                            "addURL",
                            new Class[] { URL.class }
                    );
                    method.setAccessible( true );
                    //method.invoke( cl, new File(".\\gamepadlib").toURI().toURL() );
                    method.invoke( cl, new File("./gamepadlib").toURI().toURL() );
                } catch ( Exception ex ) {
                    throw new RuntimeException("Error, could not add URL to system classloader");
                }
                
                /* Then I find the defineClass method from the ClassLoader class so I can call it.
                 * I need to use reflection because it is protected.
                 * 
                 * I was unable to get passing in the required classes for getting the method
                 * directly, so instead I just manually cycle through them until a method
                 * 'that looks right' is found. */
                Method defineClass = null;
                for ( Method m : ClassLoader.class.getDeclaredMethods() ) {
                    if ( m.getName().equals("defineClass") && m.getParameterTypes().length == 4 ) {
                        defineClass = m;
                        break;
                    }
                }
                defineClass.setAccessible( true );
                
                /* Now I manually load all of the GamePad Loader classes in a safe order. This is
                 * because they cannot be seen from the class path, and I need to load them in order
                 * to pre-load JInput in the top class loader. */
                //loadLib( new File("gamepadlib\\PollData.class"), parentCL, defineClass ); // must be loaded before GamePadLoader
                loadLib( new File("gamepadlib/PollData.class"), parentCL, defineClass ); // must be loaded before GamePadLoader
                //loadLib( new File("gamepadlib\\GamePadLoader.class"), parentCL, defineClass );
                loadLib( new File("gamepadlib/GamePadLoader.class"), parentCL, defineClass );
                
                // now grab the class and initialize!
                parentCL.loadClass( "gamepadlib.GamePadLoader" ).getMethod( "initialize" ).invoke( null );
            } catch ( IOException ex ) {
                throw new RuntimeException( "Error reading the GamePadLoader", ex );
            } catch ( Exception ex ) {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * The number of constants within the Axis enum.
     */
    private static final int NUM_AXIS = 3;
    
    private static final float IS_DOWN = 0.5f;
    
    /**
     * Name of the configuration file to store gamepad info in.
     */
    private static final String CONFIG_NAME = ".gamepads.conf";
    // lib dir is not the root of the greenfoot dir, so we move two sections up to get to there
    private static final String CONFIG_PATH = Config.getGreenfootLibDir().getAbsolutePath() + "/../../" + CONFIG_NAME;
    
    /*
     * Mapping stuff.
     */
    
    private static final String DEFAULT_MAPPING = "Generic   USB  Joystick";
    private static final Map<String, GamePadMappings> mappings = new HashMap<String, GamePadMappings>();
    static {
        // suggested defaults
        mappings.put(
                "XBOX 360 For Windows (Controller)",
                new GamePadMappings(
                        new int[] { 0, 1, 2, 3, 8, 6, 5, 7, 9, 10, 4, 4, 11, 12, 13, 14 },
                        new boolean[] { true, true, true, true, true, true,
                                        true, true, true, true, true, false,
                                        true, true, true, true }
                )
        );
        mappings.put(
                DEFAULT_MAPPING,
                new GamePadMappings(
                        new int[] { 3, 4, 0, 1, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 },
                        new boolean[] { true, true, true, true, true, true,
                                        true, true, true, true, true, true,
                                        true, true, true, true }
                )
        );
        
        // configs, these are free to override defaults
        mappings.putAll( GamePadMappings.load( CONFIG_PATH ) );
    }
    
    // each axis uses 2 components, except the POV which uses none (uses a hat instead).
    private static int NUM_AXIS_COMPS = (Axis.values().length-1)*2;
    private static int NUM_BUTTON_COMPS = Button.values().length;
    private static int NUM_COMPS = NUM_AXIS_COMPS + NUM_BUTTON_COMPS;
    
    private static GamePad[] gamePads = null;
    
    /**
     * <p>The different types of axis on the joypad.</p>
     * 
     * <p>You can use these to state which axis you are finding the direction for.</p>
     */
    public enum Axis
    {
        DPAD(),
        LEFT( 1, 0 ),
        RIGHT( 3, 2 );
        
        private final int horizontal;
        private final int vertical;
        private boolean isPOV;
        
        private Axis()
        {
            this( -1, -1 );
            this.isPOV = true;
        }
        
        private Axis( int horizontal, int vertical )
        {
            this.horizontal = horizontal;
            this.vertical = vertical;
            this.isPOV = false;
        }
        
        private boolean isPOV()
        {
            return isPOV;
        }
        
        private int getHorizontal()
        {
            return this.horizontal;
        }
        
        private int getVertical()
        {
            return this.vertical;
        }
    }
    
    /**
     * <p>An enum of all of the different buttons most commonly supported.</p>
     */
    public enum Button
    {
        ACTION_UP(),
        ACTION_RIGHT(),
        ACTION_DOWN(),
        ACTION_LEFT(),
        L1(),
        R1(),
        L2(),
        R2(),
        SELECT(),
        START(),
        L3(),
        R3();
        
        private int index;
        
        private Button()
        {
            this.index = -1;
        }
        
        private int getIndex()
        {
            return index;
        }
        
        static {
            final Button[] buttons = Button.values();
            for ( int i = 0; i < buttons.length; i++ ) {
                final Button button = buttons[i];
                button.index = i + NUM_AXIS_COMPS;
            }
        }
    }
    
    /**
     * <p>This returns the first GamePad that this library finds connected to your PC.
     * If there is only one GamePad connected, then that is the one that is returned.</p>
     * 
     * <p>If no game pad is plugged in then this will throw an exception.
     * This will always return the same first GamePad it has found.</p>
     * 
     * @return The first GamePad found.
     * @throws IllegalStateException if there is no real game pad found.
     */
    public static GamePad getGamePad()
    {
        return getGamePad( 0 );
    }
    
    /**
     * <p>For grabbing a specific gamepad that is connected to your PC.</p>
     * 
     * <p>The index given should be from 0 to the number of game pads on this
     * machine, exclusive.</p>
     * 
     * @param index The index of the GamePad to find.
     * @return The GamePad stored against the given index.
     */
    public static GamePad getGamePad(int index)
    {
        final GamePad[] pads = getGamePads();
        
        if ( pads.length == 0 ) {
            throw new IllegalArgumentException( "No gamepad found, are you sure it's plugged in?" );
        } else if ( index < 0 ) {
            throw new IllegalArgumentException( "Index must be 0 or greater, was given: " + index );
        } else if ( pads.length <= index ) {
            throw new IllegalArgumentException( "Pad not found for given index: " + index );
        } else {
            return pads[index];
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
     * <p>Returns all of the GamePads that this library can find connected to your PC.</p>
     * 
     * <p>If no pads are found then an empty array is returned.</p>
     * 
     * @return An array containing all of the GamePads found connected to this machine.
     */
    public static GamePad[] getGamePads()
    {
        // if no pads, always refresh!
        if ( gamePads == null || gamePads.length == 0 ) {
            refreshGamePads();
        }
        
        return gamePads;
    }
    
    private static void setMapping( GamePad pad )
    {
        if ( pad == null ) {
            throw new IllegalStateException("pad should never be null.");
        }
        
        final String name = pad.getName();
        final GamePadMappings mapping = GamePad.mappings.get( name );
        
        if ( mapping != null ) {
            pad.setMapping( mapping );
        } else { // default
            pad.setMapping( GamePad.mappings.get(DEFAULT_MAPPING) );
        }
    }
    
    /**
     * <p>Rescans the PC for all GamePads connected. Calling getGamePads()
     * after this will return the latest number of pads connected to the
     * machine.</p>
     */
    public static void refreshGamePads()
    {
        final GamePadLoader[] gPadLoader = GamePadLoader.getGamePads();
        gamePads = new GamePad[ gPadLoader.length ];
        
        for ( int i = 0; i < gPadLoader.length; i++ ) {
            final GamePad gamePad = new GamePad( gPadLoader[i] );
            GamePad.setMapping( gamePad );
            gamePads[ i ] = gamePad;
        }
    }
    
    private static void save()
    {
        if ( gamePads != null ) {
            // update mappings with latest mappings
            for ( GamePad pad : gamePads ) {
                mappings.put( pad.getName(), pad.getGamePadMappings() );
            }
            
            GamePadMappings.save( CONFIG_PATH, mappings );
        }
    }
        
    private final GamePadLoader inner;
    private final int[] map;
    private final boolean[] buttonMap;
    private final Map< Axis, Direction > lastDirections;
    
    /**
     * <p>Creates a new GamePad listening to the first real game pad it can find.</p>
     * 
     * @throws IllegalStateException if there is no real game pad found.
     */
    private GamePad(GamePadLoader inner )
    {
        if ( inner == null ) {
            throw new IllegalStateException("Inner GamePadLoader cannot be null.");
        }
        
        this.inner = inner;
        
        this.map = new int[ NUM_COMPS ];
        this.buttonMap = new boolean[ NUM_COMPS ];
        this.lastDirections = new EnumMap< Axis, Direction >( Axis.class );
        
        for ( GamePad.Axis axis : GamePad.Axis.values() ) {
            if ( ! axis.isPOV() ) {
                final int hIndex = axis.getHorizontal();
                final int vIndex = axis.getVertical();
                
                map[ hIndex ] = hIndex;
                map[ vIndex ] = vIndex;
            }
            
            // populate with default directions
            this.lastDirections.put( axis, new Direction(0, 0) );
        }
        
        for ( GamePad.Button button : GamePad.Button.values() ) {
            final int index = button.getIndex();
            map[ index ] = index;
        }
        
        for ( int i = 0; i < buttonMap.length; i++ ) {
            buttonMap[i] = true;
        }
    }
    
    private String getName()
    {
        return inner.getName().trim();
    }
    
    private GamePadMappings getGamePadMappings()
    {
        return new GamePadMappings( this.map, this.buttonMap );
    }
    
    private void setMapping( GamePadMappings mappings )
    {
        mappings.set( this.map, this.buttonMap );
    }
    
    /**
     * <p>Checks if the given button is supported on your gamepad,
     * and so will work when pressed, or not.</p>
     * 
     * @return True if the given button enum is supported by this controller.
     */
    public boolean isButtonSupported(Button button)
    {
        if ( button == null ) {
            throw new IllegalArgumentException("The given button cannot be null.");
        }
        
        return inner.isSupported( map[button.getIndex()] );
    }
    
    /**
     * <p>Checks if the button given is pressed down or not.
     * If the button is not supported then this will always
     * fail silently and return false.</p>
     * 
     * @param button The button to check.
     * @return True if the button with the given index is pressed, otherwise false.
     */
    public boolean isDown(Button button)
    {
        if ( button == null ) {
            throw new IllegalArgumentException("The given button cannot be null.");
        }
        
        final int buttonI = button.getIndex();
        final float force = inner.poll( map[buttonI] );
        
        if ( buttonMap[buttonI] ) {
            return force > IS_DOWN;
        } else {
            return force < -IS_DOWN;
        }
    }
    
    /**
     * <p>Checks if the given axis is supported or not.</p>
     * 
     * @param The Axis object to check.
     * @return True if the given axis is available to be read on this GamePad.
     */
    public boolean isAxisSupported(Axis axis)
    {
        if ( axis == null ) {
            throw new IllegalArgumentException("The given axis cannot be null.");
        }
        
        if ( axis.isPOV() ) {
            return inner.isPOVSupported();
        } else {
            return inner.isSupported( map[axis.getHorizontal()] )
                && inner.isSupported( map[axis.getVertical()] )
            ;
        }
    }
    
    /**
     * <p>This returns a Direction describing the current angle and strength
     * of the Axis polled. This will return a Direction regardless of if
     * the Axis is in use or not.</p>
     * 
     * <p>Axis which are not supported still return a Direction. But it will
     * just never show any movement.</p>
     * 
     * <p>To tell if the Direction is or is not in use then you should check
     * the strength of the Direction.</p>
     * 
     * @param axis The Axis to poll.
     * @return The current Direction of the Axis polled.
     */
    public Direction getAxis(Axis axis)
    {
        if ( axis == null ) {
            throw new IllegalArgumentException("The given axis cannot be null.");
        }
        
        final Direction dir;
        
        if ( axis.isPOV() ) {
            dir = getPOVDirection();
        } else {
            dir = getAxisDirection(
                    map[axis.getHorizontal()],
                    map[axis.getVertical()]
            );
        }
        
        return checkDirection( axis, dir );
    }
    
    private Direction getAxisDirection( int h, int v )
    {
        float hMove = inner.poll( h );
        float vMove = inner.poll( v );
        
        final boolean hDead = GamePadLoader.isDead(hMove);
        final boolean vDead = GamePadLoader.isDead(vMove);
        
        if ( hDead && vDead ) {
            return null;
        } else {
            if ( hDead ) { hMove = 0f; }
            if ( vDead ) { vMove = 0f; }
            
            final int angle = (int) Math.toDegrees( Math.atan2(vMove, hMove) );
            
            // If in full strength in a direction of 45 degrees, then in reality
            // the strengths should be (approximately) 0.8. On the joypad they are read as 1 (full).
            // This multiplication fixes this issue, moving from 1 to the maximum of the cos and sin value
            // for the angle it is pointed in.
            hMove *= Math.cos( angle );
            vMove *= Math.sin( angle );
            
            // The strength is now the length of the hypotenuse from the origin to where the stick is.
            final float strength = (float) Math.sqrt( hMove*hMove + vMove*vMove );
            
            return new Direction( angle, strength );
        }
    }
    
    private Direction checkDirection( Axis axis, Direction dir )
    {
        if ( dir == null ) {
            return lastDirections.get( axis );
        } else {
            final Direction noForce = new Direction( dir.getAngle(), 0f );
            lastDirections.put( axis, noForce );
            return dir;
        }
    }
    
    private Direction getPOVDirection()
    {
        final float pov = inner.pollPov();
        
        if ( GamePadLoader.isDead(pov) ) {
            return null;
        } else {
            final int angle = (int) ( ((pov+0.5f) % 1f) * 360f );
            return new Direction( angle, 1f );
        }
    }
    
    /**
     * <p>Runs and shows a GamePad configuration GUI for setting gamepad mappings.</p>
     * 
     * <p>This is what you can use for allowing users to reconfigure their
     * gamepad within your scenario.</p>
     */
    public void runConfigurePad()
    {
        final GamePadConfig config = new GamePadConfig();
        config.runConfigurePad();
    }
    
    /**
     * The internal implementation of this GamePad's remapping object.
     */
    private class GamePadConfig
    {
        private GamePadConfig()
        {
        }
        
        /**
         * <p>Runs a GamePad configuration GUI for setting gamepad mappings.</p>
         * 
         * <p>This is what you can use for allowing users to reconfigure their
         * gamepad within your scenario.</p>
         */
        public void runConfigurePad()
        {
            final int result = JOptionPane.showOptionDialog( null,
                    "Do you want to redefine your GamePad's controls?",
                    "Redefine controls?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    null,
                    null
            );
            
            if ( result == JOptionPane.YES_OPTION ) {
                final ButtonCoordinator coord = new ButtonCoordinator();
                final GamePadInputDialog dialog = new GamePadInputDialog( coord );
                final PollingThread thread = new PollingThread( coord, this, dialog );
                thread.start();
                
                thread.setAxis( false );
                for ( GamePad.Button button : GamePad.Button.values() ) {
                    dialog.setButton( button );
                    coord.clear();
                    dialog.setVisible( true );
                    
                    // setVisible blocks, so this point is only reached when dialog is no longer visible!
                    
                    if ( coord.remap != null ) {
                        map( button, coord.remap.getComponent(), coord.remap.isPositive() );
                    }
                }
                
                thread.setAxis( true );
                for ( GamePad.Axis axis : GamePad.Axis.values() ) {
                    if ( axis != GamePad.Axis.DPAD ) {
                        displayAxis( dialog, coord, axis, true );
                        displayAxis( dialog, coord, axis, false );
                    }
                }
                
                thread.stopRunning();
                dialog.dispose();
            }
            
            GamePad.save();
        }
        
        public void map( Axis from, int comp, boolean isHorizontal )
        {
            if ( !from.isPOV() ) {
                final int index;
                
                if ( isHorizontal ) {
                    index = from.getHorizontal();
                } else {
                    index = from.getVertical();
                }
                
                map[ index ] = comp;
            }
        }
        
        public void map( Button from, int comp, boolean isPositive )
        {
            map[ from.getIndex() ] = comp;
            buttonMap[ from.getIndex() ] = isPositive;
        }
        
        public PollData pollRawDown()
        {
            return inner.pollDown();
        }
        
        private void displayAxis( GamePadInputDialog dialog, ButtonCoordinator coord, GamePad.Axis axis, boolean isHorizontal )
        {
            dialog.setAxis( axis, isHorizontal );
            coord.clear();
            dialog.setVisible( true );
            
            // setVisible blocks, so this point is only reached when dialog is no longer visible!
            if ( coord.remap != null ) {
                map( axis, coord.remap.getComponent(), isHorizontal );
            }
        }
    }
    
    private static class ButtonCoordinator
    {
        private boolean skip;
        private PollData remap;
        
        public ButtonCoordinator()
        {
            clear();
        }
        
        public final synchronized void clear()
        {
            skip = false;
            remap = null;
        }
        
        private final boolean hasTakenInput()
        {
            return skip != false || remap != null;
        }
        
        public synchronized void setSkip()
        {
            if ( !hasTakenInput() ) {
                skip = true;
            }
        }
        
        public synchronized void setPollData( PollData pollData )
        {
            if ( !hasTakenInput() ) {
                remap = pollData;
            }
        }
    }
    
    private static class PollingThread extends Thread
    {
        private final static int SLEEP_TIME = 5;
        
        private final GamePadConfig config;
        private final ButtonCoordinator coord;
        private final JDialog dialog;
        private final Map<Boolean, Set<Integer>> seenComponents;
        private boolean isRunning;
        private boolean isAxis;
        
        public PollingThread( ButtonCoordinator coord, GamePadConfig config, JDialog dialog )
        {
            this.config = config;
            this.dialog = dialog;
            this.isRunning = true;
            this.coord = coord;
            this.isAxis = false;
            
            this.seenComponents = new HashMap<Boolean, Set<Integer>>();
            this.seenComponents.put( true, new HashSet<Integer>() );
            this.seenComponents.put( false, new HashSet<Integer>() );
        }
        
        public void run()
        {
            while ( isRunning ) {
                final PollData poll = config.pollRawDown();
                
                if ( poll != null ) {
                    if (
                            ( isAxis && this.seenComponents.get( true ).add( poll.getComponent() ) )
                        || ( !isAxis && this.seenComponents.get( poll.isPositive() ).add( poll.getComponent() ) )
                    ) {
                        coord.setPollData( poll );
                        dialog.setVisible( false );
                    }
                }
                
                try {
                    Thread.sleep( SLEEP_TIME );
                } catch ( InterruptedException ex ) { }
            }
        }
        
        public void setAxis(boolean isAxis)
        {
            this.isAxis = isAxis;
        }
        
        public void stopRunning()
        {
            isRunning = false;
        }
    }
    
    private static class GamePadInputDialog extends JDialog
    {
        private final ButtonCoordinator coord;
        private final JLabel label;
        
        public GamePadInputDialog( ButtonCoordinator coord )
        {
            super( (JDialog)null, "Press button...", true );
            
            this.coord = coord;
            
            setLayout( new BorderLayout() );
            label = new JLabel("");
            label.setBorder(
                    BorderFactory.createEmptyBorder( 10, 10, 10, 10 )
            );
            
            final JButton skip = new JButton("Skip");
            skip.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    GamePadInputDialog.this.coord.setSkip();
                    setVisible( false );
                }
            } );
            
            add( label, BorderLayout.CENTER );
            add( skip, BorderLayout.SOUTH );
            
            setLocationByPlatform( true );
        }
        
        public void setButton( GamePad.Button button )
        {
            this.setLabel( "Press the " + button + " button." );
        }
        
        public void setAxis( GamePad.Axis axis, boolean isHorizontal )
        {
            final String direction;
            if ( isHorizontal ) {
                direction = "horizontally";
            } else {
                direction = "vertically";
            }
            
            this.setLabel( "Move the " + axis + " axis " + direction + "." );
        }
        
        private void setLabel( String text )
        {
            this.label.setText( text );
            pack();
        }
    }
    
    /*
     * <p>GamePad config files work on an alternating row system. First is a row with the name of
     * the pad, and then a row listing the mappings. The mappings are delimitered based on their
     * position. For example a row could be '3, 5, 2, 9' which is a mapping of 0 to 3, 1 to 5,
     * 2 to 2 and 3 to 9.<p>
     * 
     * <p>However after each number there is also a true or false to state if the value should be
     * detected in the positive or negative range (only on buttons). So the format should
     * actually be something like: '3, true, 5, true, 2, false, 9, true'</p>
     */
    private static class GamePadMappings
    {
        private static final Map<String, GamePadMappings> load( String path )
        {
            final Map<String, GamePadMappings> mappings = new HashMap<String, GamePadMappings>();
            final File file = new File( path );
            
            try {
                final BufferedReader reader = new BufferedReader( new FileReader( file ) );
                
                String name = null;
                while ( reader.ready() ) {
                    final String line = reader.readLine().trim();
                    if ( line.length() > 0 ) {
                        if ( name == null ) {
                            name = line;
                        } else {
                            final String[] parts = line.split(",");
                            final int[] map = new int[ parts.length/2 ];
                            final boolean[] buttonMap = new boolean[ parts.length/2 ];
                            
                            for ( int i = 0; i < parts.length/2; i++ ) {
                                final int lineI = i*2;
                                map[i] = Integer.parseInt( parts[lineI].trim() );
                                buttonMap[i] = Boolean.parseBoolean( parts[lineI+1].trim() );
                            }
                            
                            mappings.put( name, new GamePadMappings(map, buttonMap) );
                            name = null;
                        }
                    }
                }
                
                reader.close();
            } catch ( IOException ex ) {
                // fail silently
            } catch ( NumberFormatException ex ) {
                // fail silently
            }
            
            return mappings;
        }
        
        private static final void save( String path, Map<String, GamePadMappings> mappings )
        {
            try {
                final File file = new File( path );
                final PrintStream writer = new PrintStream( file );
                for ( final Map.Entry<String, GamePadMappings> mapping : mappings.entrySet() ) {
                    writer.println( mapping.getKey() );
                    final GamePadMappings padMap = mapping.getValue();
                    
                    final int size = Math.min( padMap.map.length, padMap.buttonMap.length );
                    final StringBuilder mappingsLine = new StringBuilder(size*7);
                    boolean isFirst = true;
                    
                    for ( int i = 0; i < size; i++ ) {
                        if ( isFirst ) {
                            isFirst = false;
                        } else {
                            mappingsLine.append(',');
                        }
                        
                        mappingsLine.append(padMap.map[i]);
                        mappingsLine.append(',');
                        mappingsLine.append(padMap.buttonMap[i]);
                    }
                    
                    writer.println( mappingsLine.toString() );
                }
                
                writer.flush();
                writer.close();
            } catch ( IOException ex ) {
                // fail silently
            }
        }
        
        private static void copyArray( final int[] to, final int[] from )
        {
            final int size = Math.min( to.length, from.length );
            System.arraycopy( from, 0, to, 0, size );
        }
        
        private static void copyArray( boolean[] to, boolean[] from )
        {
            final int size = Math.min( to.length, from.length );
            System.arraycopy( from, 0, to, 0, size );
        }
        
        private final int[] map;
        private final boolean[] buttonMap;
        
        private GamePadMappings( int[] map, boolean[] buttonMap )
        {
            this.map = map;
            this.buttonMap = buttonMap;
        }
        
        private void set( int[] toMap, boolean[] toButtonMap )
        {
            GamePadMappings.copyArray( toMap, this.map );
            GamePadMappings.copyArray( toButtonMap, this.buttonMap );
        }
        
        public String toString()
        {
            final StringBuilder str = new StringBuilder();
            
            str.append( "{ " );
            for ( int i = 0; i < map.length; i++ ) {
                if ( i > 0 ) {
                    str.append( ", " );
                }
                
                str.append( i );
                str.append( " => [" );
                str.append( map[i] );
                str.append( ":" );
                
                if ( i < buttonMap.length ) {
                    str.append( buttonMap[i] );
                } else {
                    str.append( true );
                }
                
                str.append( "]" );
            }
            str.append( " }" );
            
            return str.toString();
        }
    }
}
