package org.mcp;

public class UsersSummary {
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
