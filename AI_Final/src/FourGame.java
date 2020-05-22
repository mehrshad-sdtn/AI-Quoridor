public class FourGame extends Game {

    public FourGame(Player player1, Player player2 , Player player3 , Player player4) {
        super();
        this.pawns = new Pawn[4];
        players = new Player[4];
        players[0] = player1;
        players[1] = player2;
        players[2] = player3;
        players[3] = player4;
        pawns[0] = players[0].pawn;
        pawns[1] = players[1].pawn;
        pawns[2] = players[2].pawn;
        pawns[3] = players[3].pawn;

        players[0].setMax_pawns(5);
        players[1].setMax_pawns(5);
        players[2].setMax_pawns(5);
        players[3].setMax_pawns(5);

        state.initBoard();
        //------->putting pawns on the board to start the game
        initPlayer(0);
        initPlayer(1);
        initPlayer(2);
        initPlayer(3);

    }

    private void initPlayer(int i) {
        if(i == 0)
        { players[i].pos_x = 0;
          players[i].pos_y = 8;
        }
        else if(i == 1){
           players[i].pos_x = 16;
           players[i].pos_y = 8;
        }
        else if(i == 2){
            players[i].pos_x = 8;
            players[i].pos_y = 0;
        }
        else if(i == 3){
            players[i].pos_x = 8;
            players[i].pos_y = 16;
        }


        this.state.board[players[i].pos_x][players[i].pos_y].symbol = players[i].symbol;

        if(i==0)
        {
            players[i].goal = 16;
            players[i].goalType = "row";
        } //---> setting the goal for the players
        else if(i==1)
        {
            players[i].goal = 0;
            players[i].goalType = "row";
        }
        else if(i==2)
        {
            players[i].goal = 16;
            players[i].goalType = "column";
        }
        else if(i==3)
        {
            players[i].goal = 0;
            players[i].goalType = "column";
        }


    }

}
