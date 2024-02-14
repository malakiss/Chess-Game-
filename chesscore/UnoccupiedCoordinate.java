package chesscore;

public class UnoccupiedCoordinate extends Coordinate {
    // You can customize this class based on your needs
    public UnoccupiedCoordinate() {
        super(-1, -1); // Using -1, -1 as a special value for unoccupied squares
    }

    @Override
    public String toString() {
        return "Unoccupied";
    }
}
