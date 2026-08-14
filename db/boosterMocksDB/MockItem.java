package com.offlinew.practica.db.boosterMocksDB;

public class MockItem {
    public String topicId;
    public long timestamp;
    public long correctCnt;
    public long incorrectCnt;
    public long notAttemptedCnt;

    public long timeTakenMs;



    public MockItem(String topicId, long timestamp, long correctCnt, long incorrectCnt, long notAttemptedCnt, long timeTakenMs) {
        this.topicId = topicId;
        this.timestamp = timestamp;
        this.correctCnt = correctCnt;
        this.incorrectCnt = incorrectCnt;
        this.notAttemptedCnt = notAttemptedCnt;
        this.timeTakenMs = timeTakenMs;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getCorrectCnt() {
        return correctCnt;
    }

    public void setCorrectCnt(long correctCnt) {
        this.correctCnt = correctCnt;
    }

    public long getIncorrectCnt() {
        return incorrectCnt;
    }

    public void setIncorrectCnt(long incorrectCnt) {
        this.incorrectCnt = incorrectCnt;
    }

    public long getNotAttemptedCnt() {
        return notAttemptedCnt;
    }

    public void setNotAttemptedCnt(long notAttemptedCnt) {
        this.notAttemptedCnt = notAttemptedCnt;
    }

    public long getTimeTakenMs() {
        return timeTakenMs;
    }

    public void setTimeTakenMs(long timeTakenMs) {
        this.timeTakenMs = timeTakenMs;
    }
}
