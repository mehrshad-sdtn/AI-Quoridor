
class Board {
     Cell[][] cells;
     Wall[][] h_walls;
     Wall[][] v_walls;


     public Board() {
        this.cells = new Cell[9][9];
         for (int i = 0; i < this.cells.length; i++) {
             for (int j = 0; j < this.cells[i].length; j++)
                 this.cells[i][j] = new Cell();
         }

    }
    ///----------------

    ///----------------
    public void printBoard(){
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[i].length; j++)
            System.out.print(this.cells[i][j].content+"  ");
            System.out.println();
        }

    }


    ///----------------


}
//-------
class Wall{}
//------
class Cell{
    char content;
    boolean isEmpty;

    public Cell() {
        this.isEmpty = true;
        content = 'O';
    }
}



