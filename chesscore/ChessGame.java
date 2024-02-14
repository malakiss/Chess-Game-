/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chesscore;

import java.util.ArrayList;
import java.util.List;

import chesscore.Bishop;
import chesscore.Knight;
import chesscore.Pawn;
import chesscore.Piece;
import chesscore.PieceType;
import chesscore.Queen;
import chesscore.Rook;
import chesscore.Color;
import chesscore.Board;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class ChessGame {

    private Board board;
    private PlayerType currentPlayer = PlayerType.WHITE;
    private List<Move> moveList = new ArrayList<Move>();
    private Player player1 = new Player(currentPlayer);
    private Player player2 = new Player(PlayerType.BLACK);
    private PlayerType turn; // Current turn: WHITE or BLACK
    private boolean isGameOver;
    private boolean isWhiteInCheck;
    private boolean isBlackInCheck;

    public ChessGame() {
        this.board = new Board();
        initializeBoard();
        initializePlayers();

    }

    public void initializeBoard() {
        board.resetBoard();

    }

    public Board returnBoard() {
        return this.board;
    }

    public void initializePlayers() {
        player1 = new Player(PlayerType.WHITE);
        player2 = new Player(PlayerType.BLACK);
    }

    public List<Move> getMoveList() {
        return moveList;
    }

    public void switchCurrentPlayer() {

        if (turn == player1.getPlayer()) {
            turn = player2.getPlayer();
        } else {
            turn = player1.getPlayer();
        }

    }

    public boolean isValidPromotion(BoardSquare square) {
        if (!square.isOccupied()) {
            return false;
        }
        Piece piece = square.getPiece();
        if (piece.getType() == PieceType.PAWN) {
            int row = square.getCoordinate().getY();
            if (piece.getColor() == Color.WHITE && row == 0) {
                return true;
            }
            if (piece.getColor() == Color.BLACK && row == 7) {
                return true;
            }
        }
        return false;
    }

    public boolean promote(BoardSquare square, PieceType pieceType) {
        if (isValidPromotion(square)) {
            Piece piece;
            if (pieceType == PieceType.BISHOP) {
                piece = new Bishop(square.getPiece().getPlayerType());
            } else if (pieceType == PieceType.KNIGHT) {
                piece = new Knight(square.getPiece().getPlayerType());
            } else if (pieceType == PieceType.ROOK) {
                piece = new Rook(square.getPiece().getPlayerType());
            } else {
                piece = new Queen(square.getPiece().getPlayerType());
            }
            moveList.add(new Move(square.getCoordinate(), square.getCoordinate(), piece, square));
            square.setPiece(piece);
            return true;
        }
        return false;
    }

    public boolean hasPieceMoved(BoardSquare square) {
        for (Move move : moveList) {
            if (move.getFirstCoordinate() == square.getCoordinate()
                    || move.getSecondCoordinate() == square.getCoordinate()) {
                return true;
            }
        }
        return false;
    }

    public boolean isPathClear(Coordinate[] path, Coordinate initCoordinate,Coordinate finalCoordinate) {
        BoardSquare[][] squares = board.getSquares();
        for (Coordinate coordinate : path) {
            if ((squares[coordinate.getX()][coordinate.getY()].isOccupied()  && !(squares[coordinate.getX()][coordinate.getY()].getPiece() instanceof NullPiece))
                    && (!coordinate.equals(initCoordinate))
                    && (!coordinate.equals(finalCoordinate))) {
                return false;
            }
        }
        return true;
    }

    public boolean isValidCastling(BoardSquare kingSquare, BoardSquare rookSquare) {

        if (!(kingSquare.getPiece() instanceof NullPiece) && !(rookSquare.getPiece() instanceof NullPiece)) {
            return false;
        }
        if (hasPieceMoved(kingSquare) || hasPieceMoved(rookSquare)) {
            
            return false;
        }
        if (kingSquare.getPiece().getColor() != rookSquare.getPiece().getColor()) {
            return false;
        }

        if (!isPathClear(rookSquare.getPiece().getRoute(rookSquare.getCoordinate(), kingSquare.getCoordinate()), rookSquare.getCoordinate(), kingSquare.getCoordinate())) {
           
            return false;
        }
        if (kingSquare.getPiece().getType() == PieceType.KING && rookSquare.getPiece().getType() == PieceType.ROOK) {
            int col = 0;
            if (kingSquare.getPiece().getColor() == Color.BLACK) {
                col = 7;
            }
            if (kingSquare.getCoordinate().equals(new Coordinate(4, col)) && (rookSquare.getCoordinate().equals(new Coordinate(0, col)) || rookSquare.getCoordinate().equals(new Coordinate(7, col)))) {

                // Check if there is check in any way between the king and final
                // king square
                int offset;
                if (Math.signum(rookSquare.getCoordinate().getX() - kingSquare.getCoordinate().getX()) == 1) {
                    offset = 2;
                } else {
                    offset = -2;
                }
                // Calculates final kings X coordinate
                int kingX = kingSquare.getCoordinate().getX() + offset;
                for (Coordinate coordinate : rookSquare.getPiece().getRoute(kingSquare.getCoordinate(), new Coordinate(kingX, kingSquare.getCoordinate().getY()))) {
                    if (kingSquare.equals(board.getSquare(coordinate))) {
                        // This removes a nasty null pointer exception
                        continue;
                    }
                    if (moveMakesCheck(kingSquare, board.getSquare(coordinate))) {
                        return false;
                    }
                }

                return true;
            }
        }
        return false;
    }

    public void castle(BoardSquare kingSquare, BoardSquare rookSquare) {
        int offset;
        if (Math.signum(rookSquare.getCoordinate().getX()
                - kingSquare.getCoordinate().getX()) == 1) {
            offset = 2;
        } else {
            offset = -2;
        }
        int kingX = kingSquare.getCoordinate().getX() + offset;
        int rookX = kingX - offset / 2;
        board.makeMove(kingSquare.getCoordinate(), new Coordinate(kingX, kingSquare.getCoordinate().getY()));
        board.makeMove(rookSquare.getCoordinate(), new Coordinate(rookX, rookSquare.getCoordinate().getY()));

    }

    public boolean isValidEnpassant(BoardSquare s1, BoardSquare s2) {

        if (s2.isOccupied()) {
            return false;
        }

        if (s1.getPiece() != null && s1.getPiece().getType() != PieceType.PAWN) {
            return false;
        }

        if (s1.getPiece() != null && s1.getPiece().getColor() == Color.WHITE) {
            if (s1.getCoordinate().getY() > s2.getCoordinate().getY()) {

                return false;
            }
        } else {
            if (s1.getCoordinate().getY() < s2.getCoordinate().getY()) {

                return false;
            }
        }
        if (Math.abs(s1.getCoordinate().getX() - s2.getCoordinate().getX()) == 1 && Math.abs(s1.getCoordinate().getY() - s2.getCoordinate().getY()) == 1) {

            if (moveList.isEmpty()) {
                return false;
            }
            Move lastMove = moveList.get(moveList.size() - 1);
            if (lastMove.getPiece() == null) {
                return false;
            }
            if (board.getSquare(lastMove.getSecondCoordinate()).getPiece().getType() == PieceType.PAWN) {

                if (Math.abs(lastMove.getSecondCoordinate().getY() - lastMove.getFirstCoordinate().getY()) == 2 && lastMove.getSecondCoordinate().getX() == s2.getCoordinate().getX()) {
                    Pawn pawn = new Pawn(s1.getPiece().getPlayerType());
                   // pawn.enpassant(s1, s2, moveList, board);
                    return true;
                }
            }
        }
        return false;
    }
     public void enpassant(BoardSquare sourceSquare, BoardSquare destinationSquare,List<Move> moveList,Board board)  {
		Move lastMove = moveList.get(moveList.size() - 1);
		board.capturePiece(board.getSquare(lastMove.getSecondCoordinate()));
		board.makeMove(sourceSquare, destinationSquare);

	} 

    public boolean isValidPawnCapture(BoardSquare s1, BoardSquare s2) {
        Coordinate c1 = s1.getCoordinate();
        Coordinate c2 = s2.getCoordinate();

        PieceType pieceType = s1.getPiece().getType();
        PlayerType playerType = s1.getPiece().getPlayerType();

        if (pieceType != PieceType.PAWN) {
            return false;
        }

        if (!s2.isOccupied()) {
            return false;
        }

        int yDifference = Math.abs(c1.getY() - c2.getY());
        int xDifference = Math.abs(c1.getX() - c2.getX());

        if (yDifference == 1 && xDifference == 1) {
            switch (playerType) {
                case WHITE:
                    if (c1.getY() < c2.getY()) {

                        //capturePawn(s1, s2);
                        return true;
                    }
                    break;
                case BLACK:
                    if (c1.getY() > c2.getY()) {
                        //capturePawn(s1, s2);

                        return true;
                    }
                    break;
                default:
                    return false;
            }

        }
        return false;
    }

    public void capturePawn(BoardSquare sourceSquare, BoardSquare destinationSquare) {

        // Capture the pawn
       /* if (destinationSquare.getPiece() instanceof Pawn) {
            System.out.println("Captured Pawn");
        }*/
        destinationSquare.releasePiece(); // Remove the captured pawn from the destination square
        destinationSquare.setPiece(sourceSquare.getPiece()); // Move the capturing pawn to the destination square
        sourceSquare.releasePiece(); // Remove the capturing pawn from the source square

        // throw new Exception("Invalid pawn capture.");
    }

    public BoardSquare KingSquare(PlayerType player) {
        BoardSquare[][] squares = board.getSquares();

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                BoardSquare square = squares[x][y];

                //if ( square.isOccupied() && !(square.getPiece()instanceof NullPiece)) {
                    if (square.getPiece().getType() == PieceType.KING && square.getPiece().getPlayerType() == player) {
                        //System.out.println(square.getPieceString());
                        return square;
                    }
               // }

                /*   if (square.isOccupied(square) && square.getPiece() != null) 
	 if (square.getPiece().getType() == PieceType.KING && square.getPiece().getPlayerType()== player) 
				return square;*/
            }
        }
        return null;

    }

    public BoardSquare[] getAttackingPieces(PlayerType player) {
        List<BoardSquare> squares = new ArrayList<BoardSquare>();
        BoardSquare[][] allSquares = board.getSquares();
        BoardSquare kingSquare = KingSquare(player);
        
        if (kingSquare != null) {
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    BoardSquare tmpSquare = allSquares[x][y];
                    if (tmpSquare.isOccupied()&& !(tmpSquare.getPiece() instanceof NullPiece) && tmpSquare.getPiece().getPlayerType()!=player){
                        
                        Coordinate tmp = tmpSquare.getCoordinate();
                        Coordinate king = kingSquare.getCoordinate();
                        if ( isValidMovement(tmpSquare,kingSquare) ) {
                            squares.add(tmpSquare);
                          //  System.out.println(tmpSquare.getPieceString());
                            //System.out.println(squares.size());
                            
                        }
                    }

                }
            }
        }
      //   System.out.println(squares.isEmpty());
        BoardSquare[] result = new BoardSquare[squares.size()];
        for (int i=0;i<squares.size();i++){
            result[i]=squares.get(i);
           
        }
       
        return result;
    }

    public boolean isCheck(PlayerType p) {
        if (getAttackingPieces(p).length > 0) {
           
            return true;
        } else {
            return false;
        }

    }

    public boolean moveMakesCheck(BoardSquare sourceSquare, BoardSquare destinationSquare) {
        try {
            // Save the current state

            Piece temporaryPiece = destinationSquare.getPiece();
            destinationSquare.setPiece(sourceSquare.getPiece());
            sourceSquare.releasePiece();
            boolean enpassant = false;
            Piece tmp = null;
            BoardSquare lastMove = null;
            
           
      // System.out.println(KingSquare(sourceSquare.getPiece().getPlayerType()));

       
            
            // If it is an en passant move, temporarily remove a piece from the board
            if (isValidEnpassant(sourceSquare, destinationSquare)) {
               
                enpassant = true;
                lastMove = board.getSquare(moveList.get(moveList.size() - 1).getSecondCoordinate());
                tmp = lastMove.getPiece();
                lastMove.releasePiece();
            }

            // Check if the move leaves the king in check
            if (isCheck(temporaryPiece.getPlayerType())) {
                // Revert the changes
              
                sourceSquare.setPiece(destinationSquare.getPiece());
                destinationSquare.setPiece(temporaryPiece);
               /*if (enpassant) {
                    lastMove.setPiece(tmp);
                }*/
                
                return true; // Move leaves king in check
            } else {
                // Revert the changes
               sourceSquare.setPiece(destinationSquare.getPiece());
                destinationSquare.setPiece(temporaryPiece);
                if (enpassant) {
                    lastMove.setPiece(tmp);
                }
                return false; // Move doesn't leave king in check
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception or log the error message as needed
            return false;
        }
        
    }

    private boolean isSaneMove(BoardSquare initSquare, BoardSquare finalSquare) {
        //Check if the coordinates are valid
        if (!initSquare.getCoordinate().isValid() || !initSquare.getCoordinate().isValid()) {
           
            return false;
        }
        // If the player tries to move a empty square.
        if ((initSquare.getPiece() instanceof NullPiece)) {
            
            return false;
        }
      
        if (initSquare.getPiece().getPlayerType() == finalSquare.getPiece().getPlayerType()) {
          
            return false;
        }
        // If it is moving to the same square.
        // This is also checked by every piece but still for safety
        if (initSquare.equals(finalSquare)) {
            
            return false;
        }

        return true;
    }

    private boolean isValidMovement(BoardSquare initSquare, BoardSquare finalSquare) {
        if (!isSaneMove(initSquare, finalSquare)) {
            return false;
        }
        // If the player tries to take his own piece.
        if ( !(finalSquare.getPiece() instanceof NullPiece)) {
            if (initSquare.getPiece().getPlayerType() == finalSquare.getPiece().getPlayerType()) {
                return false;
            } }
        if (!initSquare.getPiece().isValidMove(initSquare.getCoordinate(),finalSquare.getCoordinate())
                && !isValidPawnCapture(initSquare, finalSquare)
                && !isValidEnpassant(initSquare, finalSquare)  ) {
            return false;
        }
         if (initSquare.getPiece().getType() == PieceType.PAWN
                && finalSquare.isOccupied()
                && !isValidPawnCapture(initSquare, finalSquare)) {
            return false;
        } // If piece is blocked by other pieces
        Coordinate[] path = initSquare.getPiece().getRoute(initSquare.getCoordinate(), finalSquare.getCoordinate());
        if (!isPathClear(path, initSquare.getCoordinate(),
                finalSquare.getCoordinate())) {
            
            return false;
        }
        return true;
    }

    public boolean isValidMove(BoardSquare s1, BoardSquare s2) {
        if(s1.getPiece().getPlayerType()== s2.getPiece().getPlayerType() ){
            return false;
            
        }
      if (isValidCastling(s1, s2)) {

            return true;
        } 
        if (isValidMovement(s1, s2)) {
            
            return true;
        }
        if (moveMakesCheck(s1, s2)) {
            
            return true;
        }
        
                return false;

    }

    public BoardSquare[] getAllValidMoves(Coordinate coordinate) {
        List<BoardSquare> moves = new ArrayList<BoardSquare>();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (isValidMove(board.getSquare(coordinate), board.getSquares()[x][y])) {
                    moves.add(board.getSquares()[x][y]);
                }
            }
        }

        return moves.toArray(new BoardSquare[0]);
    }

    public boolean isStalemate(PlayerType player) {
        BoardSquare[][] squares = board.getSquares();
        if (player == null) {
            return false;
        }
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                BoardSquare square = squares[x][y];

                // Check if the square is occupied by a piece of the given player
                if (square.isOccupied() && square.getPiece().getPlayerType() == player) {
                    // Check if there are any valid moves for the piece on this square

                    Coordinate sourceCoordinate = square.getCoordinate();
                    BoardSquare[] validMoves = getAllValidMoves(sourceCoordinate);

                    // If there is at least one valid move, it's not stalemate
                    if (validMoves.length > 0) {

                        return false;
                    }
                }
            }
        }

        // If no valid moves are found for any piece of the player, it's stalemate
        return true;
    }

    public boolean isInsufficientMaterial() {
        BoardSquare[][] squares = board.getSquares();

        // Count the number of pieces on the board
        int whitePieces = 0;
        int blackPieces = 0;

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (squares[x][y].isOccupied()) {
                    if (squares[x][y].getPiece().getColor() == Color.WHITE) {
                        whitePieces++;
                    } else {
                        blackPieces++;
                    }
                }
            }
        }

        // Insufficient material conditions:
        // 1. Only kings are left for both players.
        // 2. Only kings and one knight are left for both players.
        // 3. Only kings and two knights are left for both players.
        if ((whitePieces == 1 && blackPieces == 1)
                || (whitePieces == 2 && blackPieces == 1 && hasOneKnight(Color.WHITE))
                || (whitePieces == 1 && blackPieces == 2 && hasOneKnight(Color.BLACK))
                || (whitePieces == 2 && blackPieces == 2 && hasOneKnight(Color.WHITE) && hasOneKnight(Color.BLACK))) {
            return true;
        }

        return false;
    }

    public boolean hasOneKnight(Color color) {
        int knightCount = 0;

        BoardSquare[][] squares = board.getSquares();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (squares[x][y].isOccupied() && squares[x][y].getPiece().getColor() == color) {
                    if (squares[x][y].getPiece().getType() == PieceType.KNIGHT) {
                        knightCount++;
                    }
                }
            }
        }

        return knightCount == 1;
    }

    public boolean isCheckmate(PlayerType player) {
     BoardSquare[] attackers = getAttackingPieces(player);
        // If there are no attackers
		if (attackers.length == 0) {
                  	return false;
		}
             // If there is more than one attacker then there are many options check all.
                boolean checkmate = true;
		BoardSquare kingSquare = KingSquare(player);
		BoardSquare[][] allSquares = board.getSquares();
   for (int x = 0; x < 8; x++) {
	for (int y = 0; y < 8; y++) { 
                    int i=0;
     Coordinate [] attackPath = attackers[i].getPiece().getRoute(attackers[i].getCoordinate(), kingSquare.getCoordinate());
    	// If the king can move to a different square.
       if ( KingSquare(player) != board.getSquares()[x][y] ){
          Coordinate[] kingpath=kingSquare.getPiece().getRoute(KingSquare(player).getCoordinate(), board.getSquares()[x][y].getCoordinate());
     if(isValidMovement( KingSquare(player), board.getSquares()[x][y]) 
          && !moveMakesCheck( board.getSquares()[x][y], KingSquare(player)) 
           && isPathClear(kingpath, KingSquare(player).getCoordinate(), board.getSquares()[x][y].getCoordinate()) 
             && !isCheck(kingSquare.getPiece().getPlayerType())) {
         // System.out.println("h");
              checkmate= false;
              
				}
       }
	for (Coordinate coordinate : attackPath) {
		BoardSquare tmpSquare = allSquares[x][y];
		// The square must be occupied
		if (!(tmpSquare.getPiece() instanceof NullPiece)) {
        // The player must move his own piece between the paths
	// of the attacker and the King.
	// If it can do so then there is no checkmate
	if (tmpSquare.getPiece().getPlayerType()== kingSquare.getPiece().getPlayerType()
        && isValidPawnCapture(tmpSquare, board.getSquare(coordinate))
	&& isValidMove(tmpSquare,board.getSquare(coordinate)) 
        && !moveMakesCheck( board.getSquare(coordinate),tmpSquare ))
        {
	      
            checkmate = false;
            
			}
		}
               
        }
			
        i++;}

		}
	return checkmate;
  }

    public boolean isGameEnded() {
        if (isCheckmate(player1.getPlayer()) || isCheckmate(player2.getPlayer())) {
            //System.out.println("Game already ended. Checkmate!");
            return true;
        }

        if (isStalemate(player1.getPlayer()) || isStalemate(player2.getPlayer())) {
            //System.out.println("Game already ended. Stalemate!");
            return true;
        }

        if (isInsufficientMaterial()) {
            //System.out.println("Game already ended. Insufficient material!");
            return true;
        }

        return false;
    }

  
}
