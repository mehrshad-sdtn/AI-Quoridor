import java.util.*;

abstract class Game {
    Player[] players;
    Board board;
    boolean gameOver;
    public abstract int winCheck();
    public abstract void play(Player p);

    public abstract void printWinner();
}
//----------------------------------------------------------------------------------------------------------------------

class TwoGame extends Game {

    //constructor---
    public TwoGame(Player p1 , Player p2) {
        this.board = new Board();
        this.players = new Player[2];
        this.players[0] = p1;
        this.players[1] = p2;

        p1.setWalls(10);
        p2.setWalls(10);

        p1.xPawn = 0; p1.yPawn = 4;
        this.board.cells[p1.xPawn][p1.yPawn].content = players[0].pawn;
        p2.xPawn = 8; p2.yPawn = 4;
        this.board.cells[p2.xPawn][p2.yPawn].content = players[1].pawn;

        this.gameOver = false;


    }



    //method to return the winner name & tweak game-over boolean
    public int winCheck(){
        int winner=-1; //-1 means that no one is winning
        for (int i = 0; i < 9; i++) {
            if(this.board.cells[8][i].content==players[0].pawn) {
                winner = 0;
                gameOver=true;
                break;
            }
            else if(this.board.cells[0][i].content==players[1].pawn){
                winner = 1;
                gameOver=true;
                break;
            }
        }
        return winner;

    }


    public void printWinner(){
        System.out.println(players[winCheck()].pawn + " won");
    }


    //-method for playing
    @Override
    public void play(Player p) {

        System.out.println("choose your action [1.move pawn / 2.set wall]");

        Scanner scn = new Scanner(System.in);
        int action = scn.nextInt();

        if(action == 1){
            String dir;
            while(true){
                System.out.println("direction?");
                dir = scn.next();
                if(legalMove(p,dir))
                {
                    movePawn(p,dir);
                    break;
                }else{
                    System.out.println("illegal move , try another direction");
                }

            }

        }
        else if(action == 2){
            System.out.println("vertical or horizontal? [v/h]");
            char type = scn.next().charAt(0);
            System.out.println("where do yo want to place the wall? {enter index of x,y?}");
            int x = scn.nextInt();
            int y = scn.nextInt();
            placeWall(p,x,y,type);
        }



    }

    private boolean legalMove(Player p, String dir) {
        boolean flag = true;
        if(dir.equals("up")){
            if(p.xPawn == 0 || this.board.h_walls[p.xPawn-1][p.yPawn].content == '-'){
                flag = false;
            }

        }else if(dir.equals("down") || this.board.h_walls[p.xPawn][p.yPawn].content == '-'){
            if(p.xPawn == 8){
                flag = false;
            }
        }
        if(dir.equals("left") || this.board.v_walls[p.xPawn][p.yPawn-1].content == '|'){
            if(p.yPawn == 0){
                flag = false;
            }

        }else if(dir.equals("right") || this.board.v_walls[p.xPawn][p.yPawn].content == '|'){
            if(p.yPawn == 8){
                flag = false;
            }
        }
        return flag;
    }


    private void movePawn(Player p, String dir) {

        Board alter = this.board;

        alter.cells[p.xPawn][p.yPawn].content = 'O';

        if (dir.equals("up")){
            p.xPawn--;
        }
        else if (dir.equals("down")){
            p.xPawn++;
        }
        else if (dir.equals("left")){
            p.yPawn--;
        }
        else if (dir.equals("right")){
            p.yPawn++;
        }

        if(alter.cells[p.xPawn][p.yPawn].content != 'O'){
            System.out.println("collision");
            //method to handle collision : todo
        }

        alter.cells[p.xPawn][p.yPawn].content = p.pawn;
        this.board = alter;
    }


    public void placeWall(Player p , int position_x , int position_y  , char type){
        if(legalPosition(position_x , position_y)) {

            if (type == 'v'){
                this.board.v_walls[position_x][position_y].content = '|';
                this.board.v_walls[position_x][position_y].isEmpty = false;
                p.walls--;
            }

            else if (type == 'h'){

                this.board.h_walls[position_x][position_y].content = '-';
                this.board.h_walls[position_x][position_y].isEmpty = false;
                 p.walls--;
            }

        }
    }

    private boolean legalPosition(int x, int y){
        return ((x >= 0) && (y >= 0) && (x < 9) && (y < 9));
    }




}





    //----------------------------------------------------------------------------------------------------------------------
//
//-------------------------------------------------------------
//--------------------------------------four player game
class FourGame extends Game {
    //constructor---
    public FourGame(Player p1, Player p2, Player p3, Player p4) {
        this.board = new Board();
        this.players = new Player[4];
        p1.setWalls(5);
        p2.setWalls(5);
        p3.setWalls(5);
        p4.setWalls(5);

        players[0] = p1;
        players[1] = p2;
        players[2] = p3;
        players[4] = p4;

        shuffleArray(players);

        players[0].xPawn = 0;
        players[0].yPawn = 3;
        this.board.cells[0][3].content = players[0].pawn;

        players[1].xPawn = 3;
        players[1].yPawn = 8;
        this.board.cells[3][8].content = players[1].pawn;

        players[2].xPawn = 8;
        players[2].yPawn = 3;
        this.board.cells[8][3].content = players[2].pawn;

        players[3].xPawn = 3;
        players[3].yPawn = 0;
        this.board.cells[3][0].content = players[3].pawn;
    }

    //methods---
    public int winCheck() {
        int winner = -1;
        for (int i = 0; i < 9; i++) {
            if (this.board.cells[8][i].content == players[0].pawn) {
                winner = 0;
                gameOver = true;
                break;
            } else if (this.board.cells[i][0].content == players[1].pawn) {
                winner = 1;
                gameOver = true;
                break;
            } else if (this.board.cells[0][i].content == players[2].pawn) {
                winner = 2;
                gameOver = true;
                break;
            } else if (this.board.cells[i][8].content == players[3].pawn) {
                winner = 3;
                gameOver = true;
                break;
            }


        }
        return winner;
    }

    @Override
    public void play(Player p) {

    }

    @Override
    public void printWinner() {

    }


    private static void shuffleArray(Player[] array) {
        List<Player> list = Arrays.asList(array);

        Collections.shuffle(list);
        list.toArray(array);

    }
}