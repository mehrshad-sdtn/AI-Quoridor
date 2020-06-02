abstract class Player {
    public Pawn pawn;
    public int goal; //-->index of the goal
    public String goalType; //-->row or column
    char symbol;
    int max_walls;
    int pos_x;
    int pos_y;
    static boolean trapHandler;


    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
        this.symbol = pawn.symbol;
    }

    public void setMax_walls(int max_walls) {
        this.max_walls = max_walls;
    }

    public abstract void play(State state);

    //--------------------------------------->checks if a wall is faced or not
    protected boolean allowedMove(State state, String dir) {
        boolean flag = true;
        //--
        int temp_x  = pos_x;
        int temp_y  = pos_y;


        switch (dir) {
            case "up":
                temp_x--;
                break;

            case "down":
                temp_x++;
                break;

            case "left":
                temp_y--;
                break;

            case "right":
                temp_y++;
                break;
        }
        if(inBounds(state , temp_x , temp_y)){
            if (state.board[temp_x][temp_y].symbol != ' ') {
                flag = false;
            }
        }


        return flag;
    }

    ///----------->method to place the wall at index [x][y]
    protected void placeWall(State state, int x, int y) {

        try {

            //----> horizontal wall
            if ((x % 2 == 1 && y % 2 == 0) && inBounds(state, x, y) && inBounds(state, x, y + 2)) {
                GameObject h_wall = new Wall();
                h_wall.setSymbol('#');

                if (state.board[x][y].symbol == ' ') {
                    if(legalWall(state , x , y)) {
                        state.board[x][y] = h_wall;
                        state.board[x][y + 2] = h_wall;
                        this.max_walls--;
                    }else{
                        System.err.println("ILLEGAL WALL");
                    }

                } else {
                    System.out.println("a wall already exists");
                }
            }
            //----->vertical wall
            else if ((x % 2 == 0 && y % 2 == 1) && inBounds(state, x, y) && inBounds(state, x + 2, y)) {
                GameObject v_wall = new Wall();
                v_wall.setSymbol('|');
                if (state.board[x][y].symbol == ' ') {
                    if(legalWall(state , x , y)) {
                       state.board[x][y] = v_wall;
                       state.board[x + 2][y] = v_wall;
                       this.max_walls--;
                    }else{
                        System.err.println("ILLEGAL WALL");
                    }

                } else {
                    System.out.println("a wall already exists");
                }
            }

        }catch(ArrayIndexOutOfBoundsException ex)
        { System.out.println("out of bounds error");}






    }

    private boolean legalWall(State state, int x, int y) {
        char[][] temp = new char[17][17];
        boolean flag = true;

       state.arrayCopy(temp);
        if(x%2 == 0 && y%2 == 1) {
            temp[x][y] = '|';
            temp[x + 2][y] = '|';

        }
        else if(x%2 == 1 && y%2 == 0) {
            temp[x][y] = '#';
            temp[x][y + 2] = '#';
        }
        char[][] backup = new char[17][17];
        for (int i = 0; i < 17 ; i++) {
            System.arraycopy(temp[i], 0, backup[i], 0, 17);
        }

        for (var p: Game.players) {

            for (int i = 0; i < 17 ; i++) {
                System.arraycopy(backup[i], 0, temp[i], 0, 17);
            }
            trapHandler = false;
            canReachGoal(p, temp , p.pos_x , p.pos_y);
           if(!trapHandler)
            { flag =  false; break;}
        }

        return flag;

    }
    public void canReachGoal(Player p, char[][] arr, int x, int y){


        if(p.goalType.equals("row")){

            if(x == p.goal){

               trapHandler =  true;

            }else{
                //-----down
                arr[x][y] = 'x';
                if(inRange(x + 2 , y)){

                    if(arr[x+2][y]!= 'x'  && arr[x+1][y] != '#'){
                        canReachGoal(p, arr , x+2 , y);
                    }
                }
              //----up
                if(inRange(x - 2 , y)){

                    if(arr[x-2][y]!= 'x'  && arr[x-1][y] != '#'){
                     canReachGoal(p, arr , x-2 , y);
                    }
                }

                //----right
                if(inRange(x , y + 2)){

                    if(arr[x][y+2]!= 'x'  && arr[x][y+1] != '|'){
                        canReachGoal(p, arr , x , y+2);
                    }
                }

                //--left
                if(inRange(x , y - 2)){

                    if(arr[x][y-2]!= 'x'  && arr[x][y-1] != '|'){
                        canReachGoal(p, arr , x , y-2);
                    }
                }



            }


        }///row goal



    }





    //----------------------------------------------------
    protected boolean inBounds(State state, int x, int y) {
        return ((0 <= x) && (x <= 16)) && ((0 <= y) && (y <= 16));
    }

    //----------------------------------------------------------
    protected void movePawn(State state, String dir) {

        int prev_x = pos_x;
        int prev_y = pos_y;

        changePosition(dir);

        if(inBounds(state , pos_x , pos_y)){
            if(state.board[pos_x][pos_y].symbol != 'o'){
                System.out.println("collision");

                changePosition(dir);
                //-->this part takes care of the condition where there is a wall behind the collision player
                if(dir.equals("right" ) && state.board[pos_x][pos_y-1].symbol != ' '){
                    pos_x+=2;
                    pos_y-=2;
                }
                else if(dir.equals("left" ) && state.board[pos_x][pos_y+1].symbol != ' '){
                    pos_x+=2;
                    pos_y+=2;
                }
                else if(dir.equals("up" ) && state.board[pos_x+1][pos_y].symbol != ' '){
                    pos_x+=2;
                    pos_y+=2;
                }
                else if(dir.equals("down" ) && state.board[pos_x-1][pos_y].symbol != ' '){
                    pos_x-=2;
                    pos_y+=2;
                }


            }


            state.board[pos_x][pos_y].setSymbol(this.symbol);
            state.board[prev_x][prev_y].setSymbol('o');


        }
        else {
            System.out.println("out of bounds");
            pos_x = prev_x;
            pos_y = prev_y;

        }


    }



    //------------------------------->method for changing the indexes of current pawn position
    protected void changePosition(String dir) {
        switch (dir) {
            case "up":
                this.pos_x-= 2;
                break;

            case "down":
                this.pos_x+= 2;
                break;

            case "left":
                this.pos_y-= 2;
                break;

            case "right":
                this.pos_y+= 2;
                break;
        }
    }
    public boolean inRange(int x,int y){
        return (x>=0 && y>=0) && (x<17 && y<17);
    }



}
