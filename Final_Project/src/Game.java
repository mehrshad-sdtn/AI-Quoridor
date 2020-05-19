import java.util.*;

abstract class Game {
   Player[] players;
   Board board;
   boolean gameOver;
    public abstract String winCheck();
    public abstract void play(Player p);

}
//---------------------------
class TwoGame extends Game {

    //constructor---
    public TwoGame(Player p1 , Player p2) {
        this.board = new Board();
        this.players = new Player[2];
        p1.setWalls(10);
        p2.setWalls(10);
        this.players[0] = p1;
        this.players[1] = p2;

        p1.xPawn = 0; p1.yPawn = 4;
        this.board.cells[p1.xPawn][p1.yPawn].content = players[0].pawn;
        p1.xPawn = 8;
        this.board.cells[p1.xPawn][p1.yPawn].content = players[1].pawn;

        this.gameOver = false;
    }



    //method to return the winner name & tweak game-over boolean
    public String winCheck(){
        String winner = null;
        for (int i = 0; i < board.cells.length; i++) {
            if( this.board.cells[0][i].content == players[1].pawn){
                winner = "A";
                gameOver = true;
                break;
            }
            else if( this.board.cells[8][i].content == players[0].pawn){
                winner = "B";
                gameOver = true;
                break;
            }
        }
        return winner;

    }

 //-method for playing
    @Override
    public void play(Player p) {

            System.out.println("choose your action [1.move pawn / 2.set wall]");

            Scanner scn = new Scanner(System.in);
            int action = scn.nextInt();

            if(action == 1){
                String dir;
                while(true){
                System.out.println("direction?");
                dir = scn.next();
                if(legalMove(p,dir))
                    movePawn(p,dir);
                else continue;

                }

            }
            else if(action == 2){
                // TODO: 5/19/2020
            }



    }

    private boolean legalMove(Player p, String dir) {
        boolean flag = true;
      if(dir.equals("up")){
          if(p.xPawn == 0 || this.board.h_walls[p.xPawn-1][p.yPawn].content == '-'){
              flag = false;
          }

      }else if(dir.equals("down") || this.board.h_walls[p.xPawn][p.yPawn].content == '-'){
          if(p.xPawn == 8){
              flag = false;
          }
      }
        if(dir.equals("left") || this.board.v_walls[p.xPawn][p.yPawn-1].content == '-'){
            if(p.yPawn == 0){
                flag = false;
            }

        }else if(dir.equals("right") || this.board.v_walls[p.xPawn][p.yPawn].content == '-'){
            if(p.yPawn == 8){
                flag = false;
            }
        }
        return flag;
    }


    private void movePawn(Player p, String dir) {

        Board alter = new Board();
        if (dir.equals("up")){
            p.xPawn--;
        }
        else if (dir.equals("down")){
            p.xPawn++;
        }
        else if (dir.equals("left")){
            p.yPawn--;
        }
        else if (dir.equals("right")){
            p.yPawn++;
        }
        alter.cells[p.xPawn][p.yPawn].content = p.pawn;


        this.board = alter;
    }


}




//----------------------------
