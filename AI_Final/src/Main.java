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
            System.out.println("player "+1+" enter yor pawn");
            char ch = input.next().charAt(0);
            Pawn pawn = new Pawn(ch);
            player1.setPawn(pawn);

            //--------player 2 creation
            System.out.println("player "+2+" enter yor pawn");
            ch = input.next().charAt(0);
            pawn = new Pawn(ch);
            player2.setPawn(pawn);
            //-------------------
            TwoGame game = new TwoGame(player1,player2);

            game.displayBoard();
            game.startGame();






        }
        else if (modeNum == 2){

        }

    }
}
