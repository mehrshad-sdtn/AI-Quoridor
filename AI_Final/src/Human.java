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
                System.out.println("enter the wall position [x&y]");
                int x = input.nextInt();
                int y = input.nextInt();
                if(legalWall(state,x,y)){
                  placeWall(state, x, y);
                }
                else{
                    System.out.println("you can't create trap");
                }
            }




        } catch (InputMismatchException ex) {
            System.out.println("wrong input");
        }


    }

    ///---------------->check to see if the wall is legal
    private boolean legalWall(State state, int x, int y) {
        boolean flag = true;
        if(traps(state , x , y)){
            flag = false;
        }
        return flag;
    }

    //--->does the wall create a trap

    private boolean traps(State state, int x, int y) {//-->recursively iterates through walls to see if there is a scape or not
        boolean flag = false;
        //check four directions for the next wall
        //mark visited walls
        //if a visited wall is reached : return true
        

        return flag;
    }


    //--------------------------------------->checks if a wall is faced or not
    private boolean allowedMove(State state, String dir) {
        boolean flag = true;
        //--
        int temp_x  = pos_x;
        int temp_y  = pos_y;

        switch (dir) {
            case "up":
                temp_x--;
                break;

            case "down":
                temp_x++;
                break;

            case "left":
                temp_y--;
                break;

            case "right":
                temp_y++;
                break;
        }
        if(state.board[temp_x][temp_y].symbol != ' '){
            flag = false;
        }

        return flag;
    }

    ///----------->method to place the wall at index [x][y]
    private void placeWall(State state, int x, int y) {

        try{
            //----> horizontal wall
          if ((x % 2 == 1 && y % 2 == 0) && inBounds(state, x, y) && inBounds(state, x, y + 2)) {
              GameObject h_wall = new Wall();
              h_wall.setSymbol('#');
              if(state.board[x][y].symbol == ' '){
                 state.board[x][y] = h_wall;
                 state.board[x][y + 2] = h_wall;
              }else{
                  System.out.println("a wall already exists");
              }
          }
          //----->vertical wall
          else if ((x % 2 == 0 && y % 2 == 1) && inBounds(state, x, y) && inBounds(state, x + 2, y )) {
              GameObject v_wall = new Wall();
              v_wall.setSymbol('|');
              if(state.board[x][y].symbol == ' '){
                 state.board[x][y] = v_wall;
                 state.board[x + 2][y] = v_wall;
              }
              else{
                  System.out.println("a wall already exists");
              }
          }
        }catch(ArrayIndexOutOfBoundsException ex)
        { System.out.println("out of bounds error");}






          }


    //----------------------------------------------------
    private boolean inBounds(State state, int x, int y) {
        return ((0 <= x) && (x <= 16)) && ((0 <= y) && (y <= 16));
    }

    //----------------------------------------------------------
    private void movePawn(State state, String dir) {

        int prev_x = pos_x;
        int prev_y = pos_y;

        changePosition(dir);

        if(inBounds(state , pos_x , pos_y)){
           if(state.board[pos_x][pos_y].symbol != 'o'){
               System.out.println("collision");
               changePosition(dir);
           }


        state.board[pos_x][pos_y].setSymbol(this.symbol);
        state.board[prev_x][prev_y].setSymbol('o');


        }
        else {
            System.out.println("out of bounds");
            pos_x = prev_x;
            pos_y = prev_y;

        }


    }



    //------------------------------->method for changing the indexes of current pawn position
    private void changePosition(String dir) {
        switch (dir) {
            case "up":
                this.pos_x-=2;
                break;

            case "down":
                this.pos_x+=2;
                break;

            case "left":
                this.pos_y-=2;
                break;

            case "right":
                this.pos_y+=2;
                break;
        }
    }


}
