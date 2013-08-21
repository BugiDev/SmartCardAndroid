package com.smartcards.entities;

/**
 * Klasa Subject predstavlja entitet Subject koji se dobija preko web servisa.
 * @author Bogdan Begovic
 */
public class Subject {

    private Long subjectID;
    
    private String subjectName;
    
    private boolean subjectDeleted;

    /**
     * Prazan konstruktor.
     */
    public Subject() {
    }
    
    /**
     * Konstruktor koji prima podatke
     *
     * @param subjectID the subject id
     * @param subjectName the subject name
     * @param subjectDeleted the subject deleted
     */
    public Subject(Long subjectID, String subjectName, boolean subjectDeleted) {
	this.subjectID = subjectID;
	this.subjectName = subjectName;
	this.subjectDeleted = subjectDeleted;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Object ID = " + subjectID.toString();
    }

    /**
     * Gets the subject id.
     *
     * @return the subjectID
     */
    public Long getSubjectID() {
        return subjectID;
    }

    /**
     * Sets the subject id.
     *
     * @param subjectID the subjectID to set
     */
    public void setSubjectID(Long subjectID) {
        this.subjectID = subjectID;
    }

    /**
     * Gets the subject name.
     *
     * @return the subjectName
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * Sets the subject name.
     *
     * @param subjectName the subjectName to set
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    /**
     * Checks if is subject deleted.
     *
     * @return the subjectDeleted
     */
    public boolean isSubjectDeleted() {
        return subjectDeleted;
    }

    /**
     * Sets the subject deleted.
     *
     * @param subjectDeleted the subjectDeleted to set
     */
    public void setSubjectDeleted(boolean subjectDeleted) {
        this.subjectDeleted = subjectDeleted;
    }
}
