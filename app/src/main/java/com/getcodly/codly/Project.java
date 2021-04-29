package com.getcodly.codly;

public class Project {
    private String projectName;
    private String projectType;
    private String projectCode;

    public Project(String projectName, String projectType, String projectCode){
        this.projectName = projectName;
        this.projectType = projectType;
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getProjectType() {
        return projectType;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }
}
