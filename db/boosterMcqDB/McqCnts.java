package com.offlinew.practica.db.boosterMcqDB;

public class McqCnts {
    public String mcqId;
    public long correctCnt;
    public long incorrectCnt;
    public long notAttemptedCnt;

    public McqCnts( String mcqId, long correctCnt, long incorrectCnt, long notAttemptedCnt ) {
        this.notAttemptedCnt = notAttemptedCnt;
        this.incorrectCnt = incorrectCnt;
        this.correctCnt = correctCnt;
        this.mcqId = mcqId;
    }

    public String getMcqId() {
        return mcqId;
    }

    public void setMcqId(String mcqId) {
        this.mcqId = mcqId;
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
}
