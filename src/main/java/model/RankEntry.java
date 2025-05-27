package model;

public class RankEntry {
    private final String playerName;
    private final int score;

    public RankEntry(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }
}