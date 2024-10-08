package Scripts;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;


public class mainScript {
    public static card[] GameDeck;
    public static card[] PlayerDeck;
    public static card[] CompDeck; 

    public static card[] TablePlayerDeck;
    public static card[] TableCompDeck;

    public static int scoreComp = 0;
    public static int scorePlayer = 0;
    public static int round = 0;

    public static boolean isPlayersTurn = true;

    public static int turnCount = 1;
    public static int cardsDrawnPlayer = 0;
    public static int cardsDrawnComputer = 0;
    public static int cardsPlayedPlayer = 0;
    public static int cardsPlayedComp = 0;

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

        dealAndShuffle();
        PlayerDeck = completeDeck(PlayerDeck);
        CompDeck = completeDeck(CompDeck);
        System.out.println("Decks have been dealt and are ready to go!");

        System.out.println("\nGame starts soon! Take a look at your hand:");
        printDeck(PlayerDeck);
        System.out.println("\nDon't worry. I will show you your hand in every move.\nBut I am very sorry to tell you that you can't see your opponent's hand. You may have to guess.");
        GameDeck = shuffler(GameDeck);

        GameTurn();


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

    public static void dealAndShuffle(){
        GameDeck = shuffler(GameDeck);

        for(int i=0; i<5; i++){
            PlayerDeck[i] = GameDeck[i];
            GameDeck[i] = new card();
            CompDeck[i] = GameDeck[GameDeck.length-i-1];
            GameDeck[GameDeck.length-i-1] = new card();
        }

        card[] newMainDeck = new card[GameDeck.length-10];
        int notEmptyMainDeck = 0;

        for(card c : GameDeck){
            if(c.color!=null){
                newMainDeck[notEmptyMainDeck] = c;
                notEmptyMainDeck++;
            }
        }
        GameDeck = newMainDeck;


    }

    public static card[] shuffler(card[] deck){
        Random rd = new Random(System.nanoTime());

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
            }while(finalDeck[lastRandom].color!=null);
            
