package chesscore;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Queen extends Piece {

    public Queen(PlayerType playerType) {
        super(playerType.getColor(), PieceType.QUEEN);
    }

    @Override
    public boolean isValidMove(Coordinate sourceSquare, Coordinate destinationSquare) {
        if (sourceSquare.equals(destinationSquare)) {
           
            return false;
        }

        //This is the bishop move.
        int diffX = Math.abs(sourceSquare.getX() - destinationSquare.getX());
        int diffY = Math.abs(sourceSquare.getY() - destinationSquare.getY());
        if (diffX == diffY) {
            return true;
        }

        //This is the rook move.
        if (sourceSquare.getX() == destinationSquare.getX()
                || sourceSquare.getY() == destinationSquare.getY()) {
            return true;
        }

        return false;

    }

    @Override
    public Coordinate[] getRoute(Coordinate initPos, Coordinate finalPos) {
        Coordinate[] path;
        if (initPos.getX() == finalPos.getX()
                || initPos.getY() == finalPos.getY()) {
            int pathLength = Math.abs(initPos.getX() - finalPos.getX())
                    + Math.abs(initPos.getY() - finalPos.getY()) + 1;
            path = new Coordinate[pathLength];
            for (int cnt = 0; cnt < pathLength; cnt++) {
                if ((initPos.getX() == finalPos.getX())) {
                    try {
                        path[cnt] = new Coordinate(initPos.getX(), Math.min(initPos.getY(), finalPos.getY()) + cnt);
                    } catch (Exception ex) {
                        Logger.getLogger(Queen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        path[cnt] = new Coordinate(Math.min(initPos.getX(), finalPos.getX()) + cnt, initPos.getY());
                    } catch (Exception ex) {
                        Logger.getLogger(Queen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        } else {
            //If it a bishop move.
            int pathLength = (Math.abs(initPos.getX() - finalPos.getX()) + Math.abs(initPos.getY() - finalPos.getY())) / 2 + 1;
            path = new Coordinate[pathLength];

            //Integer.signum(a) provides the sign of a number 1 if positive and -1 if negative.
            //In this case i am considering initPos as the first point and finalPos as second
            int i_X = Integer.signum(finalPos.getX() - initPos.getX());
            int i_Y = Integer.signum(finalPos.getY() - initPos.getY());

            for (int cnt = 0; cnt < pathLength; cnt++) {
                try {
                    path[cnt] = new Coordinate(initPos.getX() + i_X * cnt, initPos.getY() + i_Y * cnt);
                } catch (Exception ex) {
                    Logger.getLogger(Queen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return path;
    }

}
