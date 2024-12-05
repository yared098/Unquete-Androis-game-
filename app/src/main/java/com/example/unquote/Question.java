package com.example.unquote;

public class Question {

    /* COPY: Begin here */

    private int imageId;
    private String questionText;
    private String answer0;
    private String answer1;
    private String answer2;
    private String answer3;
    private int correctAnswer;
    private int playerAnswer;

    // Constructor
    public Question(int imageIdentifier,
                    String questionString,
                    String answerZero,
                    String answerOne,
                    String answerTwo,
                    String answerThree,
                    int correctAnswerIndex) {
        this.imageId = imageIdentifier;
        this.questionText = questionString;
        this.answer0 = answerZero;
        this.answer1 = answerOne;
        this.answer2 = answerTwo;
        this.answer3 = answerThree;
        this.correctAnswer = correctAnswerIndex;
        this.playerAnswer = -1; // Default value indicating no answer selected yet
    }

    // Getter and Setter methods for all fields

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswer0() {
        return answer0;
    }

    public void setAnswer0(String answer0) {
        this.answer0 = answer0;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getPlayerAnswer() {
        return playerAnswer;
    }

    public void setPlayerAnswer(int playerAnswer) {
        this.playerAnswer = playerAnswer;
    }

    // Check if the player's answer is correct
    public boolean isCorrect() {
        return playerAnswer == correctAnswer;
    }

    /* COPY: End here */

}
