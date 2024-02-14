package chesscore;

import java.util.logging.Level;
import java.util.logging.Logger;

 public class Rook extends Piece{
     public Rook(PlayerType playertype)
     {
         super(playertype.getColor(), PieceType.ROOK);
     }

    @Override
    public boolean isValidMove(Coordinate sourceSquare, Coordinate destinationSquare) {
        
        if (sourceSquare.equals(destinationSquare)){return false;}
		
		if (sourceSquare.getX()==destinationSquare.getX() ||
			sourceSquare.getY()==destinationSquare.getY())
		{return true;}
		return false;
    
    }

    @Override
    public Coordinate[] getRoute(Coordinate initPos, Coordinate finalPos) {
        int pathLength=Math.abs(initPos.getX()-finalPos.getX())+Math.abs(initPos.getY()-finalPos.getY())+1;
		Coordinate[] path=new Coordinate[pathLength];
		for (int cnt=0;cnt<pathLength;cnt++)
		{
			if ((initPos.getX()==finalPos.getX())){
                            try {
                                path[cnt]=new Coordinate(initPos.getX(),Math.min(initPos.getY(),finalPos.getY())+cnt);
                            } catch (Exception ex) {
                                Logger.getLogger(Rook.class.getName()).log(Level.SEVERE, null, ex);
                            }
			}
			else{
                            try {
                                path[cnt]=new Coordinate(Math.min(initPos.getX(),finalPos.getX())+cnt,initPos.getY());
                            } catch (Exception ex) {
                                Logger.getLogger(Rook.class.getName()).log(Level.SEVERE, null, ex);
                            }
			}
		}
		return path;
	}

    }

