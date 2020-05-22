abstract class Player {
    public Pawn pawn;
    public int goal; //-->index of the goal
    public String goalType; //-->row or column
    char symbol;
    int max_pawns;
    int pos_x;
    int pos_y;


    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
        this.symbol = pawn.symbol;
    }

    public void setMax_pawns(int max_pawns) {
        this.max_pawns = max_pawns;
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

        try{
            //----> horizontal wall
            if ((x % 2 == 1 && y % 2 == 0) && inBounds(state, x, y) && inBounds(state, x, y + 2)) {
                GameObject h_wall = new Wall();
                h_wall.setSymbol('#');
                if(state.board[x][y].symbol == ' '){
                    state.board[x][y] = h_wall;
                    state.board[x][y + 2] = h_wall;
                    this.max_pawns--;
                }else{
                    System.out.println("a wall already exists");
                }
            }
            //----->vertical wall
            else if ((x % 2 == 0 && y % 2 == 1) && inBounds(state, x, y) && inBounds(state, x + 2, y )) {
                GameObject v_wall = new Wall();
                v_wall.setSymbol('|');
                if(state.board[x][y].symbol == ' '){
                    state.board[x][y] = v_wall;
                    state.board[x + 2][y] = v_wall;
                    this.max_pawns--;
                }
                else{
                    System.out.println("a wall already exists");
                }
            }
        }catch(ArrayIndexOutOfBoundsException ex)
        { System.out.println("out of bounds error");}






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



}
