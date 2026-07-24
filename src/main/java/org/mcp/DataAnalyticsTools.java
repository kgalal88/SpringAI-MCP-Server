package org.mcp;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class DataAnalyticsTools {

    private static final Logger logger = LoggerFactory.getLogger(DataAnalyticsTools.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Tool(description = "Get user activity summary from database")
    public UserActivitySummary getUserActivity(String userId, String startDate, String endDate) {
        logger.info("Getting user activity for: {}", userId);

        String sql = "SELECT COUNT(*) as total_actions, SUM(score) as total_score " +
                "FROM user_activity WHERE user_id = ? AND created_date BETWEEN ? AND ?";

        Map<String, Object> result = jdbcTemplate.queryForMap(sql, userId, startDate, endDate);

        return new UserActivitySummary(
                userId,
                ((Number) result.get("total_actions")).intValue(),
                ((Number) result.get("total_score")).intValue()
        );
    }

    @Tool(description = "Get users data summary from database")
    public List<UserActivitySummary> getUserAllActivity() {
        logger.info("Getting users all activity data");

        List<UserActivitySummary> userActivitySummaryList = new ArrayList<>();

        try {
            String sql = "SELECT user_id, score, created_date FROM user_activity";

            List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);


            if (result != null && !result.isEmpty()) {
                result.forEach(r -> {
                    userActivitySummaryList.add(new UserActivitySummary(
                            r.get("user_id").toString(),
                            ((Number) r.get("score")).intValue(),
                            r.get("created_date").toString()
                    ));
                });
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return userActivitySummaryList;
    }

    @Tool(description = "Get users data summary from database")
    public List<UserActivitySummary> getUserActivityById(String userId) {
        logger.info("Getting users all activity data by id: {}", userId);

        List<UserActivitySummary> userActivitySummaryList = new ArrayList<>();

        try {
            String sql = "SELECT user_id, score, created_date FROM user_activity where user_id = ?";

            List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, userId);


            if (result != null && !result.isEmpty()) {
                result.forEach(r -> {
                    userActivitySummaryList.add(new UserActivitySummary(
                            r.get("user_id").toString(),
                            ((Number) r.get("score")).intValue(),
                            r.get("created_date").toString()
                    ));
                });
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return userActivitySummaryList;
    }

    @Tool(description = "Get users data summary by ID from database")
    public UsersSummary getUsersById(String userId) {
        logger.info("Getting users data for: {}", userId);

        String sql = "SELECT * FROM users WHERE user_id = ?";

        Map<String, Object> result = jdbcTemplate.queryForMap(sql, userId);

        return new UsersSummary(
                userId,
                result.get("name").toString(),
                ((Number) result.get("age")).intValue()
        );
    }

    @Tool(description = "Get users data summary from database")
    public List<UsersSummary> getUsers() {
        logger.info("Getting users data");

        List<UsersSummary> usersSummaryList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM users";

            List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);


            if (result != null && !result.isEmpty()) {
                result.forEach(r -> {
                    usersSummaryList.add(new UsersSummary(
                            r.get("user_id").toString(),
                            r.get("name").toString(),
                            ((Number) r.get("age")).intValue()
                    ));
                });
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return usersSummaryList;
    }

    @Tool(description = "Get current system time in specified format")
    public String getCurrentTime(String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                format != null ? format : "yyyy-MM-dd HH:mm:ss"
        );
        return LocalDateTime.now().format(formatter);
    }
    public static class UserActivitySummary {
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
        public String getUserId() { return userId; }
        public int getTotalActions() { return totalActions; }
        public int getTotalScore() { return totalScore; }
        public String getCreatedDate() { return createdDate; }
    }

    public static class UsersSummary {
        private String userId;
        private String name;
        private int age;

        public UsersSummary(String userId, String name, int age) {
            this.userId = userId;
            this.name = name;
            this.age = age;
        }

        public String getUserId() {
            return userId;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}