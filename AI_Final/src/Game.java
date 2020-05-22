import java.util.*;

abstract class Game {
    static Player[] players;
    Pawn[] pawns;
    Queue<Player> gameTurn;
    boolean gameOver;
    State state; //---->current stack
    static Stack<State> memory; //-->keeping past states in the stack
    Player winner;

    public Game() {
        memory = new Stack<>();
        this.state = new State();
        this.gameOver = false;
        this.gameTurn = new LinkedList<>();
    }

    public void startGame() {
        randomAdd();

        while(!gameOver && !gameTurn.isEmpty()) {

            Player p = gameTurn.poll(); ///----------->poll the front player of the queue
            memory.push(state); ///------> push the current state into the memory
            p.play(this.state); //------->change the current state by playing
            if(winCheck())
            {
                winner = p;
                gameOver = true;
            }
            gameTurn.add(p);  //-------->add the player who just played to the end of queue
            displayBoard();
        }
        endGame();


    }

    //----->
    protected void endGame(){
        System.out.println("game finished");
        System.out.println("player "+winner.symbol+" won");
    }

    //---->this method randomly chooses one player to start and adds the rest (clock-wise) to the queue
    private void randomAdd() {
        Random rand = new Random();
        int toStart = rand.nextInt(2);
        int temp = toStart;

        this.gameTurn.add(players[toStart]);
        toStart++;

        while(toStart < players.length){
            gameTurn.add(players[toStart]);
            toStart++;
        }
        gameTurn.addAll(Arrays.asList(players).subList(0, temp));
    }

    //------>
    public boolean winCheck(){
        boolean flag = false;
        for (Player p : players) {
            //---->wrap in a method later to make the code clean
            if(p.goalType.equals("row")){
                for (int i = 0; i < 17 ; i++) {
                    //--->check if the player's pawn is in the player's goal location
                    if(state.board[p.goal][i].symbol == p.symbol) {
                        System.out.println(p.pawn.symbol);
                        flag = true;
                        break;
                    }
                }

            }
        }

        return flag;
    }



    public void displayBoard(){

        for (int i = 0; i < 17 ; i++) {
            System.out.print("##");
        }
        System.out.println();

        for (int i = 0; i < 17 ; i++) {
            for (int j = 0; j < 17; j++) {
                System.out.print(state.board[i][j].symbol+" ");
            }
            System.out.println();
        }

        for (int i = 0; i < 17 ; i++) {
            System.out.print("##");
        }
        System.out.println();

    }







}