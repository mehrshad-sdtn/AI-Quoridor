public class State {
    GameObject[][] board;

    public State() {
        this.board = new GameObject[17][17];
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                board[i][j] = new GameObject();

            }
        }

    }

    public void initBoard(){
        for (int i = 0; i < 17 ; i++) {
            for (int j = 0; j < 17 ; j++) {
                board[i][j] = new GameObject();
                if(i%2 == 0 && j%2 == 0){
                    board[i][j].setSymbol('o');
                }
                else if(i%2 == 1 && j%2 == 1){
                    board[i][j].setSymbol('+');
                }
                else board[i][j].setSymbol(' ');
            }
        }
    }
}
