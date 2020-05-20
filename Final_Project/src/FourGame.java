import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class FourGame extends Game {
    //constructor---
    public FourGame(Player p1, Player p2, Player p3, Player p4) {
        this.board = new Board();
        this.players = new Player[4];
        p1.setWalls(5);
        p2.setWalls(5);
        p3.setWalls(5);
        p4.setWalls(5);

        players[0] = p1;
        players[1] = p2;
        players[2] = p3;
        players[4] = p4;

        shuffleArray(players);

        players[0].xPawn = 0;
        players[0].yPawn = 3;
        this.board.cells[0][3].content = players[0].pawn;

        players[1].xPawn = 3;
        players[1].yPawn = 8;
        this.board.cells[3][8].content = players[1].pawn;

        players[2].xPawn = 8;
        players[2].yPawn = 3;
        this.board.cells[8][3].content = players[2].pawn;

        players[3].xPawn = 3;
        players[3].yPawn = 0;
        this.board.cells[3][0].content = players[3].pawn;
    }

    //methods---
    public int winCheck() {
        int winner = -1;
        for (int i = 0; i < 9; i++) {
            if (this.board.cells[8][i].content == players[0].pawn) {
                winner = 0;
                gameOver = true;
                break;
            } else if (this.board.cells[i][0].content == players[1].pawn) {
                winner = 1;
                gameOver = true;
                break;
            } else if (this.board.cells[0][i].content == players[2].pawn) {
                winner = 2;
                gameOver = true;
                break;
            } else if (this.board.cells[i][8].content == players[3].pawn) {
                winner = 3;
                gameOver = true;
                break;
            }


        }
        return winner;
    }

    @Override
    public boolean legalPosition(int x, int y) {
        // TODO: 5/21/2020  
        return false;
    }

    @Override
    public boolean legalMove(Player p, String dir) {
        // TODO: 5/21/2020  
        return false;
    }


    private static void shuffleArray(Player[] array) {
        List<Player> list = Arrays.asList(array);

        Collections.shuffle(list);
        list.toArray(array);

    }
}