import java.util.*;

public class RandomPlayer extends Player {

    public RandomPlayer(char pawn) {
        super(pawn);
    }

    @Override
    public String getMove() {
        String str;
        Random rand = new Random();
        int op = rand.nextInt(2);
        op++;
        str = op+":";
        if(op == 1){
            int dir = rand.nextInt(3);
            if(dir == 0)
                str+="up";
            else  if(dir == 1)
                str+="down";
            else if(dir == 2)
                str+="left";
            else if(dir == 3)
                str+="right";
        }
        else if(op == 2){
            int type = rand.nextInt(2);
            if(type == 0){
                int corx = rand.nextInt(9);//0-8
                corx = corx*2;//0-16
                int cory = rand.nextInt(8);//0-7
                cory = cory*2 + 1; //1-15
                str = str + corx +"&"+ cory;

            }
            else if(type == 1){
                int cory = rand.nextInt(9);//0-8
                cory = cory*2;//0-16
                int corx = rand.nextInt(8);//0-7
                corx = corx*2 + 1; //1-15
                str = str + corx +"&"+ cory;
            }
        }
        return str;
    }
}
