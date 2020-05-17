package charlie.server.bot;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.dealer.Dealer;
import charlie.dealer.Seat;
import charlie.plugin.IBot;
import charlie.util.Play;
import java.util.List;
import java.util.Random;

/**
 *
 * @author timhoangt
 * Huey implements IBot and makes moves according to the rules
 * of BotBasicStrategy
 */
public class Huey implements IBot, Runnable{
    /**
     * Here are the initializers which set the scene for
     * each of the variables to come.
     */
    protected final int MAX_THINKING = 5;
    protected Seat mine;
    protected Hand myHand;
    protected Dealer dealer;
    protected Random ran = new Random();
    protected Card upCard;
    protected BotBasicStrategy bbs = new BotBasicStrategy();
    protected boolean myTurn;
    
    /**
     * Gets the bots hand.
     * @return Hand
     * 
     */
    @Override
    public Hand getHand() {
        return myHand;
    }

    /**
     * Sets the dealer for the bot.
     * @param dealer Dealer
     */
    @Override
    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    /**
     * Sits the bot in seat.
     * @param seat Seat
     */
    @Override
    public void sit(Seat seat) {
        this.mine = seat;
        Hid hid = new Hid(seat);
        myHand = new Hand(hid);
    }

    /**
     * Starts game with list of hand ids and shoe size.
     * The number of decks is shoeSize / 52.
     * @param hids Hand ids
     * @param shoeSize Starting shoe size
     */
    @Override
    public void startGame(List<Hid> hids, int shoeSize) {
        
    }

    /**
     * Ends a game with shoe size.
     * @param shoeSize Shoe size
     */
    @Override
    public void endGame(int shoeSize) {
        
    }

    /**
     * Deals a card to player.
     * All players receive all cards which is useful for card counting.
     * @param hid Hand id which might not necessarily belong to player.
     * @param card Card being dealt
     * @param values Hand values, literal and soft
     */
    @Override
    public void deal(Hid hid, Card card, int[] values) {
        /**
         * Check which seat is being dealt.
         * If its the dealer, that is the upcard.
         * If its you (the bots) it will send you to make a play.
         * If it is not you, it is not your turn.
         */
        if(hid.getSeat() == Seat.DEALER){
            upCard = card;
        }
        if(hid.getSeat() != mine){
            myTurn = false;
            return;
        }
        if(!myTurn){
            return;
        }
        play(hid);
    }

    /**
     * Offers player chance to buy insurance.
     */
    @Override
    public void insure() {
        
    }

    /**
     * Tells player the hand is broke.
     * If the hand is broke, it will no longer be your turn.
     * @param hid Hand id
     */
    @Override
    public void bust(Hid hid) {
        myTurn = false;
    }

    /**
     * Tells player the hand won.
     * @param hid Hand id
     */  
    @Override
    public void win(Hid hid) {
        
    }

    /**
     * Tells player the hand won with blackjack.
     * If the hand is a blackjack, it will no longer be your turn.
     * @param hid Hand id
     */  
    @Override
    public void blackjack(Hid hid) {
        myTurn = false;
    }

    /**
     * Tells player the hand won with Charlie.
     * If the hand is a charlie, it will no longer be your turn.
     * @param hid Hand id
     */  
    @Override
    public void charlie(Hid hid) {
        myTurn = false;
    }

    /**
     * Tells player the hand lost to dealer.
     * @param hid Hand id
     */   
    @Override
    public void lose(Hid hid) {
        
    }

    /**
     * Tells player the hand pushed.
     * @param hid Hand id
     */  
    @Override
    public void push(Hid hid) {
        
    }

    /**
     * Tells player shoe to be shuffled before the start of the next game.
     */
    @Override
    public void shuffling() {
        
    }

    /**
     * Tells player to start playing hand.
     * @param hid Hand id
     */  
    @Override
    public void play(Hid hid) {
        /**
         * If it is not your seat, it is not your turn.
         */
        if(hid.getSeat() != mine){
            myTurn = false;
            return;
        }
        /**
         * If it is your turn, start a new thread.
         */
        myTurn = true;

        new Thread(this).start();

    }

    /**
     * Tells player to split the hand.
     * @param newHid the new hand id
     * @param origHid the original hand id
     */
    @Override
    public void split(Hid newHid, Hid origHid) {
        
    }

    /**
     * Comes here when the actor begins.
     */
    @Override
    public void run() {
        /**
         * Starts a randomized "thinking" timer.
         */
        try{
            int thinking = ran.nextInt(MAX_THINKING * 1000);
            Thread.sleep(thinking);
        }
        catch(Exception e){
            
        }
        
        /**
         * Get a play from basic bot strategy.
         */
        Play play = bbs.getPlay(myHand,upCard);
        
        /**
         * Checks which play was returned from basic bot strategy.
         */
        switch(play){
            /**
             * If its stay, play stay.
             */
            case STAY:
                dealer.stay(this, this.myHand.getHid());
                break;
                
            /**
             * If its hit, play hit.
             */
            case HIT:
                dealer.hit(this,this.myHand.getHid());
                break;
            
            /**
             * If its double down, play double down.
             */
            case DOUBLE_DOWN:
                myHand.getHid().dubble();
                dealer.doubleDown(this,this.myHand.getHid());
                break;
            
            /**
             * If its split, play stay since split can not be done for bots.
             */
            case SPLIT:
                dealer.stay(this, this.myHand.getHid());
                break;
            
        }
        
    }
    
}
