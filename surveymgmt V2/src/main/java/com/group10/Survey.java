package com.group10;

public class Survey {
    int surveyID;
    String surveyTitle;
    String creatorName;
    String surveyDetails;
    boolean isStarted;
    boolean isBlocked;
    
    // constructor
    public Survey(int id, String title, String name, String details){
        surveyID = id;
        surveyTitle = title;
        creatorName = name;
        surveyDetails = details; //short description of survey
        isStarted = true; //visibility is set to started by default
        isBlocked = false; //surveys are not blocked by default
    }

    // constructor (survey details parameter is optional)
    public Survey(int id, String title, String name){
        surveyID = id;
        surveyTitle = title;
        creatorName = name;
        isStarted = true; //visibility is set to started by default
        isBlocked = false; //surveys are not blocked by default
    }

// Getter methods
    public int getSurveyID(){
        return surveyID;
    }

    public String getSurveyTitle(){
        return surveyTitle;
    }

    public String getCreatorName(){
        return creatorName;
    }

    public String getSurveyDetails(){
        return surveyDetails;
    }

    public boolean getIsStarted(){
        return isStarted;
    }

    public boolean getIsBlocked(){
        return isBlocked;
    }

// Setter methods
    public void setSurveyID(int newID){
        surveyID = newID;
    }

    public void setSurveyTitle(String newTitle){
        surveyTitle = newTitle;
    }

    public void setCreatorName(String newName){
        creatorName = newName;
    }

    public void setSurveyDetails(String newDetails){
        surveyDetails = newDetails;
    }

    public void setIsStarted(boolean status){
        isStarted = status;
    }

    public void setIsBlocked(boolean status){
        isBlocked = status;
    }

    //Class Methods (might be implemented from controller class)
    public void addNewSurvey(){

    }

    public void viewSurvey(){
        //might need to be implemented on the Survey Creator's screen
    }

    public void updateSurvey(){
        
    }
    
}
