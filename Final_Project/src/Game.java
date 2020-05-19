abstract class Game {
   Player[] players;
    Board board;
}
//---------------------------
class TwoGame extends Game {
    public TwoGame(Player p1 , Player p2) {
        this.board = new Board();
        this.players = new Player[2];
        this.players[0] = p1;
        this.players[1] = p2;
        this.board.cells[0][4].content = players[0].pawn;
        this.board.cells[8][4].content = players[1].pawn;
    }




}




//----------------------------
class FourGame extends Game{

}
