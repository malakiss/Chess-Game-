/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package chesscore;

/**
 *
 * @author ADMIN
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import chesscore.ChessGame;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
public class NEWmainnnn {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      try {
            ChessGame chessGame = new ChessGame();
            
            
            // Specify the file path
            String filePath = "Input.txt";

            // Read chess instructions from file
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = reader.readLine()) != null) 
            {
               
                String[] moves = line.split(",");

                // Convert moves to Coordinates
                Coordinate source = new Coordinate(moves[0]);
                Coordinate destination= new Coordinate(moves[1]);
                
                
                BoardSquare sourceSquare= chessGame.returnBoard().getSquare(source);
                BoardSquare destinationSquare=chessGame.returnBoard().getSquare(destination);
               
                 Piece piece = sourceSquare.getPiece();
       boolean moveMakesCheckBlack=false;
       boolean moveMakesCheckWhite=false;
            if (piece == null) {

            try {
                throw new Exception("No piece at the source square");
            } catch (Exception ex) {
                 ex.printStackTrace();
                //System.out.println("No piece at the source square");
                //Logger.getLogger(ChessGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (!piece.isValidMove(source, destination)) {
            try {
                throw new Exception("Invalid move for the piece");
            } catch (Exception ex) {
                 ex.printStackTrace();
                //Logger.getLogger(ChessGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
         
        
        // Perform the move
        Piece capturedPiece = destinationSquare.getPiece();
        Move move = new Move(source, destination, piece, destinationSquare);
         /*if(chessGame.isGameEnded()){
                //System.out.println("Game Ended!!");
                 return;
            }*/

       if (chessGame.moveMakesCheck(sourceSquare, destinationSquare)) {
                  
            if (destinationSquare.getPiece().getPlayerType() == PlayerType.BLACK) {
                moveMakesCheckBlack=true;
            } else {
                moveMakesCheckWhite=true;
            }
            
        }

        // Handle castling
        if (chessGame.isValidCastling(sourceSquare, destinationSquare)) {

            chessGame.castle(sourceSquare, destinationSquare);

            System.out.println("Castle");
            //move.getCapturedPiece();
          

        }  if (chessGame.isValidEnpassant(sourceSquare, destinationSquare)) {
            // Handle en passant
            Pawn pawn = (Pawn) sourceSquare.getPiece();
            pawn.enpassant(sourceSquare, destinationSquare, chessGame.getMoveList(), chessGame.returnBoard());
            System.out.println("En passant");
            //move.getCapturedPiece();
             

        }  if (chessGame.isValidPawnCapture(sourceSquare, destinationSquare)) {
            chessGame.capturePawn(sourceSquare, destinationSquare);
           // move.getCapturedPiece();
             
        } else {
            // Regular move
            // Make the move
            
            chessGame.returnBoard().makeMove(sourceSquare, destinationSquare);
            
            chessGame.getMoveList().add(move);
            }
        if(chessGame.isInsufficientMaterial() )
                    System.out.println("Insufficient Material");
       
        if(move.isCapture()){
                move.getCapturedPiece();
            }
        if (chessGame.isCheckmate(capturedPiece.getPlayerType())) {
           
            if (capturedPiece.getPlayerType()== PlayerType.BLACK) {
                moveMakesCheckWhite=false;
                
                System.out.println("White Won");
                
            }
            else {
                moveMakesCheckBlack=false;
                //System.out.println(chessGame.isGameEnded());
                System.out.println("Black Won");
                
            }
            if(chessGame.isGameEnded()){
                //System.out.println("Game Ended!!");
                 return;
            }
               
            
       }

       /* if (isStalemate(piece.getPlayerType())) {
            System.out.println("Stalemate");
        }*/
        if(moveMakesCheckBlack)
            System.out.println("Black in check");
        if(moveMakesCheckWhite)
            System.out.println("White in check");
     
        
        
        // Switch the player
        chessGame.switchCurrentPlayer();
              
                   

                if (chessGame.isStalemate(PlayerType.BLACK) || chessGame.isStalemate(PlayerType.WHITE)) {
                    System.out.println("Stalemate");
                }
                
                
            } 
                
                reader.close();
                
      }catch (Exception e) {
            e.printStackTrace();

           

         }
        }
}  
                
                
              
              
                
           
           
           
   
