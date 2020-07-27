import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("WELCOME TO THE QUORIDOR");
        System.out.println("select mode: \n1. human vs human \n2. human vs AI \n3. human vs random player \n" +
                "4. 4 player game (all human)");
        Scanner input = new Scanner(System.in);
        int mode = input.nextInt();

        if(mode == 1){
            Player p1 , p2;
            if(randomStart() == 1){
                p1 = new Human('A');
                p2 = new Human('B');

            }
            else{
                p2 = new Human('A');
                p1 = new Human('B');

            }
            Quoridor game = new Quoridor(p1 , p2);
            game.setMoveCap(1000);
            game.printBoard();
            game.play();

        }

        else if(mode == 2){
            Player p1 , p2;
            AI ai;
            if(randomStart() == 1){
                p1 = new Human('A');
                p2 = new AI('B');
                ai = (AI)p2;
            }
            else{
                p2 = new Human('A');
                p1 = new AI('B');
                ai = (AI)p1;
            }


            //setting the ai player's chromosome
            double[] c = {75.0, 65.0, 66.0, 39.0};
            ai.setChromosome(c);
            Quoridor game = new Quoridor(p1 , p2);
            ai.setGame(game);
            game.setMoveCap(1000);
            game.printBoard();
            game.play();

        }

        else if(mode == 3){
            Player p1 = new Human('A');
            Player p2 = new RandomPlayer('B');
            Quoridor game = new Quoridor(p1 , p2);
            game.setMoveCap(1000);
            game.printBoard();
            game.play();
        }
        else if(mode == 4){
            Player p1 = new Human('A');
            Player p2 = new Human('B');
            Player p3 = new Human('C');
            Player p4 = new Human('D');
            Quoridor4player game = new Quoridor4player(p1 , p2 , p3 , p4);
            game.printBoard();
            game.play();

        }
        else if(mode == 5){
            Player p1 = new RandomPlayer('A');
            Player p2 = new RandomPlayer('B');
            Quoridor game = new Quoridor(p1 , p2);
            game.printBoard();
            game.play();
        }
        else{
            AI p1 = new AI('A');
            AI p2 = new AI('B');
            double[] c = {10 , 8 , 5 , 10};
            p1.setChromosome(c);
            p2.setChromosome(c);
            Quoridor game = new Quoridor(p1 , p2);
            game.setMoveCap(1000);
            p2.setGame(game);
            p1.setGame(game);
            game.printBoard();
            game.play();
        }


    }

    private static int randomStart() {
        Random rand = new Random();
        return rand.nextInt(2)+1;
    }
}
