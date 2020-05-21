class GameObject {
    char symbol;

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }
}


class Pawn extends GameObject {


    public Pawn(char symbol) {
        this.symbol = symbol;
    }
}

class Wall extends GameObject{


}
