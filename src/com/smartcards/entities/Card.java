package com.smartcards.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Klasa Card predstavlja entitet koji će biti sačuvan u parceli.
 * Implementira interface Parcelable koji je neophodan za serializaciju podataka pri čuvanju i čitanju podataka iz parcele.
 * @author Bogdan Begovic
 */
public class Card implements Parcelable {

    private Long cardID;
    
    private String cardQuestion;
    
    private String cardAnswer;
    
    private float cardRatingTotal;
    
    private float cardNumRaters;
    
    private int cardStatus;
    
    private User user;
    
    private Subject subject;

    /**
     * Prazan konstruktor.
     */
    public Card() {
    }


    /**
     * Konstruktor koji prima podatke.
     *
     * @param cardID the card id
     * @param cardQuestion the card question
     * @param cardAnswer the card answer
     * @param cardRatingTotal the card rating total
     * @param cardNumRaters the card num raters
     * @param cardStatus the card status
     */
    public Card(Long cardID, String cardQuestion, String cardAnswer, float cardRatingTotal, float cardNumRaters, int cardStatus) {
        this.cardID = cardID;
        this.cardQuestion = cardQuestion;
        this.cardAnswer = cardAnswer;
        this.cardRatingTotal = cardRatingTotal;
        this.cardNumRaters = cardNumRaters;
        this.cardStatus = cardStatus;
    }

    
    /**
     * Konstruktor koji prima podatke.
     *
     * @param in the in
     */
    public Card(Parcel in) {
        readFromParcel(in);
    }

    
    /**
     * Metoda koja čita iz parcele.
     *
     * @param in the in
     */
    private void readFromParcel(Parcel in) {
        cardID = in.readLong();
        cardQuestion = in.readString();
        cardAnswer = in.readString();
        cardRatingTotal = in.readFloat();
        cardNumRaters = in.readFloat();
        cardStatus = in.readInt();
    }
    
    
    /** Metoda koja kreira parcelu za objekat. */
    public static final Parcelable.Creator<Card> CREATOR = new Parcelable.Creator<Card>() {
	    public Card createFromParcel(Parcel in) {
	     return new Card(in);
	    }

	    public Card[] newArray(int size) {
	     return new Card[size];
	    }
	  };

    /* (non-Javadoc)
     * @see android.os.Parcelable#describeContents()
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Metoda koja upisuje u parcelu.
     *
     * @param dest the dest
     * @param flags the flags
     * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        
        dest.writeLong(cardID);
        dest.writeString(cardQuestion);
        dest.writeString(cardAnswer);
        dest.writeFloat(cardRatingTotal);
        dest.writeFloat(cardNumRaters);
        dest.writeInt(cardStatus); 
     
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Object ID = " + cardID.toString();
    }

    /**
     * Gets the card id.
     *
     * @return the cardID
     */
    public Long getCardID() {
        return cardID;
    }

    /**
     * Sets the card id.
     *
     * @param cardID the cardID to set
     */
    public void setCardID(Long cardID) {
        this.cardID = cardID;
    }

    /**
     * Gets the card question.
     *
     * @return the cardQuestion
     */
    public String getCardQuestion() {
        return cardQuestion;
    }

    /**
     * Sets the card question.
     *
     * @param cardQuestion the cardQuestion to set
     */
    public void setCardQuestion(String cardQuestion) {
        this.cardQuestion = cardQuestion;
    }

    /**
     * Gets the card answer.
     *
     * @return the cardAnswer
     */
    public String getCardAnswer() {
        return cardAnswer;
    }

    /**
     * Sets the card answer.
     *
     * @param cardAnswer the cardAnswer to set
     */
    public void setCardAnswer(String cardAnswer) {
        this.cardAnswer = cardAnswer;
    }

    /**
     * Gets the card rating total.
     *
     * @return the cardRatingTotal
     */
    public float getCardRatingTotal() {
        return cardRatingTotal;
    }

    /**
     * Sets the card rating total.
     *
     * @param cardRatingTotal the cardRatingTotal to set
     */
    public void setCardRatingTotal(float cardRatingTotal) {
        this.cardRatingTotal = cardRatingTotal;
    }

    /**
     * Gets the card num raters.
     *
     * @return the cardNumRaters
     */
    public float getCardNumRaters() {
        return cardNumRaters;
    }

    /**
     * Sets the card num raters.
     *
     * @param cardNumRaters the cardNumRaters to set
     */
    public void setCardNumRaters(float cardNumRaters) {
        this.cardNumRaters = cardNumRaters;
    }

    /**
     * Gets the card status.
     *
     * @return the cardStatus
     */
    public int getCardStatus() {
        return cardStatus;
    }

    /**
     * Sets the card status.
     *
     * @param cardStatus the cardStatus to set
     */
    public void setCardStatus(int cardStatus) {
        this.cardStatus = cardStatus;
    }

    /**
     * Gets the user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user.
     *
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the subject.
     *
     * @return the subject
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * Sets the subject.
     *
     * @param subject the subject to set
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

}
