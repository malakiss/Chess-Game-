package chesscore;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

class Pawn extends Piece  {
   public Pawn(PlayerType playerType) {
		super(playerType.getColor(),PieceType.PAWN);
	}

    @Override
	public boolean isValidMove(Coordinate sourceSquare, Coordinate destinationSquare) {
		if (sourceSquare.equals(destinationSquare)) {
                   
			return false;
		}

		// This is for normal pawn moves.
		if (Math.abs(sourceSquare.getY() - destinationSquare.getY()) == 1
				&& Math.abs(sourceSquare.getX() - destinationSquare.getX()) == 0) {
			// White can only move forward
                        
			if (this.getPlayerType()== PlayerType.WHITE) {
				if (sourceSquare.getY() < destinationSquare.getY()) {
					return true;
				}
			}
			// Black can only move backward in a sense.
			if (this.getPlayerType()== PlayerType.BLACK) {
				if (sourceSquare.getY() > destinationSquare.getY()) {
					return true;
				}
			}

		}

		// This is for first pawn move.
		if (Math.abs(sourceSquare.getY() - destinationSquare.getY()) == 2
				&& Math.abs(sourceSquare.getX() - destinationSquare.getX()) == 0
				&& (sourceSquare.getY() == 1 || sourceSquare.getY() == 6) ) {

			// White can only move forward
			if (this.getPlayerType()== PlayerType.WHITE) {
				if (sourceSquare.getY() < destinationSquare.getY()) {
                                    
					return true;
				}
			}
			// Black can only move backward in a sense.
			if (this.getColor()== Color.BLACK) {
				if (sourceSquare.getY() > destinationSquare.getY()) {
					return true;
				}
			}

		}
               
if (Math.abs(sourceSquare.getX() - destinationSquare.getX()) == 1
            && Math.abs(sourceSquare.getY() - destinationSquare.getY()) == 1) {
        // White can only move forward diagonally for a capture
        
        if (this.getColor() == Color.WHITE && sourceSquare.getY() < destinationSquare.getY()) {
            return true;
        }
        // Black can only move backward diagonally for a capture
        if (this.getColor() == Color.BLACK && sourceSquare.getY() > destinationSquare.getY()) {
            return true;
        }
    }

		return false;
        }

    @Override
    public Coordinate[] getRoute(Coordinate sourceSquare, Coordinate destinationSquare) {
        //This is for pawn captures
		if (sourceSquare.getX()!=destinationSquare.getX()){return new Coordinate[]{sourceSquare,destinationSquare};}
		//This is for normal pawn moves and first pawn moves.
		int routeLength = Math.abs(sourceSquare.getY() - destinationSquare.getY()) + 1;
		Coordinate[] route = new Coordinate[routeLength];

		for (int cnt = 0; cnt < routeLength; cnt++) {
                    try {
                        route[cnt] = new Coordinate(sourceSquare.getX(), Math.min(sourceSquare.getY(),
                                destinationSquare.getY()) + cnt);
                    } catch (Exception ex) {
                        System.out.println("Invalid Coordinates");
                    }
		}

		return route; }
    
     public void enpassant(BoardSquare sourceSquare, BoardSquare destinationSquare,List<Move> moveList,Board board)  {
		Move lastMove = moveList.get(moveList.size() - 1);
		board.capturePiece(board.getSquare(lastMove.getSecondCoordinate()));
		board.makeMove(sourceSquare, destinationSquare);

	} 

   
}