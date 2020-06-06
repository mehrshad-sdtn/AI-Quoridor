import java.util.*;

public class AI extends Player {
    ArrayList<char[][]> availableMoves;
    Quoridor game;
    static int i = 0;

    public AI(char pawn) {
        super(pawn);
        availableMoves = new ArrayList<>();
    }

    public void setGame(Quoridor game) {
        this.game = game;
    }

    public String getMove() {
        setAvailableMoves();
        System.out.println(miniMax(game.board, 3, true) + "");
        System.out.println(availableMoves.size());


        return null;
    }

    public double miniMax(char[][] state , int depth , boolean isMax){

       double bestScore;
        if(depth == 0){
            bestScore = eval(state);
        }
        else{

            if(isMax){
                double maxEval = Double.NEGATIVE_INFINITY;
                for(var child : availableMoves) {
                    double eval = miniMax(child, depth - 1, false);
                    maxEval = Math.max(maxEval, eval);
                }
                bestScore =  maxEval;

            }
            else{
                double minEval = Double.POSITIVE_INFINITY;
                for(var child : availableMoves){
                  double eval = miniMax(child,depth - 1 , true);
                  minEval = Math.min(minEval , eval);
                }
                bestScore =  minEval;
            }

        }
        return bestScore;
    }

    public void setAvailableMoves() {
        char[][] copy = new char[17][17];
        StringBuilder move = null;

        move = new StringBuilder("1:");

        for (int dir = 0; dir < 4; dir++) {
            if(dir == 0) move.append("up");
            else if(dir == 1) move.append("down");
            else if(dir == 2) move.append("right");
            else move.append("left");

            game.makeMove(move.toString(), this);
            move = new StringBuilder("1:");

            for (int i = 0; i < 17; i++) {
                System.arraycopy(game.board[i], 0, copy[i], 0, 17);
            }



            if(game.possible){
               this.availableMoves.add(copy);
               copy = new char[17][17];
                game.undo();
            }


        }

        //-------------------------------------------------
        move = new StringBuilder("2:");

        for (int i = 16; i >= 0; i--) {
            for (int j = 16; j >= 0; j--) {

                if((i%2 == 0 && j%2 == 1)||(i%2 == 1 && j%2 == 0)){
                     move.append(i).append("&").append(j);

                     game.makeMove(move.toString(), this);
                     move = new StringBuilder("2:");

                    for (int c = 0; c < 17; c++) {
                        System.arraycopy(game.board[c], 0, copy[c], 0, 17);
                    }


                   if(game.possible){
                        this.availableMoves.add(copy);

                        copy = new char[17][17];
                        game.undo();
                    }

                }

            }

        }


        }

    private double eval(char[][] state) {
        return 2;
    }





}//end class
