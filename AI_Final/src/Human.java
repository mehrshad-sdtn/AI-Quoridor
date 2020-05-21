import java.util.*;
public class Human extends Player{


    public Human() {
        super();
    }

    @Override
    public void play(State state) {
        try{
        Scanner input = new Scanner(System.in);
        System.out.println(this.symbol+" turn: \n1.move pawn\n2.place a wall");
        int code = input.nextInt();
        if(code == 1){
            System.out.println("enter the direction [up/down/left/right]");
            String dir = input.next();

            if(allowed(state , dir)){
               movePawn(state , dir);
            }
        }
        else if(code == 2){
            System.out.println("enter the wall position [x&y]");
            int x = input.nextInt();
            int y = input.nextInt();
            placeWall(state,x,y);
          }
        }catch(InputMismatchException ex){
            System.out.println("wrong input");
        }


    }

    private boolean allowed(State state, String dir) {
        boolean flag = true;

        return flag;
    }

    private void placeWall(State state, int x, int y) {
        try{
        if((x%2 == 1 && y%2 == 0) && inBounds(state, x,y) && inBounds(state, x,y+2))
        state.board[x][y].setSymbol('#');
        state.board[x][y+2].setSymbol('#');
        }catch(ArrayIndexOutOfBoundsException ex){
            System.out.println("out of bounds");
        }
    }

    //----------------------------------------------------
    private boolean inBounds(State state, int x, int y) {
        return ((0 <= x) && (x <= 16)) && ((0 <= y) && (y <= 16));
    }

    //----------------------------------------------------------
    private void movePawn(State state, String dir) {

        char temp = this.pawn.symbol;
         state.board[pos_x][pos_y].setSymbol('o');

        changePosition(dir);

        if(state.board[pos_x][pos_y].symbol != 'o'){
            System.out.println("collision");
            changePosition(dir);
        }

        if(inBounds(state , pos_x , pos_y))
           state.board[pos_x][pos_y].setSymbol(temp);


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
