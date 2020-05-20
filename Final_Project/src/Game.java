import java.util.*;
abstract class Game {
    Player[] players;
    Board board;
    boolean gameOver;

    public abstract int winCheck();
    public abstract boolean legalPosition ( int x, int y);
    public abstract boolean legalMove(Player p, String dir);

    public void play(Player p) throws Exception {


        try {


            System.out.println("choose your action [1.move pawn / 2.set wall]");
          Scanner scn = new Scanner(System.in);
          int action = scn.nextInt();

         if (action == 1) {
            String dir;
            while (true) {
                System.out.println("direction?");
                dir = scn.next();
                if (legalMove(p, dir)) {
                    movePawn(p, dir);
                    break;
                } else {
                    System.out.println("illegal move , try another direction");
                }

            }

        } else if (action == 2) {
             while(true){
            System.out.println("vertical or horizontal? [v/h]");
            char type = scn.next().charAt(0);

                 System.out.println("where do yo want to place the wall? {enter index of x,y?}");
                 int x = scn.nextInt();
                 int y = scn.nextInt();

                 if(canScape(p , x , y , type)){
                   placeWall(p, x, y, type);
                   break;
                 }
                 else {
                     System.out.println("illegal move , you can't trap a player");
                 }

             }
         }
        }catch (InputMismatchException ex) {
            System.out.println("error occurred");
        }


    }

    protected boolean canScape(Player p, int position_x, int position_y, char type){


        //todo ----> ino farda tamumesh mknm
        return true;
    }

    ///-----------------------------------------------------method to move the pawn in a direction----------
    private void movePawn(Player p, String dir){

        Board alter = this.board;

        alter.cells[p.xPawn][p.yPawn].content = 'O';

        changePosition(p, dir);

        if (alter.cells[p.xPawn][p.yPawn].content != 'O') {
            System.out.println("collision");
            changePosition(p, dir);
        }

        alter.cells[p.xPawn][p.yPawn].content = p.pawn;
        this.board = alter;
    }


    //------------------------------------------method for performing position change---------------------------------
    private void changePosition(Player p, String dir) {
        switch (dir) {
            case "up":
                p.xPawn--;
                break;
            case "down":
                p.xPawn++;
                break;
            case "left":
                p.yPawn--;
                break;
            case "right":
                p.yPawn++;
                break;
        }
    }




//-------------checks if a position is empty so a wall could be placed in it (checks if it's legal for wall or not?)--------------------------------
    private boolean legalWall(int position_x, int position_y, char sym) {
        boolean flag = true;
        if(sym == '|'){
            if(board.v_walls[position_x][position_y].content == '|'){
                flag = false;
            }

        }else if(sym == '-'){
            if(board.h_walls[position_x][position_y].content == '-'){
                flag = false;
            }
        }
        return flag;
    }



    public void placeWall(Player p, int position_x, int position_y, char type) {
        if (legalPosition(position_x, position_y)) {

            if (type == 'v') {

                if(legalWall(position_x , position_y , '|') && legalWall(position_x + 1 , position_y , '|')){

                    this.board.v_walls[position_x][position_y].content = '|';
                    this.board.v_walls[position_x + 1][position_y].content = '|';
                    p.walls--;
                }


            } else if (type == 'h') {

                if(legalWall(position_x , position_y , '-') && legalWall(position_x  , position_y + 1 , '-')) {

                    this.board.h_walls[position_x][position_y].content = '-';
                    this.board.h_walls[position_x][position_y + 1].content = '-';
                    p.walls--;
                }

            }
        }
    }

    public void printWinner() {
        System.out.println(players[winCheck()].pawn + " won");
    }



}

