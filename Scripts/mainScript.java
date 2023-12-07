package Scripts;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;


public class mainScript {
    public static card[] GameDeck;
    public static card[] PlayerDeck;
    public static card[] CompDeck; 
    public static void main(String[] args){

        Play();
        
    }

    public static void Play(){
        GameDeck = createDeck("main");
        PlayerDeck = createDeck("player");
        CompDeck = createDeck("Comp");
        //Decks Done
        System.out.println("Decks have been generated.");

        for (card card1 : shuffler(GameDeck)) {
            System.out.println(card1.num);
        }
    }

    public static card[] createDeck(String deckName){

        if(deckName == "main"){
            card[] returnMainDeck= new card[10];
            
            for(int i = 1;i<11;i++)
            returnMainDeck[i-1] = new card("main","main",true,i);

            return returnMainDeck;
        }else{
            card[] returnDeck = new card[10];
            return returnDeck;
        }
        
    }

    public static void mixAndShuffle(){

    }

    public static card[] shuffler(card[] deck){
        Random rd = new Random(Calendar.getInstance().getTimeInMillis());

        int deckSize = deck.length;
        int count = deckSize;
        int lastRandom;

        card[] finalDeck = new card[deckSize];
        for(int i=0;i<deckSize;i++){
            finalDeck[i] = new card();
        }

        for(card cards : deck){
            do{
                lastRandom = rd.nextInt(deckSize);
            }while(finalDeck[lastRandom].num!=0);
            

            finalDeck[lastRandom] = cards;
            count--;
        }
        System.out.println("done");
        return finalDeck;
    }
    
}