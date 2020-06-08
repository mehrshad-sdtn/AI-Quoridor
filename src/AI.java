import java.util.*;

public class AI extends Player {
    Quoridor game;
    static int counter = 0;

    public AI(char pawn) {
        super(pawn);

    }

    public void setGame(Quoridor game)
    {
        this.game = game;
        game.simulating = true;
    }


    public String getMove() {
        var a = Double.NEGATIVE_INFINITY;
        var b = Double.POSITIVE_INFINITY;
        String[] result = miniMax(5 , a , b , true);

        System.out.println(result[1]);
        System.out.println("move: "+ result[0]);
        return result[0];

    }

    //[0]: move //[1]:score
   public String[] miniMax(int depth ,double alpha , double beta , boolean isMax) {

       Set<String> legalMoves;
        if(isMax) {
            legalMoves = game.findAvailableMoves(this);
        }
        else {
            legalMoves = game.findAvailableMoves(game.players[0]);
        }




       double bestScore = 0;
       String bestMove = null;

       if (depth == 0) {
           bestScore = eval(game);
       }

        else{
          if (isMax) {
              bestScore = alpha;
              for (var possibleMove : legalMoves) {
                  game.makeMove(possibleMove, this);

                  if(possibleMove.startsWith("2"))
                      this.walls++;

                  var eval = miniMax(depth - 1, alpha , beta , false);

                  game.undo();
                  eval[0] = possibleMove;

                  if (Double.parseDouble(eval[1]) > alpha) {
                      alpha = Double.parseDouble(eval[1]);
                      bestMove = eval[0];
                      bestScore = alpha;
                  }
                  if(alpha >= beta)
                      break;


              }

          }

          else{
              bestScore = beta;
              for (var possibleMove : legalMoves){
                  game.makeMove(possibleMove, game.players[0]);

                  if(possibleMove.startsWith("2"))
                      game.players[0].walls++;

                  var eval = miniMax(depth - 1 , alpha , beta , true);

                  game.undo();
                  eval[0] = possibleMove;
                  if (Double.parseDouble(eval[1]) < beta) {
                      beta = Double.parseDouble(eval[1]);
                      bestMove = eval[0];
                      bestScore = beta;
                  }
                  if(alpha >= beta)
                      break;



              }
          }

        }

       return new String[]{bestMove, bestScore + ""};

    }




    public double eval(Quoridor state){
        char[][] copy = new char[17][17];
        for (int i = 0; i < 17; i++) {
            System.arraycopy(state.board[i], 0, copy[i], 0, 17);
        }
        double man = Math.abs(9 - (state.players[1].x - state.players[1].goal)/2); //--players manhattan distance to the goal
        double opponentMan = Math.abs((state.players[0].x - state.players[0].goal)/2); //--opponent's manhattan
        double oppDist = 0;



       return 1*man + 1*opponentMan + 2*oppDist;
    }











}//end class
