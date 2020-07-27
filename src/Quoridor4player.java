import java.util.*;

public class Quoridor4player extends Quoridor{
    char[][] board;
    Player[] players;
    Player winner;
    Stack<char[][]> memory;
    Queue<Player> turnQueue;


    public Quoridor4player(Player p1, Player p2, Player p3, Player p4) {
        this.board = new char[17][17];
        this.players = new Player[4];
        this.memory = new Stack<>();

        this.turnQueue = new ArrayDeque<>();
        players[0] = p1;/*down*/
        players[1] = p2;/*up*/
        players[2] = p3;/*left*/
        players[3] = p4;/*right*/

        players[0].setPosition(0 , 8);
        players[1].setPosition(16 , 8);
        players[2].setPosition(8 , 0);
        players[3].setPosition(8 , 16);

        players[0].setWallLimit(5);
        players[1].setWallLimit(5);
        players[2].setWallLimit(5);
        players[3].setWallLimit(5);

        Random random = new Random();
        int starter = random.nextInt(4);
        turnQueue.addAll(Arrays.asList(players).subList(starter, players.length));
        turnQueue.addAll(Arrays.asList(players).subList(0, starter));
        makeBoard();





    }

    @Override
    public void  makeBoard(){
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                if(i%2 == 0 && j%2 == 0)
                    board[i][j] = 'o';
                else if(i%2 == 1 && j%2 == 1)
                    board[i][j] = '+';
                else board[i][j] = ' ';
            }
        }
        board[players[0].x][players[0].y] = players[0].pawn;
        board[players[1].x][players[1].y] = players[1].pawn;
        board[players[2].x][players[2].y] = players[2].pawn;
        board[players[3].x][players[3].y] = players[3].pawn;

    }


    @Override
    public void play() {
        String move;
       while(!turnQueue.isEmpty()){
           Player player = turnQueue.poll();
           System.out.println(player.pawn+"'s turn");
           move = player.getMove();
           makeMove(move ,player);
           turnQueue.add(player);
           printBoard();
           if(gameOver){break;}

       }
        System.out.println("player "+winner+" won");
    }

    @Override
    public void makeMove(String move, Player p) {

        if(move == null){move = "1:right";}
        try {
            String[] arr = move.split(":"); // decodes the move

            if (arr[0].equals("1")) {
                String dir = arr[1].trim();
                if (allowed(dir, p)) {
                    possible = true;
                    movePawn(dir, p);
                    checkGameOver(p);


                } else {
                    possible = false;
                    if (!simulating)
                        System.err.println("illegal move");
                }
            } else if (arr[0].equals("2")) {
                String[] str = arr[1].trim().split("&");
                int x = Integer.parseInt(str[0]);
                int y = Integer.parseInt(str[1]);
                if (legalWall(x, y) && p.walls > 0) {
                    possible = true;
                 //   memorize();
                    placeWall(x, y);
                    p.walls--;

                } else {
                    possible = false;
                    if (!simulating)
                        System.err.println("illegal wall");
                }

            }
        }catch (NullPointerException e){
            System.err.println("null move");
        }
    }

    private void checkGameOver(Player p) {
        if(p.pawn == 'A'){
            if(p.x == 16){
                gameOver = true;
                winner = p;
            }
        }
        else if(p.pawn == 'B'){
            if(p.x == 0){
                gameOver = true;
                winner = p;
            }
        }
        else if(p.pawn == 'C'){
            if(p.y == 16){
                gameOver = true;
                winner = p;
            }
        }
        else if(p.pawn == 'D'){
            if(p.y == 0){
                gameOver = true;
                winner = p;
            }
        }
    }


    @Override
    protected boolean allowed(String dir, Player p) {
        boolean flag = false;

        switch (dir) {

            case "up":
                if (inRange(p.x - 2, p.y) && inRange(p.x - 1, p.y)) {
                    if (board[p.x - 1][p.y] == ' ')
                        flag = true;
                }
                break;
            case "down":
                if (inRange(p.x + 2, p.y) && inRange(p.x + 1, p.y)) {
                    if (board[p.x + 1][p.y] == ' ')
                        flag = true;
                }
                break;
            case "left":
                if (inRange(p.x, p.y - 2) && inRange(p.x, p.y - 1)) {
                    if (board[p.x][p.y - 1] == ' ')
                        flag = true;
                }
                break;
            case "right":
                if (inRange(p.x, p.y + 2) && inRange(p.x, p.y + 1)) {
                    if (board[p.x][p.y + 1] == ' ')
                        flag = true;
                }
                break;
        }


        return flag;
    }

    @Override
    protected void movePawn(String dir, Player p) {
        try{
            switch (dir) {
                case "up":
                    board[p.x][p.y] = 'o';
                    //collision handler
                    if(board[p.x - 2][p.y] == 'o')
                    { p.x = p.x - 2;}
                    else {
                        if(board[p.x - 3][p.y] != '#')
                            p.x = p.x - 4;
                        else if(board[p.x - 3][p.y] == '#'){ alternateMove(p,"up"); }
                    }

                    board[p.x][p.y] = p.pawn;
                    break;

                case "down":
                    board[p.x][p.y] = 'o';

                    //collision handler
                    if(board[p.x + 2][p.y] == 'o')
                    { p.x = p.x + 2;}
                    else {
                        if(board[p.x + 3][p.y] != '#')
                            p.x = p.x + 4;
                        else if(board[p.x + 3][p.y] == '#'){ alternateMove(p,"down"); }

                    }

                    board[p.x][p.y] = p.pawn;
                    break;
                case "left":
                    board[p.x][p.y] = 'o';

                    //collision handler
                    if(board[p.x][p.y - 2] == 'o')
                    { p.y = p.y - 2;}
                    else {
                        if(board[p.x][p.y - 3] != '#')
                            p.y = p.y - 4;
                        else if(board[p.x][p.y - 3] == '#'){ alternateMove(p,"left"); }
                    }

                    board[p.x][p.y] = p.pawn;
                    break;
                case "right":
                    board[p.x][p.y] = 'o';

                    if(board[p.x][p.y + 2] == 'o')
                    { p.y = p.y + 2;}
                    else {
                        if(board[p.x][p.y + 3] != '#')
                            p.y = p.y + 4;
                        else if(board[p.x][p.y + 3] == '#'){ alternateMove(p,"right"); }
                    }

                    board[p.x][p.y] = p.pawn;
                    break;
            }
        }catch(ArrayIndexOutOfBoundsException ex){
            if(!simulating)
                System.err.println("move index out of bounds");
        }
    }

    @Override
    protected void placeWall(int x, int y) {
        //vertical
        if(x % 2 == 0 && y % 2 == 1){
            board[x][y] = '#';
            board[x + 2][y] = '#';
        }
        //horizontal
        else if(x % 2 == 1 && y % 2 == 0){
            board[x][y] = '#';
            board[x][y + 2] = '#';
        }
    }

    protected boolean legalWall(int x, int y) {
        boolean flag = false;

        if(x % 2 == 0 && y % 2 == 1){
            if(inRange(x , y) && inRange(x + 2 , y)) {
                if(board[x][y] != '#' && board[x+2][y] != '#') {
                    if(inRange(x , y - 1) && inRange(x , y + 1)) {

                        if (board[x + 1][y - 1] != '#' && board[x + 1][y + 1] != '#')
                            flag = true;
                    }

                }
            }
        }
        else if(x % 2 == 1 && y % 2 == 0){
            if(inRange(x , y) && inRange(x , y + 2)){
                if(board[x][y] != '#' && board[x][y+2] != '#') {
                    if(inRange(x - 1, y) && inRange(x + 1, y )){
                        if (board[x - 1][y + 1] != '#' && board[x + 1][y + 1] != '#')
                            flag = true;
                    }
                }
            }
        }

        if(traps(x , y)){
            flag = false;
        }

        return flag;
    }

    @Override
    boolean traps(int x, int y) {
        boolean flag = false;
        char[][] copy = new char[17][17];
        char[][] temp = new char[17][17];

        for (int i = 0; i < 17; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, 17);
        }
        if(x%2 == 0 && y%2 == 1 && inRange(x+2 , y)) {
            copy[x][y] = '#';
            copy[x + 2][y] = '#';

        }
        else if(x%2 == 1 && y%2 == 0 && inRange(x , y+2)) {
            copy[x][y] = '#';
            copy[x][y + 2] = '#';
        }



        for (var p : players) {
            for (int i = 0; i < 17; i++) {
                System.arraycopy(copy[i], 0, temp[i], 0, 17);
            }

            trapped = true;

            reachGoal(p , temp , p.x , p.y );
            if(trapped)
            { flag =  true;
                break;
            }
        }
        return flag;
    }

    @Override
    public void memorize() {
        super.memorize();
    }

    public void reachGoal(Player p , char[][] arr , int x , int y) {

        try{

            if (x == p.goal) {
                trapped = false;

            } else {
                arr[x][y] = 'x';
                if (inRange(x - 2, y)) {
                    if (arr[x - 2][y] != 'x' && arr[x - 1][y] != '#')
                        reachGoal(p, arr, x - 2, y);

                }
                if (inRange(x + 2, y)) {
                    if (arr[x + 2][y] != 'x' && arr[x + 1][y] != '#')
                        reachGoal(p, arr, x + 2, y);

                }
                if (inRange(x, y - 2)) {
                    if (arr[x][y - 2] != 'x' && arr[x][y - 1] != '#')
                        reachGoal(p, arr, x, y - 2);

                }
                if (inRange(x, y + 2)) {
                    if (arr[x][y + 2] != 'x' && arr[x][y + 1] != '#')
                        reachGoal(p, arr, x, y + 2);
                }
            }
        }catch(ArrayIndexOutOfBoundsException e){
            if(!simulating)
            {e.printStackTrace();}
        }

    }

    @Override
    public void printBoard() {
        System.out.print(" ");
        for (int i = 0; i < 17; i++) {
            if(i<10)
                System.out.print("   "+i);
            else
                System.out.print("  "+i);
        }
        System.out.println();

        for (int i = 0; i < 17; i++) {
            if(i<10)
                System.out.print(i+" | ");
            else
                System.out.print(i+"| ");
            for (int j = 0; j < 17; j++) {
                System.out.print(board[i][j]+"   ");
            }
            System.out.println("|");
        }
        System.out.print(" ");
        for (int i = 0; i < 17; i++) {
            if(i<10)
                System.out.print("   "+i);
            else
                System.out.print("  "+i);
        }
        System.out.println();
    }







}
