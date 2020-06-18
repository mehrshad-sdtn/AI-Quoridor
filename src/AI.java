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
    }


    public String getMove() {
        var a = Double.NEGATIVE_INFINITY;
        var b = Double.POSITIVE_INFINITY;
        game.simulating = true;
        String[] result = miniMax(5, a , b , true);

        System.out.println(result[1]);
        System.out.println("move: "+ result[0]);
        game.simulating = false;
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

       if (depth == 0 || this.x == this.goal
               || game.players[0].x == game.players[0].goal) {
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

        Player maximizer = this;
        Player minimizer = null;

        for (var plr: state.players) {
            if(plr.pawn != this.pawn) {
                minimizer = plr;
            }
        }


        double distFromGoal_ai = Math.abs(8 - (maximizer.x - maximizer.goal)/2); //--ai manhattan
        assert minimizer != null;
        double distFromGoal_human =  Math.abs((minimizer.x - minimizer.goal)/2);  //--opponent's manhattan
        double movesToNextRow_max = 9 - state.getMovesToNextRow(maximizer , "up");   //for ai
        double movesToNextRow_Min =  state.getMovesToNextRow(minimizer , "down");  //for opponent


        return (10 * distFromGoal_ai) + (2 * distFromGoal_human) +
                (1 * movesToNextRow_max) + (5 * movesToNextRow_Min);
    }











}//end class
