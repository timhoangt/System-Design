
package charlie.client;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.plugin.IAdvisor;
import charlie.util.Play;
import java.util.HashMap;

/**
 *
 * @author timhoangt
 */
public class SimpleAdvisor implements IAdvisor {
    
    protected HashMap<String, Play> plays = new HashMap<>();

    @Override
    public Play advise(Hand myHand, Card upCard) {
        
        /*1. myHand = null
        2. upCard = null
        3.  myHand.size < 5
        4. myHand.size >= 2
        5. myHand.getValue < 21
        6. myHand.size = 2 && myHand.getCard[0] = myHand.getCard[1] (for split)
        7. myHand.size = 2 (for double down)
        
        1. Play.NONE
        2. Catch the exception and log it
        
        a. If your hand = A+A or 8+8, split.
        b. If your hand ≥ 17, stay.
        c. If your hand ≤ 10, hit.
        d. If your hand = 11, double down.
        e. If your hand ≥ 12 and your hand ≤ 16 and dealer up-card+10 ≤ 16, stay.
        f. If your hand ≥ 12 and your hand ≤ 16 and dealer up-card+10 > 16, hit.*/

        if (myHand == null || upCard == null){
            return Play.NONE; 
        }

        else if (myHand.size() < 2  || myHand.size() > 5 || myHand.getValue() > 20){
            return Play.NONE;
        }

        else {
            if(myHand.getCard(0).getRank() == Card.ACE && myHand.getCard(1).getRank() == Card.ACE && myHand.size() == 2){
                return Play.SPLIT;
            }
            else if(myHand.getCard(0).getRank() == 8 && myHand.getCard(1).getRank() == 8 && myHand.size() == 2){
                return Play.SPLIT;
            }
            else if(myHand.getValue() >= 17){
                return Play.STAY;
            }
            else if(myHand.getValue() == 11 && myHand.size() == 2){
                return Play.DOUBLE_DOWN;
            }
            else if(myHand.getValue() <= 10){
                return Play.HIT;
            }
            else if(16 >= myHand.getValue() && myHand.getValue() >= 12 && upCard.value() + 10 <= 16){
                return Play.STAY;
            }
            else if(16 >= myHand.getValue() && myHand.getValue() >= 12 && upCard.value() + 10 >= 16){
                return Play.HIT;
            }
            else {
                return Play.NONE;
            }
        }
    }
}

