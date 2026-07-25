package org.mcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

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
                (result.get("total_actions") != null ? ((Number) result.get("total_actions")).intValue() : 0),
                (result.get("total_score") != null ? ((Number) result.get("total_score")).intValue() : 0)
        );
    }

    @Tool(description = "Get all users data summary from database")
    public List<UserActivitySummary> getAllUserActivity() {
        logger.info("Getting all users activity data");

        List<UserActivitySummary> userActivitySummaryList = new ArrayList<>();

        try {
            String sql = "SELECT user_id, score, created_date FROM user_activity";

            List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);

            if (result != null && !result.isEmpty()) {
                result.forEach(r -> {
                    userActivitySummaryList.add(new UserActivitySummary(
                            r.get("user_id").toString(),
                            (r.get("score") != null ? ((Number) r.get("score")).intValue() : 0),
                            r.get("created_date").toString()
                    ));
                });
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return userActivitySummaryList;
    }

    @Tool(description = "Get users all activity data by id from database")
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
                            (r.get("score") != null ? ((Number) r.get("score")).intValue() : 0),
                            r.get("created_date").toString()
                    ));
                });
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return userActivitySummaryList;
    }

    @Tool(description = "Get users profile data by id from database")
    public UsersSummary getUsersById(String userId) {
        logger.info("Getting users profile data for: {}", userId);

        String sql = "SELECT * FROM users WHERE user_id = ?";

        Map<String, Object> result = jdbcTemplate.queryForMap(sql, userId);

        return new UsersSummary(
                userId,
                result.get("name").toString(),
                (result.get("age") != null ? ((Number) result.get("age")).intValue() : 0)
        );
    }

    @Tool(description = "Get all users profile data from database")
    public List<UsersSummary> getUsers() {
        logger.info("Getting users profile data");

        List<UsersSummary> usersSummaryList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM users";

            List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);

            if (result != null && !result.isEmpty()) {
                result.forEach(r -> {
                    usersSummaryList.add(new UsersSummary(
                            r.get("user_id").toString(),
                            r.get("name").toString(),
                            (r.get("age") != null ? ((Number) r.get("age")).intValue() : 0)
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

}