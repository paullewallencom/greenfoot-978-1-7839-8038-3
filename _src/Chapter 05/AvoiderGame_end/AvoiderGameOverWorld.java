import greenfoot.*; 
import java.nio.*;
import java.nio.file.*;
import java.io.IOException;
import java.util.List;

public class AvoiderGameOverWorld extends World {
    public AvoiderGameOverWorld() {    
        super(600, 400, 1); 
        
        // get the badges!
        List<Badge> badgeList = BadgeCenter.getInstance().getBadges();
        int yPos = 130;
        while(!badgeList.isEmpty()) {
            Badge nextBadge = badgeList.remove(0);
            addObject(nextBadge, 60, yPos);
            yPos += 70;
        }

    }

    public void act() {
        // Restart the game if the user clicks the mouse anywhere 
        if( Greenfoot.mouseClicked(this) ) {
            AvoiderWorld world = new AvoiderWorld();
            Greenfoot.setWorld(world);
        }
    }

    public void setPlayerHighScore(String s) {
        Label scoreBoardMsg = new Label("Your Score:  " + s, 35);
        Label highScoreMsg = new Label("Your Best:  " + recordAndReturnHighScore(s), 35);
        addObject(scoreBoardMsg, getWidth()/2, getHeight() * 2 / 3);
        addObject(highScoreMsg, getWidth()/2, (getHeight() * 2 / 3) + 45);
    }

    private String recordAndReturnHighScore(String s) {
        String hs = null;
        try {
            Path scoreFile = Paths.get("./scoreFile.txt");

            if( Files.exists(scoreFile) ) {
                byte[] bytes = Files.readAllBytes(scoreFile);
                hs = new String(bytes);

                if( Integer.parseInt(s) > Integer.parseInt(hs) ) {
                    Files.write(scoreFile, s.getBytes());  // Default is CREATE | TRUNCATE_EXISTING | WRITE
                    hs = s;
                }
            } else {
                Files.write(scoreFile, s.getBytes());
                hs = s;
            }

        } catch( IOException e ) {
            System.out.println("IOException");
        }
        
        return hs;
    }
}
