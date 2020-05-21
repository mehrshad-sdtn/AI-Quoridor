abstract class Player {
    public Pawn pawn;
    public int goal; //-->index of the goal
    public String goalType; //-->row or column
    char symbol;
    int pos_x;
    int pos_y;

    public Player() {
    }

    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
        this.symbol = pawn.symbol;
    }

    public abstract void play(State state);











}
