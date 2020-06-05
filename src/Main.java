import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("WELCOME TO THE QUORIDOR");
        System.out.println("select mode: \n1. human vs human \n2. human vs AI \n3. human vs random player");
        Scanner input = new Scanner(System.in);
        int mode = input.nextInt();

        if(mode == 1){
            Player p1 = new Human('A');
            Player p2 = new Human('B');
            Quoridor game = new Quoridor(p1 , p2);
            game.printBoard();
            game.play();

        }

        else if(mode == 2){
            System.out.println("AI mode");
        }

        else if(mode == 3){
            Player p1 = new Human('A');
            Player p2 = new RandomPlayer('B');
            Quoridor game = new Quoridor(p1 , p2);
            game.printBoard();
            game.play();
        }


    }
}
