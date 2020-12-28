import java.util.ArrayList;
import java.util.List;

public class BadgeCenter  //Implemented as a Singleton
{
    private int clovers, rocks, enemies, cupcakes;
    int rockBadges;
    private String previous;
    private ArrayList<Badge> badges = new ArrayList<Badge>();
    private static final BadgeCenter INSTANCE = new BadgeCenter();

    private BadgeCenter() {
        clovers = rocks = enemies = cupcakes = 0;
        rockBadges = 0;
    }
    
    public static BadgeCenter getInstance() {
        return INSTANCE;
    }

    public void hitClover() {
        ++clovers;
        previous = "clover";
        if( clovers % 20 == 0 ) {
            if( clovers > 80 ) {
                awardBadge("Magically Delicious ");
            } else {
                awardBadge(clovers + " Clovers ");
            }
        }
    }

    public void hitRock() {
        if( previous != "rock" ) {
            rocks = 0;
        }
        ++rocks;
        previous = "rock";
        if( rocks > 2 ) {
            rockBadges++;
            rocks = 0;
        }    
    }

    public void hitEnemy() {
        ++enemies;
        previous = "enemy";
        if( enemies % 10 == 0 ) {
            if( enemies > 60 ) {
                awardBadge( "Unbreakable " );
            } else {
                awardBadge("Hit " + enemies + " times ");
            }
        }
    }

    public void hitCupcake() {
        ++cupcakes; // Check if under 3 when return badges
        previous = "cupcake";
    }

    public List<Badge> getBadges() {
        if( cupcakes < 3 ) {
            awardBadge("Master Avoider ");
        }
        if( rockBadges > 0 ) {
            awardBadge("Turkey x " + rockBadges + " ");
        }
        cupcakes = 0;
        return badges;
    }

    private void awardBadge(String title) {
        badges.add(new Badge(title));
    }

}
