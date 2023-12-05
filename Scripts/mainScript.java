package Scripts;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class mainScript {
    public static void main(String[] args){
        
        
    }

    public static card[] createDeck(boolean isMainDeck){

        if(isMainDeck){
            card[] returnMainDeck= new card[10];
            
            for(int i = 1;i<11;i++)
            returnMainDeck[i-1] = new card("main","main",true,i);

            return returnMainDeck;
        }
        
        return null;
        
    } 
    
}