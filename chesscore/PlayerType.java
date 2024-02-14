package chesscore;

import chesscore.Color;

public enum PlayerType {
    WHITE("W", Color.WHITE), BLACK("B", Color.BLACK);

    private String value;
    private Color color;

    PlayerType(String value, Color color) {
        this.value = value;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return value;
    }

    public static PlayerType getPlayerTypeByColor(Color color) {
        for (PlayerType playerType : PlayerType.values()) {
            if (playerType.getColor() == color) {
                return playerType;
            }
        }

        return null; // Handle the case when color doesn't match any player type
    }
}
