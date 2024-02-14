/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chesscore;
//import pieces.*;

import chesscore.PlayerType;
import chesscore.BoardSquare;
import chesscore.Board;
import chesscore.Coordinate;

/**
 *
 * @author ADMIN
 */
public class Board {

    private BoardSquare[][] squares = new BoardSquare[8][8];

    public void resetBoard() {
        setSquares();
        setWhitePieces();
        setBlackPieces();
    }

    private void setSquares() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Coordinate c = new Coordinate(x, y);
                squares[x][y] = new BoardSquare(c);
            }
        }

    }

    private void setWhitePieces() {
        squares[2][0].setPiece(new Bishop(PlayerType.WHITE));
        squares[5][0].setPiece(new Bishop(PlayerType.WHITE));
        squares[1][0].setPiece(new Knight(PlayerType.WHITE));
        squares[6][0].setPiece(new Knight(PlayerType.WHITE));
        squares[0][0].setPiece(new Rook(PlayerType.WHITE));
        squares[7][0].setPiece(new Rook(PlayerType.WHITE));
        squares[3][0].setPiece(new Queen(PlayerType.WHITE));
        squares[4][0].setPiece(new King(PlayerType.WHITE));
        squares[0][1].setPiece(new Pawn(PlayerType.WHITE));
        squares[1][1].setPiece(new Pawn(PlayerType.WHITE));
        squares[2][1].setPiece(new Pawn(PlayerType.WHITE));
        squares[3][1].setPiece(new Pawn(PlayerType.WHITE));
        squares[4][1].setPiece(new Pawn(PlayerType.WHITE));
        squares[5][1].setPiece(new Pawn(PlayerType.WHITE));
        squares[6][1].setPiece(new Pawn(PlayerType.WHITE));
        squares[7][1].setPiece(new Pawn(PlayerType.WHITE));

    }

    private void setBlackPieces() {
        squares[2][7].setPiece(new Bishop(PlayerType.BLACK));
        squares[5][7].setPiece(new Bishop(PlayerType.BLACK));
        squares[1][7].setPiece(new Knight(PlayerType.BLACK));
        squares[6][7].setPiece(new Knight(PlayerType.BLACK));
        squares[0][7].setPiece(new Rook(PlayerType.BLACK));
        squares[7][7].setPiece(new Rook(PlayerType.BLACK));
        squares[3][7].setPiece(new Queen(PlayerType.BLACK));
        squares[4][7].setPiece(new King(PlayerType.BLACK));
        squares[0][6].setPiece(new Pawn(PlayerType.BLACK));
        squares[1][6].setPiece(new Pawn(PlayerType.BLACK));
        squares[2][6].setPiece(new Pawn(PlayerType.BLACK));
        squares[3][6].setPiece(new Pawn(PlayerType.BLACK));
        squares[4][6].setPiece(new Pawn(PlayerType.BLACK));
        squares[5][6].setPiece(new Pawn(PlayerType.BLACK));
        squares[6][6].setPiece(new Pawn(PlayerType.BLACK));
        squares[7][6].setPiece(new Pawn(PlayerType.BLACK));

    }

    public BoardSquare[][] getSquares() {
        return squares;
    }

    public BoardSquare getSquare(Coordinate coordinate) {
        BoardSquare result = null;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Coordinate c = new Coordinate(x, y);
                if ((c.getX() == coordinate.getX()) && (c.getY() == coordinate.getY())) {
                    result = squares[x][y];
                    //System.out.println(result.getCoordinate());

                }
            }
        }

        return result;
    }

    public void makeMove(Coordinate initCoordinate, Coordinate finalCoordinate) {
        makeMove(getSquare(initCoordinate), getSquare(finalCoordinate));

    }

    public void makeMove(BoardSquare initSquare, BoardSquare finalSquare) {

        if (finalSquare.isOccupied()) {
        
            capturePiece(finalSquare);
        }
       
        finalSquare.setPiece(initSquare.getPiece());
        initSquare.releasePiece();
    }

    public void setPiece(Coordinate coordinate, Piece piece) {
        getSquare(coordinate).setPiece(piece);
    }

    public void capturePiece(BoardSquare square) {
        if (square.isOccupied()) {
            if (square.getPiece() instanceof Pawn) {
              //System.out.println("Captured Pawn");
            }
            square.releasePiece();

        }
    }

}
