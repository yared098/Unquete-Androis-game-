package com.example.unquote;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;

public class oldMain extends AppCompatActivity {

    int currentQuestionIndex;
    int totalCorrect;
    int totalQuestions;
    ArrayList<Question> questions;
    // Array of images (you can add more images)


    // Declare View member variables
    private TextView questionTextView;
    private Button answerButton1;
    private Button answerButton2;
    private Button answerButton3;
    private Button answerButton4;
    private Button submitButton;
    private TextView scoreTextView;
    private TextView questionsRemainingTextView;

    private MediaPlayer mediaPlayer; // MediaPlayer for playing audio
    private TextView feedbackTextView;  // New TextView for feedback

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();


        // Show app icon in ActionBar
        if (actionBar != null) {
            actionBar.setTitle("Unqoute App");
            actionBar.setIcon(R.drawable.ic_unquote_icon);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setLogo(R.drawable.ic_unquote_icon);  // Use the correct icon resource
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setElevation(0);

        }

        setContentView(R.layout.activity_main);

        // Assign View member variables
        questionTextView = findViewById(R.id.tv_main_question_title);
        VideoView videoview = (VideoView) findViewById(R.id.video_view);
        videoview.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.video1);
        MediaController mediaController=new MediaController(this);
        mediaController.setAnchorView(mediaController);
        videoview.setMediaController(mediaController);
        // Start the video automatically when it's prepared
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoview.start(); // Start the video automatically
            }
        });


        answerButton1 = findViewById(R.id.btn_main_answer_0);
        answerButton2 = findViewById(R.id.btn_main_answer_1);
        answerButton3 = findViewById(R.id.btn_main_answer_2);
        answerButton4 = findViewById(R.id.btn_main_answer_3);
        submitButton=findViewById(R.id.btn_main_submit_answer);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Proceed to the next question after a short delay
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        onAnswerSubmission();
//                    }
//                }, 1000);
                // 1.5 second delay before showing the next question
                onAnswerSubmission();
            }
        });

        scoreTextView = findViewById(R.id.tv_main_questions_remaining_count);

        questionsRemainingTextView = findViewById(R.id.tv_main_questions_remaining);
        feedbackTextView = findViewById(R.id.tv_main_questions_remaining);  // New TextView for feedback
        // Initialize MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.game_play); // Replace with your audio resource
        mediaPlayer.setLooping(true);



        // Set onClickListener for each answer Button
        answerButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAnswerSelected(0,answerButton1);

            }
        });
        answerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAnswerSelected(1,answerButton2);
            }
        });
        answerButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAnswerSelected(2,answerButton3);
            }
        });
        answerButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAnswerSelected(3,answerButton4);
            }
        });

        // Start a new game
        playAudio();
        startNewGame();
    }

    private void playAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.start(); // Start playing audio
        }
    }

    private void displayQuestion(Question question) {
        questionTextView.setText(question.getQuestionText());
        answerButton1.setText(question.getAnswer0());
        answerButton2.setText(question.getAnswer2());
        answerButton3.setText(question.getAnswer3());
        answerButton4.setText(question.getAnswer3());  // Fixed to display the correct answer 4
    }

    private void displayQuestionsRemaining(int questionsRemaining) {
        questionsRemainingTextView.setText("Questions Remaining: " + questionsRemaining);
    }

    private void onAnswerSelected(int answerSelected,Button button) {

        Question currentQuestion = getCurrentQuestion();

        // Disable buttons after selecting an answer
        answerButton1.setEnabled(false);
        answerButton2.setEnabled(false);
        answerButton3.setEnabled(false);
        answerButton4.setEnabled(false);

        // Show feedback: Correct or Incorrect
        if (answerSelected == currentQuestion.getCorrectAnswer()) {
            feedbackTextView.setText("Correct! ✔ ✔");
            // make the button name add corrected icon
            button.setText(" Correct! ✔ ✔");




            totalCorrect++;
        } else {
            scoreTextView.setText(String.valueOf(totalCorrect));
            feedbackTextView.setText(currentQuestion.getCorrectAnswer()+"❌ ❎! The correct answer was: " );
        }

        // Proceed to the next question after a short delay
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                onAnswerSubmission();
//            }
//        }, 1000);
        // 1.5 second delay before showing the next question
    }

    private void onAnswerSubmission() {
        // Remove the answered question
        Question currentQuestion = getCurrentQuestion();
        questions.remove(currentQuestion);

        // Display questions remaining
        displayQuestionsRemaining(questions.size());

        // If no more questions remain, show Game Over
        if (questions.size() == 0) {
            String gameOverMessage = getGameOverMessage(totalCorrect, totalQuestions);
            showGameOverDialog(gameOverMessage);
        } else {
            // Choose the next question and display it
            displayQuestion(chooseNewQuestion());
            // Re-enable the buttons for the next question
            enableAnswerButtons();
            // Clear the feedback text
            feedbackTextView.setText("");
        }
    }

    private void enableAnswerButtons() {
        answerButton1.setEnabled(true);
        answerButton2.setEnabled(true);
        answerButton3.setEnabled(true);
        answerButton4.setEnabled(true);
    }

    private void showGameOverDialog1(String gameOverMessage) {
        new AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setMessage(gameOverMessage)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        startNewGame(); // Start a new game after the dialog is closed
                    }
                })
                .show();
    }
    private void showGameOverDialog(String gameOverMessage) {
        // Create the game over dialog
        new AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setMessage(gameOverMessage)
                .setCancelable(false) // Prevent closing the dialog by tapping outside
                .setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startNewGame();  // Start a new game after the dialog is closed
                    }
                })
                .show();
    }


    private void startNewGame() {
        questions = new ArrayList<>();

        // Add some sample questions
        Question question0 = new Question(0, "Pretty good advice, and perhaps a scientist did say it. Who actually did?", "Albert Einstein", "Isaac Newton", "Rita Mae Brown", "Rosalind Franklin", 2);
        Question question1 = new Question(1, "Was honest Abe honestly quoted? Who authored this pithy bit of wisdom?", "Edward Stieglitz", "Maya Angelou", "Abraham Lincoln", "Ralph Waldo Emerson", 0);
        Question question2 = new Question(2, "Easy advice to read, difficult advice to follow. Who actually said this?", "Martin Luther King Jr", "Mother Theresa", "Fred Rogers", "Oprah Winfrey", 1);
        Question question3 = new Question(3, "Insanely inspiring, insanely incorrect (maybe). Who is the true source of this inspiration?", "Nelson Mandela", "Harriet Tubman", "Mahatma Gandhi", "Nicholas Klein", 3);
        Question question4 = new Question(4, "A peace worth striving for - who actually reminded us of this?", "Malala Yousafzai", "Martin Luther King Jr", "Liu Xiaobo", "Dalai Lama", 1);
        Question question5 = new Question(5, "Unfortunately, true - but did Marilyn Monroe convey it or did someone else?", "Laurel Thatcher Ulrich", "Eleanor Roosevelt", "Marilyn Monroe", "Queen Victoria", 0);
//        add new file
        Question question6 = new Question(6, "Here’s the truth, Will Smith \u200Bdid\n" + "say this, but in which movie?", "Independence day ","Bad Boys ", "Men In Black " ,"The Pursuit of Happyness", 2);
        Question question7 = new Question(7, "Which TV funny gal actually\n" + "quipped this 1-liner?", "Ellen Degeneres", "Betty White", "Betty White", "Tina Fay", 3);

        questions.add(question0);
        questions.add(question1);
        questions.add(question2);
        questions.add(question3);
        questions.add(question4);
        questions.add(question5);
        questions.add(question6);
        questions.add(question7);

        totalCorrect = 0;
        totalQuestions = questions.size();

        // Display the first question
        displayQuestionsRemaining(questions.size());
        displayQuestion(chooseNewQuestion());
    }

    private Question chooseNewQuestion() {
        int newQuestionIndex = generateRandomNumber(questions.size());
        currentQuestionIndex = newQuestionIndex;
        return questions.get(currentQuestionIndex);
    }

    private int generateRandomNumber(int max) {
        double randomNumber = Math.random();
        double result = max * randomNumber;
        return (int) result;
    }

    private Question getCurrentQuestion() {
        return questions.get(currentQuestionIndex);
    }

    private String getGameOverMessage(int totalCorrect, int totalQuestions) {
        if (totalCorrect == totalQuestions) {
            return "You got all " + totalQuestions + " right! You won!";
        } else {
            return "You got " + totalCorrect + " right out of " + totalQuestions + ". Better luck next time!";
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Start the music when the app is brought into the foreground
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Stop the music when the app goes into the background
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        // Release the MediaPlayer when the activity is completely stopped
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        // Optionally, you can start the music again if it was paused
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }
}

