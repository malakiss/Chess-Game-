package chesscore;




public abstract class Piece {
    
     private Color color;
     private PieceType type;
     private PlayerType playertype;

    public Piece(Color color,PieceType type){
        this.type=type;
        this.color=color;
        setPlayertype(color);
    }

    public void setPlayertype(Color color) {
       for (PlayerType playerType : PlayerType.values()) {
            if (playerType.getColor() == color) {
                this.playertype=playerType;
            }
       }
    }
    
    public PieceType getType()
     {
         return this.type;
     }
    public  PlayerType getPlayerType(){
       this.playertype=PlayerType.getPlayerTypeByColor(getColor());
       return this.playertype;
    }
    
    public Color getColor() {
        return color;
    }
    public String toString(){
    	return color.toString()+type.toString();
    } 
    public abstract boolean isValidMove( Coordinate sourceSquare, Coordinate destinationSquare);

    public abstract Coordinate[] getRoute(Coordinate initPos,Coordinate finalPos);
 }