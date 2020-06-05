import java.util.*;

public abstract class Player {
    char pawn;
    int x,y;
    int goal;
    int walls;


    public Player(char pawn) {
        this.pawn = pawn;
    }

    public void setWallLimit(int walls) {
        this.walls = walls;
    }

    public void setPosition(int x , int y){
        this.x = x;
        this.y = y;
    }

    //-------------------------------------
    public abstract String getMove();
}
