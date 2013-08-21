package com.smartcards.entities;

import java.util.Date;

/**
 * Klasa User predstavlja entitet User koji se dobija preko web servisa.
 * @author Bogdan Begovic
 */
public class User {
    
    private Long userID;
    
    private String username;
    
    private String password;
    
    private String email;
    
    private String firstname;
    
    private String lastname;
    
    private Date birthday;
    
    private int roleType;
    
    private boolean userConfirmed;
    
    private int dailyCounter;
    
    private Date lastLogedIn;
    
    private boolean userActive;

    /**
     * Prazan konstruktor.
     */
    public User() {
    }

    /**
     * Konstruktor koji prima podatke
     *
     * @param username the username
     * @param password the password
     * @param email the email
     * @param firstname the firstname
     * @param lastname the lastname
     * @param birthday the birthday
     * @param roleType the role type
     * @param userConfirmed the user confirmed
     * @param dailyCounter the daily counter
     * @param lastLogedIn the last loged in
     * @param userActive the user active
     */
    public User(String username, String password, String email, String firstname, String lastname, Date birthday, int roleType,
	    boolean userConfirmed, int dailyCounter, Date lastLogedIn, boolean userActive) {
	this.username = username;
	this.password = password;
	this.email = email;
	this.firstname = firstname;
	this.lastname = lastname;
	this.birthday = birthday;
	this.roleType = roleType;
	this.userConfirmed = userConfirmed;
	this.dailyCounter = dailyCounter;
	this.lastLogedIn = lastLogedIn;
	this.userActive = userActive;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "Object ID = " + userID.toString();
    }

    /**
     * Gets the user id.
     *
     * @return the userID
     */
    public Long getUserID() {
	return userID;
    }

    /**
     * Sets the user id.
     *
     * @param userID the userID to set
     */
    public void setUserID(Long userID) {
	this.userID = userID;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
	return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
	this.username = username;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
	return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
	this.password = password;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
	return email;
    }

    /**
     * Sets the email.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
	this.email = email;
    }

    /**
     * Gets the firstname.
     *
     * @return the firstname
     */
    public String getFirstname() {
	return firstname;
    }

    /**
     * Sets the firstname.
     *
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
	this.firstname = firstname;
    }

    /**
     * Gets the lastname.
     *
     * @return the lastname
     */
    public String getLastname() {
	return lastname;
    }

    /**
     * Sets the lastname.
     *
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
	this.lastname = lastname;
    }

    /**
     * Gets the birthday.
     *
     * @return the birthday
     */
    public Date getBirthday() {
	return birthday;
    }

    /**
     * Sets the birthday.
     *
     * @param birthday the birthday to set
     */
    public void setBirthday(Date birthday) {
	this.birthday = birthday;
    }

    /**
     * Gets the role type.
     *
     * @return the roleType
     */
    public int getRoleType() {
	return roleType;
    }

    /**
     * Sets the role type.
     *
     * @param roleType the roleType to set
     */
    public void setRoleType(int roleType) {
	this.roleType = roleType;
    }

    /**
     * Gets the user confirmed.
     *
     * @return the userConfirmed
     */
    public boolean getUserConfirmed() {
	return userConfirmed;
    }

    /**
     * Sets the user confirmed.
     *
     * @param userConfirmed the userConfirmed to set
     */
    public void setUserConfirmed(boolean userConfirmed) {
	this.userConfirmed = userConfirmed;
    }

    /**
     * Gets the daily counter.
     *
     * @return the dailyCounter
     */
    public int getDailyCounter() {
	return dailyCounter;
    }

    /**
     * Sets the daily counter.
     *
     * @param dailyCounter the dailyCounter to set
     */
    public void setDailyCounter(int dailyCounter) {
	this.dailyCounter = dailyCounter;
    }

    /**
     * Gets the last loged in.
     *
     * @return the lastLogedIn
     */
    public Date getLastLogedIn() {
	return lastLogedIn;
    }

    /**
     * Sets the last loged in.
     *
     * @param lastLogedIn the lastLogedIn to set
     */
    public void setLastLogedIn(Date lastLogedIn) {
	this.lastLogedIn = lastLogedIn;
    }
    
    /**
     * Checks if is user active.
     *
     * @return the userActive
     */
    public boolean isUserActive() {
	return userActive;
    }

    /**
     * Sets the user active.
     *
     * @param userActive the userActive to set
     */
    public void setUserActive(boolean userActive) {
	this.userActive = userActive;
    }

}
