import java.util.*;

public class AI extends Player {
    public AI() {
        super();
    }


    @Override
    public void play(State state) {
        //create the mini_max tree here
    }

    public int hEval(State state){
        int h = 0;
      if(goalType.equals("row")){
         h = h +(2 * (Math.abs(goal - pos_x)));

      }
      return h;
    }
}//class end