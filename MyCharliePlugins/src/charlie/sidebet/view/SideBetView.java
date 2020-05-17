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

package charlie.sidebet.view;

import charlie.audio.Effect;
import charlie.audio.SoundFactory;
import charlie.card.Hid;
import charlie.plugin.ISideBetView;
import charlie.view.AMoneyManager;
import static charlie.view.AMoneyManager.STAKE_HOME_X;
import static charlie.view.AMoneyManager.STAKE_HOME_Y;
import charlie.view.sprite.AtStakeSprite;
import charlie.view.sprite.Chip;

import charlie.view.sprite.ChipButton;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import org.apache.log4j.Logger;


/**
 * This class implements the side bet view
 * @author Ron Coleman, Ph.D.
 */
public class SideBetView implements ISideBetView {
    private final Logger LOG = Logger.getLogger(SideBetView.class);
    
    public final static int X = 400;
    public final static int Y = 200;
    public final static int DIAMETER = 50;
    
    protected Font font = new Font("Arial", Font.BOLD, 18);
    protected BasicStroke stroke = new BasicStroke(3);
    
    // See http://docs.oracle.com/javase/tutorial/2d/geometry/strokeandfill.html
    protected float dash1[] = {10.0f};
    protected BasicStroke dashed
            = new BasicStroke(3.0f,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER,
                    10.0f, dash1, 0.0f);   

    // Creates the list of chips.
    protected List<ChipButton> buttons;
    protected List<Chip> chips = new ArrayList<>();
    protected int amt = 0;
    protected AMoneyManager moneyManager;
    
    //flags for if the outcome is a win or loss
    protected boolean win = false;
    protected boolean lose = false;
    
    // Gets the side bet wager sprite.
    protected AtStakeSprite sideBetWager = new AtStakeSprite(X,Y,0);


    public SideBetView() {
        LOG.info("side bet view constructed");
    }
    
    /**
     * Sets the money manager.
     * @param moneyManager 
     */
    @Override
    public void setMoneyManager(AMoneyManager moneyManager) {
        this.moneyManager = moneyManager;
        this.buttons = moneyManager.getButtons();
    }
    

    /**
     * Registers a click for the side bet.
     * @param x X coordinate
     * @param y Y coordinate
     */
    @Override
    public void click(int x, int y) {
        int oldAmt = amt;
        
        
        
        
        // Test if any chip button has been pressed.
        for(ChipButton button: buttons) {
            if(button.isPressed(x, y)) {
                amt += button.getAmt();
                SoundFactory.play(Effect.CHIPS_IN);
                LOG.info("A. side bet amount "+button.getAmt()+" updated new amt = "+amt);
                
                Chip chip = new Chip(button.getImage(), (int) ((450 + Math.random()*20)
                        + chips.size() * 5), (int) (170 + (Math.random()*20)),amt);
                chips.add(chip);
                
            } 
        }
        
        //tests if at-stake area is pressed 
        if(this.sideBetWager.isPressed(x, y)) {
            if(oldAmt == amt) {
                amt = 0;
                SoundFactory.play(Effect.CHIPS_OUT);
                chips.clear();
                LOG.info("B. side bet amount cleared");
            }
        }
    }

    /**
     * Informs view the game is over and it's time to update the bankroll for the hand.
     * @param hid Hand id
     */
    @Override
    public void ending(Hid hid) {
        double bet = hid.getSideAmt();
        
        if(bet == 0){
            return;
        }

        if(bet > 0){
            win = true;
        }
        
        if(bet < 0){
            lose = true;
        }
        

        LOG.info("side bet outcome = " + bet);
        
        // Update the bankroll
        moneyManager.increase(bet);
        
        LOG.info("new bankroll = "+moneyManager.getBankroll());
        
    }

    /**
     * Informs view the game is starting
     */
    @Override
    public void starting() {
        
        //Clears the win and lose graphics
        win = false;
        lose = false;

    }

    /**
     * Gets the side bet amount.
     * @return Bet amount
     */
    
    
    @Override
    public Integer getAmt() {
        return amt;
    }

    /**
     * Updates the view
     */
    @Override
    public void update() {
    }

    /**
     * Renders the view
     * @param g Graphics context
     */
    @Override
    public void render(Graphics2D g) {
        // Draw the at-stake place on the table
        g.setColor(Color.RED); 
        g.setStroke(dashed);
        g.drawOval(X-DIAMETER/2, Y-DIAMETER/2, DIAMETER, DIAMETER);
        
        // Draw the at-stake amount
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(""+amt, X-5, Y+5);
        
        //draw the payouts
        g.drawString("SUPER 7 pays 3:1", X + 50, Y - 70);
        g.drawString("ROYAL MATCH pays 25:1", X + 50, Y - 50);
        g.drawString("EXACTLY 13 pays 1:1", X + 50, Y - 30);
        
        //Renders the chips
        for(int i=0; i < chips.size(); i++) {
            Chip chip = chips.get(i);
            chip.render(g);
        }
        
        // Renders the win graphic if the side bet loses
        if(win == true){
            g.setColor(Color.GREEN);
            g.fillRect (X-5, Y-10, 60, 30); 
            g.setFont(font);
            g.setColor(Color.BLACK);
            g.drawString("WIN!", X+5, Y+10);
        }
        
        // Renders the lose graphic if the side bet loses
        if(lose == true){
            g.setColor(Color.BLACK);
            g.fillRect (X+1, Y-10, 60, 30); 
            g.setFont(font);
            g.setColor(Color.WHITE);
            g.drawString("LOSE!", X+5, Y+10);
        }

        
//        for(Chip chip: chips)
//            g.drawImage(chip.getImage(), chip.getX(), chip.getY(), null);
    }
}