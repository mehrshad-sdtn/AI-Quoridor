import java.util.*;

public class Quoridor{
    char[][] board;
    Player[] players;
    boolean gameOver;
    Player winner;
    Stack<char[][]> memory;
    boolean trapped;
    boolean possible;
    boolean simulating;
    int moveCap;
    int moveCounter = 0;
    boolean tieGame = false;
    boolean inTraining = false;


    public Quoridor(Player p1 , Player p2) {
        this.board = new char[17][17];
        this.players = new Player[2];
        this.memory = new Stack<>();

        this.gameOver = false;

        players[0] = p1;
        players[1] = p2;
        players[0].setPosition(0 , 8);
        players[0].goal = 16;
        players[1].setPosition(16 , 8);
        players[1].goal = 0;
        makeBoard();

        players[0].setWallLimit(10);
        players[1].setWallLimit(10);
        players[0].turn = true;
        players[1].turn = false;

    }

    public Quoridor() {
    }
    //----------------------------------------

    public void setMoveCap(int moveCap) {
        this.moveCap = moveCap;
    }

    //---------------------------------------
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

    }
    //------------------------------------------
    public void printBoard(){
        //--some dirty code to style the board
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

    //-------------------------method for playing the game
    public void play(){
        String move;

        while(!gameOver){

            //----puts a limit to the number of moves for ai vs ai
            if(moveCounter >= moveCap){
                System.out.println("move limit reached");
                tieGame = true;
                break;
            }

            System.out.println(players[0].pawn+"'s turn , "+players[0].walls+" left");
            try{

                if(players[0].turn){
                    move = players[0].getMove();
                    makeMove(move , players[0]);
                  // System.out.println("---->"+getMovesToNextRow(players[0], "down"));
                    players[1].turn = true;
                    players[0].turn = false;
                }




            }catch(InputMismatchException ex){
                System.err.println("wrong input");
            }

            //win check
            if(gameOver){
                break;
            }
            printBoard();

            System.out.println(players[1].pawn+"'s turn , "+players[1].walls+" left");
            try{
                if(players[1].turn){
                   move = players[1].getMove();
                   makeMove(move , players[1]);
               //  System.out.println("---->"+getMovesToNextRow(players[1], "up"));
                    players[1].turn = false;
                    players[0].turn = true;
                }


            }catch(InputMismatchException ex){
                System.err.println("wrong input");
            }

            //win check
           if(gameOver){
               break;
           }
            printBoard();

        }
        System.out.println("final board state:");
        printBoard();//final board state
        if(winner != null)
        System.out.println("player "+winner.pawn+" won");
        else
            System.out.println("tie game");

    }
    ///-------------------------------------------------
    public void makeMove(String move , Player p){
        if(move == null){move = "1:right";}
        try {
            String[] arr = move.split(":"); // decodes the move

            if (arr[0].equals("1")) {
                String dir = arr[1].trim();
                if (allowed(dir, p)) {
                    possible = true;
                    memorize();
                    movePawn(dir, p);

                    if (p.x == p.goal && !simulating) {
                        gameOver = true;
                        winner = p;
                    }


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
                    memorize();
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


    //-----------------------takes care of the wall----------
    private void placeWall(int x, int y) {
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

    private boolean legalWall(int x, int y) {
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



    private boolean traps(int x , int y) {
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

   //------------------------------------------------------
    private void movePawn(String dir, Player p) {
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


    //choosing alternative move for collision with wall behind
    private void alternateMove(Player p , String dir) {
        Scanner input = new Scanner(System.in);

        if(!simulating && !inTraining){
            String alt;
           if(dir.equals("up")){
            System.out.println("the move is unavailable select an alternate [upleft / upright]");
           alt = input.next();
            if(alt.equals("upleft")){
                board[p.x][p.y] = 'o';
                p.x = p.x - 2;
                p.y = p.y - 2;
                board[p.x][p.y] = p.pawn;
            }
            else if(alt.equals("upright")){
                board[p.x][p.y] = 'o';
                p.x = p.x - 2;
                p.y = p.y + 2;
                board[p.x][p.y] = p.pawn;
            }

        }
           else if(dir.equals("down")){
            System.out.println("the move is unavailable select an alternate [downleft / downright]");
            alt = input.next();
            if(alt.equals("downleft")){
                board[p.x][p.y] = 'o';
                p.x = p.x + 2;
                p.y = p.y - 2;
                board[p.x][p.y] = p.pawn;
            }
            else if(alt.equals("downright")){
                board[p.x][p.y] = 'o';
                p.x = p.x + 2;
                p.y = p.y + 2;
                board[p.x][p.y] = p.pawn;
            }
        }
           else if(dir.equals("left")){
            System.out.println("the move is unavailable select an alternate [leftup/leftdown]");
            alt = input.next();
            if(alt.equals("leftup")){
                board[p.x][p.y] = 'o';
                p.x = p.x - 2;
                p.y = p.y - 2;
                board[p.x][p.y] = p.pawn;
            }
            else if(alt.equals("leftdown")){
                board[p.x][p.y] = 'o';
                p.x = p.x + 2;
                p.y = p.y - 2;
                board[p.x][p.y] = p.pawn;
            }
        }
           else if(dir.equals("right")){
            System.out.println("the move is unavailable select an alternate [rightup/rightdown]");
            alt = input.next();
            if(alt.equals("rightup")){
                board[p.x][p.y] = 'o';
                p.x = p.x - 2;
                p.y = p.y + 2;
                board[p.x][p.y] = p.pawn;
            }
            else if(alt.equals("rightdown")){
                board[p.x][p.y] = 'o';
                p.x = p.x + 2;
                p.y = p.y + 2;
                board[p.x][p.y] = p.pawn;
            }
        }
        }
        else{
            Random rnd = new Random();
            int choice = rnd.nextInt(2);
            if(dir.equals("up")){
                if(choice == 0){
                    board[p.x][p.y] = 'o';
                    p.x = p.x - 2;
                    p.y = p.y - 2;
                    board[p.x][p.y] = p.pawn;
                }
                else {
                    board[p.x][p.y] = 'o';
                    p.x = p.x - 2;
                    p.y = p.y + 2;
                    board[p.x][p.y] = p.pawn;
                }

            }
            else if(dir.equals("down")){
                if(choice == 0){
                    board[p.x][p.y] = 'o';
                    p.x = p.x + 2;
                    p.y = p.y - 2;
                    board[p.x][p.y] = p.pawn;
                }
                else {
                    board[p.x][p.y] = 'o';
                    p.x = p.x + 2;
                    p.y = p.y + 2;
                    board[p.x][p.y] = p.pawn;
                }
            }
            else if(dir.equals("left")){
                if(choice == 0){
                    board[p.x][p.y] = 'o';
                    p.x = p.x - 2;
                    p.y = p.y - 2;
                    board[p.x][p.y] = p.pawn;
                }
                else{
                    board[p.x][p.y] = 'o';
                    p.x = p.x + 2;
                    p.y = p.y - 2;
                    board[p.x][p.y] = p.pawn;
                }
            }
            else if(dir.equals("right")){
                if(choice == 0){
                    board[p.x][p.y] = 'o';
                    p.x = p.x - 2;
                    p.y = p.y + 2;
                    board[p.x][p.y] = p.pawn;
                }
                else{
                    board[p.x][p.y] = 'o';
                    p.x = p.x + 2;
                    p.y = p.y + 2;
                    board[p.x][p.y] = p.pawn;
                }

            }
        }
    }
    //------------------------------------------------------


    private boolean allowed(String dir, Player p) {
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

    //------------------------------------------------

    public void memorize(){
        char[][] copy  = new char[17][17];
        for (int i = 0; i < 17; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, 17);
        }
        memory.push(copy);
    }
//undo
    public void undo(){

        if(!memory.isEmpty()){
        char[][] top = memory.pop();
        for (int i = 0; i <17 ; i++) {
            for (int j = 0; j <17 ; j++) {
                if(top[i][j] == players[0].pawn){
                    players[0].x = i;
                    players[0].y = j;
                    break;
                }
            }
        }
        for (int i = 0; i <17 ; i++) {
            for (int j = 0; j <17 ; j++) {
                if(top[i][j] == players[1].pawn){
                    players[1].x = i;
                    players[1].y = j;
                    break;
                }
            }
        }
        for (int i = 0; i < 17; i++) {
            System.arraycopy(top[i], 0, board[i], 0, 17);
          }

        }//outer condition brace

    }


    public Set<String> findAvailableMoves(Player player){

        StringBuilder move = null;
        Set<String> availableMoves = new TreeSet<>();

        //----pawn
        move = new StringBuilder("1:");

        for (int dir = 0; dir < 4; dir++) {
            if (dir == 0) move.append("up");
            else if (dir == 1) move.append("down");
            else if (dir == 2) move.append("right");
            else move.append("left");

            makeMove(move.toString(), player);
            if (possible) {
               availableMoves.add(move.toString());
                undo();
            }
            move = new StringBuilder("1:");
        }
            //-----------wall

            move = new StringBuilder("2:");
            for (int i = 1; i < 16; i++) {
                for (int j = 0; j < 16; j++) {

                    if((i%2 == 0 && j%2 == 1)||(i%2 == 1 && j%2 == 0)){
                        move.append(i).append("&").append(j);
                        makeMove(move.toString() , player);
                        if(possible){
                            availableMoves.add(move.toString());
                            undo();
                            player.walls++;
                        }
                        move = new StringBuilder("2:");
                    }
                }
            }

            filter(availableMoves , player);

            return availableMoves;

    }


    private void filter(Set<String> set , Player player) {
        Player other = null;
        for (var element : players) {
            if(element.pawn!=player.pawn)
            {other = element; break;}
        }

        Iterator<String> itr = set.iterator();


        //System.out.println("before"+set.size());
           while(itr.hasNext()) {
               String value = itr.next();

               //--removes states where a wall is placed behind the opponent
               if(player.goal == 0){
                  if(value.startsWith("2") && Integer.parseInt(value.split(":")[1].split("&")[0]) < other.x)
                    {

                        itr.remove();

                    }


               }
               if(player.goal == 16){
                   if(value.startsWith("2") && Integer.parseInt(value.split(":")[1].split("&")[0]) > other.x)
                   {
                       itr.remove();

                   }

               }


           }

           //---removes states where wall is placed beside the player
        itr = set.iterator();
        while(itr.hasNext()) {
            String value = itr.next();
            if(value.startsWith("2") && Integer.parseInt(value.split(":")[1].split("&")[1]) % 2 == 1 && Integer.parseInt(value.split(":")[1].split("&")[1]) == player.y + 1 ||
                    value.startsWith("2") && Integer.parseInt(value.split(":")[1].split("&")[1]) % 2 == 1 && Integer.parseInt(value.split(":")[1].split("&")[1]) == player.y - 1)
            {

                itr.remove();

            }
        }

        //removes wall which are far from opponent
        itr = set.iterator();
        while(itr.hasNext()) {
            String value = itr.next();
            if(value.startsWith("2") && Math.abs(Integer.parseInt(value.split(":")[1].split("&")[1]) - other.y) > 3
                    || value.startsWith("2") && Math.abs(Integer.parseInt(value.split(":")[1].split("&")[0]) - other.x) > 4 )
            {
                itr.remove();

            }
        }

        itr = set.iterator();
        while(itr.hasNext()) {
            String value = itr.next();
            if(player.goal == 0)
            {
                if(value.startsWith("2") && Integer.parseInt(value.split(":")[1].split("&")[0]) % 2 == 1 &&
                        Integer.parseInt(value.split(":")[1].split("&")[0]) < player.x)
                {itr.remove();}

            }
        }

        itr = set.iterator();
        while(itr.hasNext()) {
            String value = itr.next();
            if(player.goal == 16)
            {
                if(value.startsWith("2") && Integer.parseInt(value.split(":")[1].split("&")[0]) % 2 == 1 &&
                        Integer.parseInt(value.split(":")[1].split("&")[0]) > player.x)
                {itr.remove();}

            }
        }


        //-----



        //System.out.println("after"+set.size());


    }


    public static boolean inRange(int x,int y){
        return (x>=0 && y>=0) && (x<17 && y<17);
    }

    //---for the downside player
    public int getMovesToNextRow(Player player , String dir) {
        int x = player.x;
        int y = player.y;
        int left = 0;
        int right = 0;

        int dif = 0;
        if(dir.equals("up")){
             dif = -1;
        }
        else if(dir.equals("down")){
             dif = +1;
        }

            while(inRange(x + dif , y)){

              if(board[x + dif][y] != '#'){
                break;
              }
              else if(inRange(x , y - 2)){
                  if(board[x][y - 1] != '#')
                  {
                      //if doesn't hit the wall
                      left++ ;  y = y - 2 ;
                  }
                  else { left = 20; break; }
              }
              else if (!inRange(x , y - 2)){
                  //hits the borders
                  left = 20;
                  break;
              }

            }
            x = player.x;
            y = player.y;

            while (inRange(x + dif , y)){
                if(board[x + dif][y] != '#'){
                    break;
                }
                else if(inRange(x , y + 2)){
                    if(board[x][y + 1] != '#')
                    {
                        //if doesn't hit the wall
                        right++ ;  y = y + 2 ;
                    }
                    else { right = 20; break; }
                }
                else if (!inRange(x , y + 2)){
                    //hits the borders
                    right = 20;
                    break;
                }
            }


        return Math.min(left , right);

    }



}
