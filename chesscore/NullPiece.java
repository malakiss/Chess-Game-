
package chesscore;

public class NullPiece extends Piece {

    
    public NullPiece()
    {
        super(null, null);
    }
    
 
    @Override
    public boolean isValidMove(Coordinate sourceSquare, Coordinate destinationSquare) {
       return false;
    }

    @Override
    public Coordinate[] getRoute(Coordinate initPos, Coordinate finalPos) {
       return new Coordinate[0];  }
  }


