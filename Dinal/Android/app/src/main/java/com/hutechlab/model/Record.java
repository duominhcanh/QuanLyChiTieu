package com.hutechlab.model;

import java.io.Serializable;

public class Record implements Serializable {
      private int recordId;
    private String description;
    private String time;
    private int ammount;
    private int activityId;
    private String activityName;
    private int flowId;
    private String flowName;

    public Record(int recordId, String description, String time, int ammount, int activityId, String activityName, int flowId, String flowName) {
        this.recordId = recordId;
        this.description = description;
        this.time = time;
        this.ammount = ammount;
        this.activityId = activityId;
        this.activityName = activityName;
        this.flowId = flowId;
        this.flowName = flowName;
    }

    public Record() {
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String content) {
        this.description = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getAmmount() {
        return ammount;
    }

    public void setAmmount(int ammount) {
        this.ammount = ammount;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getFlowId() {
        return flowId;
    }

    public void setFlowId(int flowId) {
        this.flowId = flowId;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }
}
