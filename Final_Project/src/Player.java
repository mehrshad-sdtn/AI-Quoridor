import java.util.*;
public class Player {
    char pawn;
    //---position of pawn char in game board
    int xPawn;
    int yPawn;
    //---------------
    int walls;


    public Player(char pawn) {
        this.pawn = pawn;
    }

    public void setWalls(int walls) {
        this.walls = walls;
    }



}
//---
