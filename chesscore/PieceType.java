/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package chesscore;

/**
 *
 * @author ADMIN
 */
public enum PieceType {
    
   KING("King"), KNIGHT("Knight"), ROOK("Rook"), QUEEN("Queen"), BISHOP("Bishop"), PAWN("Pawn");
    private String value;

	PieceType(String value) {
		this.value = value;

	}
    public String toString() {
		return this.value;
	}
    public static PieceType fromString(String value) {
    for (PieceType piece : PieceType.values()) {
        if (piece.value.equalsIgnoreCase(value)) {
            return piece;
        }
    }
    return null;
}

}
