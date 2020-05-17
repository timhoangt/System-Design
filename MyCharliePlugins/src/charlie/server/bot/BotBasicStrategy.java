package charlie.server.bot;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.client.BasicStrategy;
import charlie.util.Play;

/**
 *
 * @author timhoangt
 * Bot basic strategy extends basic strategy making it
 * essentially a duplicate of basic strategy.
 */
class BotBasicStrategy extends BasicStrategy {
    @Override
    public Play getPlay(Hand hand, Card upCard) {
        Card card1 = hand.getCard(0);
        Card card2 = hand.getCard(1);
        
        if(hand.isPair() && hand.getValue() >= 4 && hand.getValue() < 12) {
            return doSection2(hand,upCard);
        }
        else if(hand.isPair() && hand.getValue() >= 12 && hand.getValue() < 22) {
            return doSection1(hand,upCard);
        }
        else if(hand.size() == 2 && (card1.getRank() == Card.ACE || card2.getRank() == Card.ACE)) {
            return doSection3(hand,upCard);
        }
        else if(hand.getValue() >=5 && hand.getValue() < 12) {
            return doSection2(hand,upCard);
        }
        else if(hand.getValue() >= 12) {
            return doSection1(hand,upCard);
        }
        return Play.NONE;
    }
}
