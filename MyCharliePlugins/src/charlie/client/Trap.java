/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charlie.client;
import charlie.card.Hid;
import charlie.dealer.Seat;
import charlie.message.Message;
import charlie.message.view.to.Blackjack;
import charlie.message.view.to.Bust;
import charlie.message.view.to.Charlie;
import charlie.message.view.to.Lose;
import charlie.message.view.to.Outcome;
import charlie.message.view.to.Push;
import charlie.message.view.to.SplitResponse;
import charlie.message.view.to.Win;
import charlie.plugin.ITrap;
import org.apache.log4j.Logger;

/**
 *
 * @author timhoangt
 */
public class Trap implements ITrap {
    
    protected static Logger LOG = Logger.getLogger(Trap.class);
    protected int youWin = 0;
    protected int youLose = 0;
    protected int youPush = 0;
    protected int youBust = 0;
    protected int youBlackjack = 0;
    protected int youCharlie = 0;
    protected int youSplits = 0;
    protected double youBankRoll = charlie.util.Constant.PLAYER_BANKROLL;
    
    @Override
    public void onReceive(Message msg) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if(msg instanceof Win){
            Hid hid = ((Outcome) msg).getHid();
            if(hid.getSeat() == Seat.YOU){
                youWin++;   
            }
            youBankRoll = youBankRoll + (hid.getAmt());
        }
        
        else if(msg instanceof Lose){
            Hid hid = ((Outcome) msg).getHid();
            if(hid.getSeat() == Seat.YOU){
                youLose++;   
            }
            youBankRoll = youBankRoll - (hid.getAmt());
        }
        
        else if(msg instanceof Push){
            Hid hid = ((Outcome) msg).getHid();
            if(hid.getSeat() == Seat.YOU){
                youPush++;   
            }
        }
        
        else if(msg instanceof Bust){
            Hid hid = ((Outcome) msg).getHid();
            if(hid.getSeat() == Seat.YOU){
                youBust++;   
            }
            youBankRoll = youBankRoll - (hid.getAmt());
        }
        
        else if(msg instanceof Blackjack){
            Hid hid = ((Outcome) msg).getHid();
            if(hid.getSeat() == Seat.YOU){
                youBlackjack++;   
            }
            youBankRoll = youBankRoll + (hid.getAmt());
        }
        
        else if(msg instanceof Charlie){
            Hid hid = ((Outcome) msg).getHid();
            if(hid.getSeat() == Seat.YOU){
                youCharlie++;   
            }
            youBankRoll = youBankRoll + (hid.getAmt());
        }
        
        else if(msg instanceof SplitResponse){
            Hid hid = ((Outcome) msg).getHid();
            if(hid.getSeat() == Seat.YOU){
                youSplits++;   
            }
        }
        
        LOG.info("wins: " + youWin);
        LOG.info("loses: " + youLose);
        LOG.info("pushes: " + youPush);
        LOG.info("busts: " + youBust);
        LOG.info("blackjacks: " + youBlackjack);
        LOG.info("charlies: " + youCharlie);
        LOG.info("bankroll: " + youBankRoll);
        LOG.info("splits: " + youSplits);
        
    }
    
    @Override
    public void onSend(Message msg) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    
}
