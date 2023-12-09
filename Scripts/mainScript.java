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
        CompDeck = createDeck("comp");
        //Decks Done
        System.out.println("Decks have been generated.");

        /*for (card card1 : shuffler(GameDeck)) {
            //System.out.println(card1.num);
        }
        */

        mixAndShuffle();
        PlayerDeck = completeDeck(PlayerDeck);
        CompDeck = completeDeck(CompDeck);
        

        System.out.println(PlayerDeck[9].);

    }

    public static card[] createDeck(String deckName){

        if(deckName == "main"){
            card[] returnMainDeck= new card[40];

            int currColorCount = 0;
            String currColorName = "";
            
            for(int i = 0;i<40;i++){
                
                currColorName = colorInterpertation(currColorCount);
                

                returnMainDeck[i] = new card(currColorName,"main",true,i%10+1);

                if(((i%10)+1)==0) currColorCount++;
            }



            return returnMainDeck;
        }else{
            card[] returnDeck = new card[10];
            return returnDeck;
        }
        
    }

    public static void mixAndShuffle(){
        GameDeck = shuffler(GameDeck);

        for(int i=0; i<10; i++){
            PlayerDeck[i] = GameDeck[i];
            CompDeck[i] = GameDeck[GameDeck.length-i-1];
        }


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

    public static card[] completeDeck(card[] deck){
        Random rd = new Random(Calendar.getInstance().getTimeInMillis());
        
        for(int i = 0; i<5; i++){
            
            int tempColor = rd.nextInt(4);
            int tempNum = rd.nextInt(6)+1;
            Boolean isPos = rd.nextBoolean();

            String tempColorName = colorInterpertation(tempColor);

            deck[i+5]= new card(tempColorName,deck[0].deck,isPos,tempNum);

        }

        if(rd.nextInt(5)==0){
            deck[8] = new card("DOUBLE",deck[0].deck,true,0);
        }
        if(rd.nextInt(5)==0){
            deck[9] = new card("FLIP",deck[0].deck,true,0);
        }


        return deck;
    }

    public static String colorInterpertation(int colorNum){
        String currColorName;
        switch(colorNum){
            case 0:
                currColorName = "Red";
                break;
            case 1:
                currColorName = "Blue";
                break;
            case 2:
                currColorName = "Yellow";
                break;
            case 3:
                currColorName = "Green";
                break;
            default:
                currColorName = "Red";
                break;

        }
        return currColorName;
    }

    
}