import java.util.*;


public class TwoGame extends Game {


    public TwoGame(Player player1, Player player2) {
        super();
        this.pawns = new Pawn[2];
        players = new Player[2];
        players[0] = player1;
        players[1] = player2;
        pawns[0] = players[0].pawn;
        pawns[1] = players[1].pawn;

        players[0].setMax_walls(10);
        players[1].setMax_walls(1);

        state.initBoard();
        //------->putting pawns on the board to start the game
        initPlayer(0);
        initPlayer(1);

    }

    private void initPlayer(int i) {
        if(i == 0)
           players[i].pos_x = 0;
        else if(i == 1)
            players[i].pos_x = 16;

        players[i].pos_y = 8;
        this.state.board[players[i].pos_x][players[i].pos_y].symbol = players[i].symbol;

        if(i==0)
          players[i].goal = 16; //---> setting the goal for the player
        else if(i==1)
          players[i].goal = 0;

        players[i].goalType = "row";
    }


}
