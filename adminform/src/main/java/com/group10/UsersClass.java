package com.group10;

public class UsersClass {
    
    // Encapsulation
    String SCName, username, creatorID, adminID;
    
    // Constructor
    public UsersClass (String SCName, String username, String creatorID, String adminID) {
        this.SCName = SCName;
        this.username = username;
        this.creatorID = creatorID;
        this.adminID = adminID;
    }

    // Getter Methods
    public String getSCName() {
        return SCName;
    }
    public String getUsername() {
        return username;
    }
    public String getCreatorID() {
        return creatorID;
    }
    public String getAdminID() {
        return adminID;
    }

    // Setter Methods
    public void setSCName(String newSCName){
        this.SCName = newSCName;
    }
    public void setUsername (String newUsername){
        this.username = newUsername;
    }
    public void setCreatorID (String newCreatorID){
        this.creatorID = newCreatorID;
    }
    public void setadminID (String newAdminID){
        this.adminID = newAdminID;
    }
}