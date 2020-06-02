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

    public State(char[][] array) {
        this.board = new GameObject[17][17];
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                board[i][j] = new GameObject();
                board[i][j].symbol = array[i][j];

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

   public char[][] extractArray(){
        char[][] array = new char[17][17];
       for (int i = 0; i < 17; i++) {
           for (int j = 0; j < 17; j++) {
               array[i][j] = this.board[i][j].symbol;
           }
       }
       return array;
   }


   public void arrayCopy(char[][] copyTo){
       for (int i = 0; i < 17; i++) {
           for (int j = 0; j < 17; j++) {
               copyTo[i][j] = this.board[i][j].symbol;
           }
       }
   }

}//end class
