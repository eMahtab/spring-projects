package net.mahtabalam.model;

public class Tweet {
    private String id;
    private String username;
    private String content;
    private int likeCount;
    private int retweetCount;
    private String timestamp;

    public Tweet() {
    }

    public Tweet(String id, String username, String content, int likeCount, int retweetCount, String timestamp) {
        this.id = id;
        this.username = username;
        this.content = content;
        this.likeCount = likeCount;
        this.retweetCount = retweetCount;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", content='" + content + '\'' +
                ", likeCount=" + likeCount +
                ", retweetCount=" + retweetCount +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
