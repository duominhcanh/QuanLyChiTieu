package com.hutechlab.model;

public class Activity {
    private int activityId;
    private String activityName;
    private int flowId;
    private String flowName;

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

    public Activity(int activityId, String activityName, int flowId, String flowName) {
        this.activityId = activityId;
        this.activityName = activityName;
        this.flowId = flowId;
        this.flowName = flowName;
    }

    public Activity() {
    }
}
