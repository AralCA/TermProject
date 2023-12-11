package Scripts;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;


public class mainScript {
    public static card[] GameDeck;
    public static card[] PlayerDeck;
    public static card[] CompDeck; 

    public static card[] TablePlayerDeck;
    public static card[] TableCompDeck;

    public static int scoreComp = 0;
    public static int scorePlayer = 0;

    public static boolean isPlayersTurn = true;

    

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static void main(String[] args){


        Play();
        
    }

    public static void Play(){
        GameDeck = createDeck("main");
        PlayerDeck = createDeck("player");
        CompDeck = createDeck("comp");
        //Decks Done
        System.out.println("Decks have been generated.");

        mixAndShuffle();
        PlayerDeck = completeDeck(PlayerDeck);
        CompDeck = completeDeck(CompDeck);
        System.out.println("Decks have been dealt and are ready to go!");

        System.out.println("\nGame starts soon! Take a look at your hand:");
        printStart();
        System.out.println("\nDon't worry. I will show you your hand in every move.\nBut I am very sorry to tell you that you can't see your opponent's hand. You may have to guess.");


    }

    public static card[] createDeck(String deckName){

        if(deckName == "main"){
            card[] returnMainDeck= new card[40];

            int currColorCount = 0;
            String currColorName = "";
            
            for(int i = 0;i<40;i++){
                
                currColorName = colorInterpertation(currColorCount);
                

                returnMainDeck[i] = new card(currColorName,"main",true,i%10+1);

                if(((i+1)%10)==0) currColorCount++;
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

        deck = finalDeckDealer(deck);

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

    public static card[] finalDeckDealer(card[] deck){
        Random rd = new Random(Calendar.getInstance().getTimeInMillis());

        card[] finalDeck = new card[4];
        deck = shuffler(deck);
        for(int i = 0 ; i<4 ; i++)
        finalDeck[i] = deck[i];
        

        return finalDeck;
    } 

    public static void printStart(){

        String currTerminalColor = "";
        for (card c : PlayerDeck) {
            switch(c.color){
                case "Red":
                    currTerminalColor = ANSI_RED;
                    break;
                case "Blue":
                    currTerminalColor = ANSI_BLUE;
                    break;
                case "Green":
                    currTerminalColor = ANSI_GREEN;
                    break;
                case "Yellow":
                    currTerminalColor = ANSI_YELLOW;
                    break;
                case "FLIP":
                case "DOUBLE":
                    currTerminalColor = ANSI_CYAN;
                    break;
            }
            if(c.num!=0)
            System.out.print(currTerminalColor + c.num + ANSI_RESET);
            else if (c.color == "FLIP" || c.color == "DOUBLE")
            System.out.print(currTerminalColor + c.color + ANSI_RESET);
            System.out.print(" ");
        }
    }
}