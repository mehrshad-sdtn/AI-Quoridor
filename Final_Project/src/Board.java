class Board {
     Cell[][] cells;
     Wall[][] h_walls;
     Wall[][] v_walls;


     public Board() {
        this.cells = new Cell[9][9];
        this.v_walls = new Wall[9][8];
        this.h_walls = new Wall[8][9];

         for (int i = 0; i < this.cells.length; i++) {
             for (int j = 0; j < this.cells[i].length; j++)
                 this.cells[i][j] = new Cell();
         }

         for (int i = 0; i < this.v_walls.length; i++) {
             for (int j = 0; j < this.v_walls[i].length; j++){
                 this.v_walls[i][j] = new Wall(true);
             }
         }

         for (int i = 0; i < this.h_walls.length; i++) {
             for (int j = 0; j < this.h_walls[i].length; j++){
                 this.h_walls[i][j] = new Wall(false);
             }
         }

    }
    ///----------------

    ///----------------
    public void printBoard(){
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[i].length; j++){

               System.out.print(this.cells[i][j].content+" ");
               if(j != 8)
               System.out.print(this.v_walls[i][j].content);



            }
            System.out.println();
            int r = 0;
            for (int k = 0; k < h_walls[0].length ; k++) {
                System.out.print(h_walls[r][k].content+" ");
            }
            r++;
            System.out.println();
        }
        //


    }



}

class Cell{
    char content;
    boolean isEmpty;

    public Cell() {
        this.isEmpty = true;
        content = 'O';
    }
}

///----------------------
class Wall{
    char content;
    boolean isEmpty;
    boolean isVertical;

    public Wall(boolean isVertical) {
        this.isEmpty = true;
        this.content = ' ';
        this.isVertical = isVertical;

    }
}





