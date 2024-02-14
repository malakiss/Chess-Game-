/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chesscore;
import chesscore.Piece;
/**
 *
 * @author ADMIN
 */
public class Move {
    private Coordinate firstCoordinate;
    private Coordinate secondCoordinate;
    private Piece piece;
    private Piece capturedPiece=null;
    private Coordinate captureCoordinate=null;

    public Move(Coordinate firstCoordinate, Coordinate secondCoordinate, Piece piece) {
        this.firstCoordinate = firstCoordinate;
        this.secondCoordinate = secondCoordinate;
        this.piece = piece;
    }
    public Move(Coordinate firstCoordinate, Coordinate secondCoordinate,Piece piece,BoardSquare captureSquare) {
		this.firstCoordinate = firstCoordinate;
		this.secondCoordinate = secondCoordinate;
		this.piece = piece;
		if(captureSquare!=null){
		this.capturedPiece=captureSquare.getPiece();
		this.captureCoordinate=captureSquare.getCoordinate();
		}
	}

    public Coordinate getFirstCoordinate() {
        return firstCoordinate;
    }

    public Coordinate getSecondCoordinate() {
        return secondCoordinate;
    }

   

    public Piece getPiece() {
        return piece;
    }
    
    public boolean isCapture(){
		if ((capturedPiece instanceof NullPiece)){
                    return false;}
		return true;
	}
     public Piece getCapturedPiece() {
       System.out.println("Captured "+capturedPiece.getType());
        return capturedPiece;
    }

    public Coordinate getCaptureCoordinate() {
        return captureCoordinate;
    }
    
   }