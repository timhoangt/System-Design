
package charlie.client;
import charlie.card.Card;
import charlie.message.Message;
import charlie.message.view.to.GameStart;
import charlie.message.view.to.Deal;
import charlie.message.view.to.Shuffle;
import charlie.plugin.ITrap;
import org.apache.log4j.Logger;

/**
 *
 * @author timhoangt
 * This class traps messages of GameStart, Deal, and Shuffle
 * and updates the ShoeSize, RunningCount, TrueCount, and Suggested Bet.
 * Essentially, it is a card counter.
 */
public class CountingTrap implements ITrap {
    protected static Logger LOG = Logger.getLogger(Trap.class);
    protected double youShoeSize = 0;
    protected double youRunningCount = 0;
    protected double youTrueCount = 0;
    protected int youBet = 0;
    
    @Override
    public void onReceive(Message msg){
        
        //if GameStart message comes, update the shoe size.
        if(msg instanceof GameStart){
            youShoeSize = ((GameStart) msg).shoeSize()/52.0;
        }
        
        /**if the deal message occurs and is not null, 
         * update the running count depending on the card value and face.
         * 
         */
        if(msg instanceof Deal){
            Card card = ((Deal) msg).getCard();
            if (card != null){
            
                if (card.value() >= 2 && card.value() <= 6){
                    youRunningCount = youRunningCount + 1;
                }
                else if (card.value() == 10 || card.isAce() == true){
                    youRunningCount = youRunningCount - 1;
                }
                else if (card.value() >= 7 && card.value() <= 9){
                    youRunningCount = youRunningCount + 0;
                }

                //Get the true count by divinding the running count by the shoe size
                youTrueCount = youRunningCount / youShoeSize;

                //get the bet size from the true count
                if(youTrueCount > 0){
                    youBet = (int)Math.ceil((youTrueCount + 1) / 100);
                }
                else{
                    youBet = 1;
                }
                
            }
        }
        
        //if the shuffle message occurs, reset the counts and suggested bet
        if(msg instanceof Shuffle){
            youRunningCount = 0;
            youTrueCount = 0;
            youBet = 0;
        }
        
        //log the information
        LOG.info("shoe size: " + youShoeSize);
        LOG.info("running count: " + youRunningCount);
        LOG.info("true count: " + youTrueCount);
        LOG.info("bet: " + youBet);
    }
    
    @Override
    public void onSend(Message msg){
  
    }
    
}
