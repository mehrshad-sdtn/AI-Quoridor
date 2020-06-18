import java.util.InputMismatchException;
import java.util.Scanner;

public class Human extends Player {
    public Human(char pawn) {
        super(pawn);
    }
    //-----
    public String getMove() throws InputMismatchException {
        //gets a move and codes it in string format

        //1:up
        //2:16&5
        Scanner input = new Scanner(System.in);
        System.out.println("1. move pawn \n2. place a wall");
        int op = input.nextInt();
        if (op == 1) {
            System.out.println("direction? [up / down / left / right]");
            String dir = input.next();
            return "1:" + dir;
        } else if (op == 2) {
            System.out.println("wall position? [x & y]");
            int xp = input.nextInt();
            int yp = input.nextInt();
            return "2:" + xp + "&" + yp;

        } else return null;

    }
}
