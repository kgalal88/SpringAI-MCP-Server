package org.mcp;

public class UserActivitySummary {
    private String userId;
    private int totalActions;
    private int totalScore;
    private String createdDate;

    public UserActivitySummary(String userId, int totalActions, int totalScore) {
        this.userId = userId;
        this.totalActions = totalActions;
        this.totalScore = totalScore;
    }

    public UserActivitySummary(String userId, int totalScore, String createdDate) {
        this.userId = userId;
        this.totalScore = totalScore;
        this.createdDate = createdDate;
    }

    // Getters
    public String getUserId() {
        return userId;
    }

    public int getTotalActions() {
        return totalActions;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public String getCreatedDate() {
        return createdDate;
    }
}
