package com.group10;

public class Survey {
    int surveyID;
    String surveyTitle;
    String creatorName;
    String surveyDetails;
    
    // constructor
    public Survey(int id, String title, String name, String details){
        surveyID = id;
        surveyTitle = title;
        creatorName = name;
        surveyDetails = details; //short description of survey
    }

    // constructor (survey details parameter is optional)
    public Survey(int id, String title, String name){
        surveyID = id;
        surveyTitle = title;
        creatorName = name;
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

    public void addNewSurvey(){

    }

    public void viewSurvey(){
        //might need to be implemented on the Survey Creator's screen
    }

    public void updateSurvey(){
        
    }
    
}
