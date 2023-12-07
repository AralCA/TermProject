package Scripts;

public class card {
    public String color;
    public String deck;
    public boolean isPositive = true;
    public int colorCode = 0;

    public int num=0;

    public card(String col,String dec, boolean isPos, int number){
        color = col;
        deck = dec;
        isPositive = isPos;
        num = number;
    }

    public card(){
        num = 0;
    }

}
