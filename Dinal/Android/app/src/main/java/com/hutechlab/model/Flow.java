package com.hutechlab.model;

public class Flow {
    private int flowId;
    private String flowName;

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

    public Flow(int flowId, String flowName) {
        this.flowId = flowId;
        this.flowName = flowName;
    }

    public Flow() {
    }
}
