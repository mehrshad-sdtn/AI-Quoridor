import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("enter number of players: 2 or 4");
        int num = input.nextInt();

        Player p1 = new Player('A');
        Player p2 = new Player('B');

        if(num == 2){
            Game game = new TwoGame(p1,p2);
            game.board.printBoard();

            //---assuming player one starts
           String temp = null;

            while(!game.gameOver){
                System.out.println("A turn");
                game.play(p1);

                System.out.println("B turn");
                game.play(p2);
                temp = game.winCheck();
            }
            System.out.println("player"+ temp +" won");

        }


        else{
            Player p3 = new Player('C');
            Player p4 = new Player('D');

        }




    }
}
