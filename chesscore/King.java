package chesscore;
public class King extends Piece{
    public King(PlayerType playertype)
    {
        super(playertype.getColor(), PieceType.KING);
    }

    @Override
    public boolean isValidMove(Coordinate sourceSquare, Coordinate destinationSquare) {
        if (sourceSquare.equals(destinationSquare)){return false;}
		
                	
			//You have not checked for castling
			if (   Math.abs(destinationSquare.getX()-sourceSquare.getX())<=1 
			    && Math.abs(destinationSquare.getY()-sourceSquare.getY())<=1 )
			{
						return true;
			}
			
			return false;
    
    }

    @Override
    public Coordinate[] getRoute(Coordinate initPos, Coordinate finalPos) {
        
        return new Coordinate[]{initPos,finalPos};
      
    }
    

    
}