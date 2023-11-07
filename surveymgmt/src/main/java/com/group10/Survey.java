package com.group10;

public class Survey {
    int SurveyID;
    String SurveyTitle;
    String CreatorName;
    String SurveyDetails;
    
    // constructor
    public Survey(int id, String title, String name, String details){
        id = SurveyID;
        title = SurveyTitle;
        name = CreatorName;
        details = SurveyDetails; //short description of survey
    }

    // constructor (survey details parameter is optional)
    public Survey(int id, String title, String name){
        id = SurveyID;
        title = SurveyTitle;
        name = CreatorName;
    }

// Getter methods
    public int getSurveyID(){
        return SurveyID;
    }

    public String getSurveyTitle(){
        return SurveyTitle;
    }

    public String getCreatorName(){
        return CreatorName;
    }

    public String getSurveyDetails(){
        return SurveyDetails;
    }

// Setter methods
    public void setSurveyID(int newID){
        SurveyID = newID;
    }

    public void setSurveyTitle(String newTitle){
        SurveyTitle = newTitle;
    }

    public void setCreatorName(String newName){
        CreatorName = newName;
    }

    public void setSurveyDetails(String newDetails){
        SurveyDetails = newDetails;
    }

    
}