            finalDeck[lastRandom] = cards;
            count--;
        }
        return finalDeck;
    }

    public static card[] completeDeck(card[] deck){
        Random rd = new Random(System.nanoTime());
        
        for(int i = 0; i<5; i++){
            
            int tempColor = rd.nextInt(4);
            int tempNum = rd.nextInt(6)+1;
            Boolean isPos = rd.nextBoolean();

            if(!isPos) tempNum*=-1;

            String tempColorName = colorInterpertation(tempColor);

            deck[i+5]= new card(tempColorName,deck[0].deck,isPos,tempNum);

        }

        if(rd.nextInt(2)==0){
            deck[8] = new card("DOUBLE",deck[0].deck,true,0);
        }
        if(rd.nextInt(2)==0){
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
        Random rd = new Random(System.currentTimeMillis()*System.currentTimeMillis());

        card[] finalDeck = new card[4];
        deck = shuffler(deck);
        for(int i = 0 ; i<4 ; i++)
        finalDeck[i] = deck[i];
        

        return finalDeck;
    } 

    public static void printDeck(card[] deck){
        String currTerminalColor = "";
        for (card c : deck) {
            if(c!=null){
            if(c.color == null) System.out.print("USED ");
            else{

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

    }

    public static void printGameTurn(){
        //System.out.print("\033[H\033[2J");
        System.out.print("Computer Hand: ");
        printDeck(CompDeck);
        System.out.print("\nComputer table: ");
        printDeck(TableCompDeck);
        System.out.print("\nPlayer Hand: ");
        printDeck(PlayerDeck);
        System.out.print("\nPlayer table: ");
        printDeck(TablePlayerDeck);

        int playerTableAddUp = 0;
        int cursor = 0;

        for(card card : TablePlayerDeck){

            if(card==null)
            continue;

            if(card.color!="DOUBLE" && card.color!="FLIP"){
                playerTableAddUp += card.num;
            }else if(card.color.equals("DOUBLE")){
                playerTableAddUp += TablePlayerDeck[cursor-1].num;
            }else if(card.color.equals("FLIP")){
                playerTableAddUp += TablePlayerDeck[cursor-1].num*-1;
            }
            
            cursor++;
        }
        System.out.println("Add Up "  + playerTableAddUp);
        
    }

    public static void GameTurn(){

        Scanner sc = new Scanner(System.in);

        TableCompDeck = new card[50];
        TablePlayerDeck = new card[50];

        boolean isPlayerOnStand = false;

        card playedCard = new card();

        do{
            if(turnCount == 1){
                System.out.println("\nGame has started.");
                TablePlayerDeck[0] = GameDeck[0];
                System.out.print("Your hand: ");
                printCard(TablePlayerDeck[0]);
                System.out.println();
                printDeck(PlayerDeck);
                cardsDrawnPlayer++;
                turnCount++;
            }

            int move = 0;

            if(!isPlayerOnStand){
                System.out.println("\nStand[1], Draw[2], Play From Hand[3]");
                move = sc.nextInt();
            }else{
                System.out.println("You are on stand you little cheeky guy!");
                sc.nextInt();
            }

            switch(move){
                case 1:
                    isPlayerOnStand = true;
                    break;
                case 2:
                    TablePlayerDeck[cardsDrawnPlayer] = GameDeck[cardsDrawnComputer+cardsDrawnPlayer];
                    cardsDrawnPlayer++;
                    break;
                case 3:
                    System.out.println("Which card you want to play? Please state it's place from 1 to 4.");
                    int playedCardHand= sc.nextInt();
                    
                    if(PlayerDeck[playedCardHand-1].color!= null){
                        playedCard =  PlayerDeck[playedCardHand-1];
                    }
                    
                    PlayerDeck[playedCardHand-1] = new card();
                    TablePlayerDeck[cardsDrawnPlayer+cardsPlayedPlayer] = playedCard;
                    cardsPlayedPlayer++;
                    break;

            }

            int playerTableAddUp = 0;
            int cursor = 0;
            boolean isThereAnyNegative = false;

            for(card card : TablePlayerDeck){
                if(card==null)
                continue;

                if(card.color!="DOUBLE" && card.color!="FLIP"){
                    playerTableAddUp += card.num;
                    if(card.isPositive==false)isThereAnyNegative=true;
                }else if(card.color.equals("DOUBLE")){
                    playerTableAddUp += TablePlayerDeck[cursor-1].num;
                }else if(card.color.equals("FLIP")){
                    playerTableAddUp += TablePlayerDeck[cursor-1].num*-1;
            }
            cursor++;
            }

            boolean goNextRoundPlayer = false;

            System.out.println("Is there any negative" + " " + isThereAnyNegative);

            if(!isThereAnyNegative && playerTableAddUp>20) goNextRoundPlayer = true;

            boolean goNextRoundBot = !bot();
            printGameTurn();
            turnCount++;

            if(goNextRoundBot && goNextRoundPlayer) System.out.println("ITS OVER");
            else{
                System.out.println(goNextRoundBot + " " +goNextRoundPlayer);
            }
        }while(true);
    }

    public static void printCard(card card){

        String currTerminalColor = null;


            switch(card.color){
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

        

        if(card.num!=0)
            System.out.print(currTerminalColor + card.num + ANSI_RESET);
        else
            System.out.print(currTerminalColor + card.color + ANSI_RESET);
    }

    public static boolean bot(){
        System.out.println(round + " round");
        boolean onStand = false;
        boolean canPlayCard = true;
        int compTableAddUp = 0;
        int cursor = 0;


        int activeCards = 0;
        for(card c : CompDeck){
            if(c.color!= null) activeCards++;

        }

        if(activeCards-(3-round) <1) canPlayCard = false;

        for(card card : TableCompDeck){

            if(card==null)
            continue;


            if(card.color!="DOUBLE" && card.color!="FLIP"){
                compTableAddUp += card.num;
            }else if(card.color.equals("DOUBLE")){
                compTableAddUp += TableCompDeck[cursor-1].num;
            }else if(card.color.equals("FLIP")){
                compTableAddUp += TableCompDeck[cursor-1].num*-1;
            }
            
            cursor++;
        }

        if(compTableAddUp==20) return false;

        if(compTableAddUp>=14){

            Boolean isAnyCardPlayed = false;

            card selectedCardToPlay;
            cursor = 0;
            int cardCursor = 0;
            for(card c : CompDeck){
                if(c.color == null){
                    cardCursor++;
                    continue;
                }
                int possibleAddUp=compTableAddUp;
                if(c.color!=null){

                    if(c.color!="DOUBLE" && c.color!="FLIP"){
                        possibleAddUp += c.num;
                    }else if(c.color.equals("DOUBLE")){
                        possibleAddUp += TableCompDeck[cardsDrawnComputer+cardsPlayedComp-1].num;
                    }else if(c.color.equals("FLIP")){
                        possibleAddUp += TableCompDeck[cardsDrawnComputer+cardsPlayedComp-1].num*-2;
                    }
                    cursor++;
                }
                if((possibleAddUp>compTableAddUp && possibleAddUp<21 && compTableAddUp<18 && canPlayCard) || riskFactor(round, possibleAddUp)){
                    selectedCardToPlay = c;
                    CompDeck[cardCursor]= new card();
                    TableCompDeck[cardsDrawnComputer+cardsPlayedComp] = c;
                    cardsPlayedComp++;
                    isAnyCardPlayed= true;
                    System.out.println("Comp played card in 17 21");
                    break;
                }else if((possibleAddUp<21 && compTableAddUp>20 && canPlayCard) || riskFactor(round, possibleAddUp)){
                    selectedCardToPlay = c;
                    CompDeck[cardCursor]= new card();
                    TableCompDeck[cardsDrawnComputer+cardsPlayedComp] = c;
                    cardsPlayedComp++;
                    isAnyCardPlayed= true;
                    System.out.println("Comp played card in 21 20 15");
                    break;
                }else onStand = true;
                cardCursor++;
            }
            if(!isAnyCardPlayed){
                System.out.println("No card Played");
                onStand = true;
            }
        }else if(compTableAddUp<14){
            System.out.println("Comp drawn card from game deck");
            TableCompDeck[cardsDrawnComputer+cardsPlayedComp] = GameDeck[cardsDrawnComputer+cardsDrawnPlayer];
            cardsDrawnComputer++;
            printGameTurn();
        }
        
        System.out.println(compTableAddUp);
        
        if(onStand) return false;
        else return true;
    }

    public static int deckAddUp(){
        return 0;
    }

    public static boolean riskFactor(int round, int possAddUp){
        if((18-round)<possAddUp && possAddUp<21) return true;
        else return false; 
    }
}