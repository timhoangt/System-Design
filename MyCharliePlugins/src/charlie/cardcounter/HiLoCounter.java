/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charlie.cardcounter;

import charlie.card.Card;
import charlie.plugin.ICardCounter;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import org.apache.log4j.Logger;

/**
 *
 * @author timhoangt
 */
public class HiLoCounter implements ICardCounter {
    private final Logger LOG = Logger.getLogger(HiLoCounter.class);
    public final static int X = 400;
    public final static int Y = 200;
    protected Font font = new Font("Arial", Font.BOLD, 18);
    protected BasicStroke stroke = new BasicStroke(3);
    double currentShoeSize = 0;
    double correctedShoe = 0;
    int runningCount = 0;
    int trueCount = 0;
    int bet = 1;
    boolean gonnaShuffle = false;

    /**
     * gets the shoe size at the beginning of the round
     * @param shoeSize 
     */
    @Override
    public void startGame(int shoeSize) {
        currentShoeSize = shoeSize;
        correctedShoe = currentShoeSize/52;
        gonnaShuffle = false;
    }

    /**
     * Updates all the variables according to the card drawn.
     * If it is going to shuffle, nothing will update because you cant change
     * you bet anyways.
     * @param card 
     */
    @Override
    public void update(Card card) {
        //updates shoe size
        currentShoeSize = currentShoeSize-1;
        correctedShoe = currentShoeSize/52;
        
        if (gonnaShuffle == false){
            //updates running count
            if (card.getRank() < 7 && card.getRank() != 1){
                runningCount++;
            }
            else if (card.getRank() > 9 || card.getRank() == 1){
                runningCount--;
            }

            //updates true count
            trueCount = (int) (runningCount/correctedShoe);

            //updates bet
            if(trueCount > 0){
                bet = 1 + trueCount;
            }
            else{
                bet = 1;
            }
        }
    }

    /**
     * draws the counting card variables
     * @param g 
     */
    @Override
    public void render(Graphics2D g) {
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString("Hi-Lo", X-20, Y+80);
        g.drawString("Shoe Size: "+ correctedShoe, X-20, Y+100);
        g.drawString("Running Count: "+ runningCount, X-20, Y+120);
        g.drawString("True Count: "+ trueCount, X-20, Y+140);
        g.drawString("Bet: "+ bet + " chips", X-20, Y+160);
    }

    /**
     * Resets the variables once the deck is pending shuffle.
     * 
     */
    @Override
    public void shufflePending() {
        gonnaShuffle = true;
        currentShoeSize = 52;
        runningCount = 0;
        trueCount = 0;
        bet = 1;
    }
    
}
