package chesscore;
public class Bishop extends Piece{
    public Bishop(PlayerType playertype)
    {
        super(playertype.getColor(), PieceType.BISHOP);
    }

    @Override
    public boolean isValidMove(Coordinate sourceSquare, Coordinate destinationSquare) {
       if (sourceSquare.equals(destinationSquare)){return false;}
		
		int diffX=Math.abs(sourceSquare.getX()-destinationSquare.getX());
		int diffY=Math.abs(sourceSquare.getY()-destinationSquare.getY());
		if (diffX==diffY) return true;
		
		return false;
    
    }

    @Override
    public Coordinate[] getRoute(Coordinate initPos, Coordinate finalPos) {
        int pathLength=( Math.abs(initPos.getX()-finalPos.getX())+
						Math.abs(initPos.getY()-finalPos.getY()) )/2+1;
		Coordinate[] path=new Coordinate[pathLength];
		
		//Integer.signum(a) provides the sign of a number 1 if positive and -1 if negative.
		//In this case i am considering initPos as the first point and finalPos as second
		int i_X=Integer.signum(finalPos.getX()-initPos.getX());
		int i_Y=Integer.signum(finalPos.getY()-initPos.getY());

		for (int cnt=0;cnt<pathLength;cnt++)
		{
			path[cnt]=new Coordinate(initPos.getX()+i_X*cnt,initPos.getY()+i_Y*cnt);
		}

		
		return path;
	}

   

    }

    
