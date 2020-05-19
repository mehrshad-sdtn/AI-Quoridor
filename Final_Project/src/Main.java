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

        }


        else{
            Player p3 = new Player('C');
            Player p4 = new Player('D');
            Game game = new FourGame();
        }




    }
}
