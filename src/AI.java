import java.util.*;

public class AI extends Player {
    Quoridor game;
    double[] chromosome;
    static int counter = 0;


    public AI(char pawn) {
        super(pawn);

    }

    public void setGame(Quoridor game)
    {
        this.game = game;
    }
    public void setChromosome(double[] chromosome){
        this.chromosome = chromosome;
    }


    public String getMove() {
        game.moveCounter++;

        var a = Double.NEGATIVE_INFINITY;
        var b = Double.POSITIVE_INFINITY;
        game.simulating = true;
        String[] result = miniMax(4, a , b , true);

        System.out.println(result[1]);
        System.out.println("move: "+ result[0]);
         game.simulating = false;

        return result[0];

    }

    //[0]: move //[1]:score
   public String[] miniMax(int depth ,double alpha , double beta , boolean isMax) {

       Player maximizer = this;
       Player minimizer = null;

       for (var plr: game.players) {
           if(plr.pawn != this.pawn) {
               minimizer = plr;
           }
       }

       Set<String> legalMoves;
        if(isMax) {
            legalMoves = game.findAvailableMoves(maximizer);
        }
        else {
            legalMoves = game.findAvailableMoves(minimizer);
        }



       double bestScore = 0;
       String bestMove = null;

       assert minimizer != null;
       if (depth == 0 || maximizer.x == maximizer.goal
               || minimizer.x == minimizer.goal) {
           bestScore = eval(game);
       }

        else{
          if (isMax) {
              bestScore = alpha;
              for (var possibleMove : legalMoves) {
                  game.makeMove(possibleMove, maximizer);

                  if(possibleMove.startsWith("2"))
                      maximizer.walls++;

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
                  game.makeMove(possibleMove, minimizer);

                  if(possibleMove.startsWith("2"))
                      minimizer.walls++;

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

        double distFromGoal_max = 9 - Math.abs((maximizer.x - maximizer.goal)/2); //--this player's manhattan
        assert minimizer != null;
        double distFromGoal_min =  Math.abs((minimizer.x - minimizer.goal)/2);  //--opponent's manhattan

        double movesToNextRow_max = 0;//for this player
        double movesToNextRow_min = 0; //for opponent

        if(maximizer.goal == 0)
        {
            movesToNextRow_max = 9 - state.getMovesToNextRow(maximizer , "up");
            movesToNextRow_min =  state.getMovesToNextRow(minimizer , "down");
        }
        else if(minimizer.goal == 16)
        {
            movesToNextRow_max = 9 - state.getMovesToNextRow(maximizer , "down");
            movesToNextRow_min =  state.getMovesToNextRow(minimizer , "up");
        }





        return (chromosome[0] * distFromGoal_max) + (chromosome[1] * distFromGoal_min)
               + (chromosome[2] * movesToNextRow_max) + (chromosome[3] * movesToNextRow_min);
    }












}//end class
