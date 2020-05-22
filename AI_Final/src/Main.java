import java.util.*;
public class Main {
    /*----
    note:
   in board array:
   [odd][even] --> horizontal wall place
   [even][odd] --> vertical wall place
   [even][even] --> pawn place
   [odd][odd] --> nothing (+)

    ----- */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("welcome to the quoridor");
        System.out.println("select mode: \n1. 2-player game \n2. 4-player game");
        int modeNum = input.nextInt();


        if(modeNum == 1){
            Player player1 = new Human();
            Player player2 = new Human();
            //-------player 1 creation
            Pawn pawn;
            pawn = new Pawn('R');
            player1.setPawn(pawn);

            //--------player 2 creation
            pawn = new Pawn('Y');
            player2.setPawn(pawn);
            //-------------------
            TwoGame game = new TwoGame(player1,player2);

            game.displayBoard();
            game.startGame();







        }
        else if (modeNum == 2){

            Player player1 = new Human();
            Player player2 = new Human();
            Player player3 = new Human();
            Player player4 = new Human();

            Pawn pawn;
            pawn = new Pawn('R');
            player1.setPawn(pawn);

            pawn = new Pawn('Y');
            player2.setPawn(pawn);

            pawn = new Pawn('G');
            player3.setPawn(pawn);

            pawn = new Pawn('B');
            player4.setPawn(pawn);
            //-------------------
            FourGame game = new FourGame(player1, player2, player3, player4);

            game.displayBoard();
            game.startGame();
        }

    }
}
