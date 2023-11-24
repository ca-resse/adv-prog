package com.group10;

public class SurveysClass {
    
    // Encapsulation
    String surveyTitle, surveyID, creatorName;
    Boolean blockedStatus, startedStatus;

    // Constructor
    public SurveysClass (Boolean blockedStatus, Boolean startedStatus, String surveyTitle, String surveyID, String creatorName) {
        this.blockedStatus = blockedStatus;
        this.startedStatus = startedStatus;
        this.surveyTitle = surveyTitle;
        this.surveyID = surveyID;
        this.creatorName = creatorName;
    }

    // Getter Methods
    public Boolean getBlockedStatus() {
        return blockedStatus;
    }
    public Boolean getStartedStatus() {
        return startedStatus;
    }
    public String getSurveyTitle() {
        return surveyTitle;
    }
    public String getSurveyID() {
        return surveyID;
    }
    public String getCreatorName() {
        return creatorName;
    }

    //Setter Methods
    public void SetBlockedStatus(Boolean newBlockedStatus){
        this.blockedStatus = newBlockedStatus;
    }
    public void setStartedStatus (Boolean newStartedStatus){
        this.startedStatus = newStartedStatus;
    }    
    public void SetSurveyTitle(String newSurveyTitle){
        this.surveyTitle = newSurveyTitle;
    }
    public void SetSurveyID(String newSurveyID){
        this.surveyID = newSurveyID;
    }
    public void SetCreatorName(String newCreatorName){
        this.creatorName = newCreatorName;
    }
}