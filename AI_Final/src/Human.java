import java.util.*;
public class Human extends Player {


    public Human() {
        super();
    }

    @Override
    public void play(State state) {
        try {


            Scanner input = new Scanner(System.in);
            System.out.println(this.symbol + " turn: \n1.move pawn\n2.place a wall");
            int code = input.nextInt();
            if (code == 1) {
                System.out.println("enter the direction [up/down/left/right]");
                String dir = input.next();

                if (allowedMove(state, dir)) {
                    movePawn(state, dir);
                }
                else{
                    System.out.println("you can get past a wall");
                }
            } else if (code == 2) {
                  if(this.max_walls > 0){
                      System.out.println("enter the wall position [x&y]");
                      int x = input.nextInt();
                      int y = input.nextInt();

                      placeWall(state, x, y);
                }else
                      System.err.println("no walls left");

            }




        } catch (InputMismatchException ex) {
            System.out.println("wrong input");
        }


    }



}