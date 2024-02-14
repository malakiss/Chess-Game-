package chesscore;

class BoardSquare {

    private Piece piece = null;
    private Coordinate coordinate;

    public BoardSquare(Coordinate coordinate, Piece piece) {
        this.coordinate = coordinate;
        setPiece(piece);
    }

    public BoardSquare(Coordinate coordinate) {
        this(coordinate, null);
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        if (this.piece == null) {
            return new NullPiece();
        }
        return this.piece;

    }

    public Coordinate getCoordinate() {
        if (this.piece == null) {
            // Return a default coordinate or handle the unoccupied case accordingly
            return new UnoccupiedCoordinate(); // Replace with your logic for unoccupied squares
        }

        // Check if the coordinate is null before returning
        if (coordinate == null) {
            // Handle the case where the coordinate is unexpectedly null
            throw new IllegalStateException("Coordinate is unexpectedly null for an occupied square");
        }

        return coordinate;
    }

    public boolean equals(BoardSquare square) {
        if (square.getCoordinate().equals(coordinate)) {
            return true;
        }
        return false;
    }

    public boolean isOccupied() {
        return  !(piece instanceof NullPiece);
    }

    public String getPieceString() {
        if (piece == null) {
            return "  ";
        }
        return piece.toString();
    }

    public void releasePiece() {
        piece = null;
    }

}
