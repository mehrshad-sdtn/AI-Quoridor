import java.util.*;
class TwoGame extends Game {
    //constructor---
    public TwoGame(Player p1, Player p2) {
        this.board = new Board();
        this.players = new Player[2];
        this.players[0] = p1;
        this.players[1] = p2;

        p1.setWalls(10);
        p2.setWalls(10);

        p1.xPawn = 0;
        p1.yPawn = 4;
        this.board.cells[p1.xPawn][p1.yPawn].content = players[0].pawn;
        p2.xPawn = 8;
        p2.yPawn = 4;
        this.board.cells[p2.xPawn][p2.yPawn].content = players[1].pawn;

        this.gameOver = false;


    }


    //method to return the winner name & tweak game-over boolean---------------------------------------------------
    @Override
    public int winCheck() {
        int winner = -1; //-1 means that no one is winning
        for (int i = 0; i < 9; i++) {
            if (this.board.cells[8][i].content == players[0].pawn) {
                winner = 0;
                gameOver = true;
                break;
            } else if (this.board.cells[0][i].content == players[1].pawn) {
                winner = 1;
                gameOver = true;
                break;
            }
        }
        return winner;

    }


    ///-------------------------------------------------boolean to determine if the move is available---------------
    public boolean legalMove(Player p, String dir) {
        boolean flag = true;
        if (dir.equals("up")) {
            if (p.xPawn == 0 || this.board.h_walls[p.xPawn - 1][p.yPawn].content == '-') {
                flag = false;
            }

        } else if (dir.equals("down") ) {
            if (p.xPawn == 8 || this.board.h_walls[p.xPawn][p.yPawn].content == '-') {
                flag = false;
            }
        }
        if (dir.equals("left")) {
            if (p.yPawn == 0 || this.board.v_walls[p.xPawn][p.yPawn - 1].content == '|') {
                flag = false;
            }

        } else if (dir.equals("right")) {
            if (p.yPawn == 8  || this.board.v_walls[p.xPawn][p.yPawn].content == '|') {
                flag = false;
            }
        }
        return flag;
    }



    public boolean legalPosition ( int x, int y){
        return ((x >= 0) && (y >= 0) && (x < 9) && (y < 9));
    }

}
