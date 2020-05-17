package charlie.sidebet.test;

import charlie.card.Card;

/**
 *
 * @author timhoangt
 * 
 */
public class Super7Shoe extends charlie.card.Shoe {
    
    /*
    * Clears the shoe and puts these cards inside. This sets the deck so you
    * can play through with specified cards. This overrides the shuffling and
    * loading so that the cards are set.
    */
    
    @Override
    public void init() {        
        cards.clear();
        
        // Scenario 1
        cards.add(new Card(7,Card.Suit.HEARTS));        //player 7
        cards.add(new Card(Card.KING,Card.Suit.CLUBS)); //dealer 10
        cards.add(new Card(9,Card.Suit.SPADES));        //player 16
        cards.add(new Card(8,Card.Suit.DIAMONDS));      //dealer 18
        cards.add(new Card(3,Card.Suit.CLUBS));         //player 19
        
        // Scenario 2
        cards.add(new Card(8,Card.Suit.HEARTS));        //player 8
        cards.add(new Card(Card.KING,Card.Suit.CLUBS)); //dealer 10
        cards.add(new Card(9,Card.Suit.SPADES));        //player 17
        cards.add(new Card(8,Card.Suit.DIAMONDS));      //dealer 18
        cards.add(new Card(3,Card.Suit.CLUBS));         //player 20
        
        // Scenario 3
        cards.add(new Card(7,Card.Suit.HEARTS));            //player 7
        cards.add(new Card(Card.KING,Card.Suit.CLUBS));     //dealer 10
        cards.add(new Card(9,Card.Suit.SPADES));            //player 16
        cards.add(new Card(Card.QUEEN,Card.Suit.DIAMONDS)); //dealer 20 
        cards.add(new Card(3,Card.Suit.CLUBS));             //player 19
        
        // Scenario 4
        cards.add(new Card(8,Card.Suit.HEARTS));            //player 8
        cards.add(new Card(Card.KING,Card.Suit.CLUBS));     //dealer 10
        cards.add(new Card(9,Card.Suit.SPADES));            //player 17
        cards.add(new Card(Card.QUEEN,Card.Suit.DIAMONDS)); //dealer 20
        cards.add(new Card(2,Card.Suit.CLUBS));             //player 19
    
    }
    
    /*
    *Overrides shuffling so that it never shuffles.
    */
    
    @Override
    public boolean shuffleNeeded() {
        return false;
    }
    
}
