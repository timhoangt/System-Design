/*
 Copyright (c) 2014 Ron Coleman

 Permission is hereby granted, free of charge, to any person obtaining
 a copy of this software and associated documentation files (the
 "Software"), to deal in the Software without restriction, including
 without limitation the rights to use, copy, modify, merge, publish,
 distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to
 the following conditions:

 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package charlie.sidebet.rule;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.plugin.ISideBetRule;
import org.apache.log4j.Logger;

/**
 * This class implements the side bet rule for Super 7.
 * @author Ron Coleman
 */
public class SideBetRule implements ISideBetRule {
    private final Logger LOG = Logger.getLogger(SideBetRule.class);
    
    private final Double PAYOFF_SUPER7 = 3.0;
    private final Double PAYOFF_ROYALMATCH = 25.0;
    private final Double PAYOFF_EXACTLY13 = 1.0;

    /**
     * Apply rule to the hand and return the payout if the rule matches
     * and the negative bet if the rule does not match.
     * @param hand Hand to analyze.
     * @return Payout amount: <0 lose, >0 win, =0 no bet
     */
    @Override
    public double apply(Hand hand) {
        Double bet = hand.getHid().getSideAmt();
        LOG.info("side bet amount = "+bet);
        
        if(bet == 0) {
            return 0.0;
        }
        
        LOG.info("side bet rule applying hand = "+ hand);
        
        // Gets the values of the fist two cards in you hand.
        Card card1 = hand.getCard(0);
        Card card2 = hand.getCard(1);
 
        //Checks if the fist card is a 7.
        if(card1.getRank() == 7) {
            LOG.info("side bet SUPER 7 matches");
            return bet * PAYOFF_SUPER7;
        }
        
        //Checks if it is a royal match
        if(card1.getRank() == Card.QUEEN && card2.getRank() == Card.KING 
                  && card1.getSuit() == card2.getSuit()){
            LOG.info("side bet ROYAL MATCH matches");
            return bet * PAYOFF_ROYALMATCH;
        }  
        
    
        if(card1.getRank() == Card.KING && card2.getRank() == Card.QUEEN 
                  && card1.getSuit() == card2.getSuit()){
            LOG.info("side bet ROYAL MATCH matches");
            return bet * PAYOFF_ROYALMATCH;
        }  
        
        //Checks if it is 13
        if(hand.getValue() == 13) {
            LOG.info("side bet exact 13 matches");
            return bet * PAYOFF_EXACTLY13;   
        }
          

        
        LOG.info("side bet rule no match");
        
        return -bet;
    }
}