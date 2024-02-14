package chesscore;
public class Knight extends Piece{
    public Knight(PlayerType playertype)
    {
        super(playertype.getColor(), PieceType.KNIGHT);
    }

    @Override
    public boolean isValidMove(Coordinate sourceSquare, Coordinate destinationSquare) {
        if (sourceSquare.equals(destinationSquare)){return false;}
		
		int diffX=Math.abs(sourceSquare.getX()-destinationSquare.getX());
		int diffY=Math.abs(sourceSquare.getY()-destinationSquare.getY());
		if ((diffX+diffY)==3 && diffX!=0 && diffY!=0)
		{return true;}
		
		return false;
    
    
    }

    @Override
    public Coordinate[] getRoute(Coordinate initPos, Coordinate finalPos) {
        return new Coordinate[]{initPos,finalPos};
    }
    

   
    
    
}