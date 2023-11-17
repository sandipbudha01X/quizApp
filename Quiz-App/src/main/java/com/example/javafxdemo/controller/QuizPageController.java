package com.example.javafxdemo.controller;

import com.opencsv.CSVReader;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizPageController {
    @FXML
    private Label questionNumberLabel;
    @FXML
    private Label questionLabel;
    @FXML
    private Button answerButton1;
    @FXML
    private Button answerButton2;
    @FXML
    private Button answerButton3;
    @FXML
    private Button answerButton4;
    @FXML
    private Label resultLabel;
    @FXML
    private Label correctAnswersLabel;
    @FXML
    private Label incorrectAnswersLabel;

    private List<QuizQuestion> quizQuestions = new ArrayList<>();
    private int currentQuestionIndex;
    private int correctAnswers = 0;
    private int incorrectAnswers = 0;

    private Timeline questionTimer;

    @FXML
    public void initialize() {
        loadQuizQuestions();
        currentQuestionIndex = 0;
        showNextQuestion();
        questionTimer = new Timeline(new KeyFrame(Duration.seconds(2), e -> showNextQuestion()));
        questionTimer.setCycleCount(1);
    }

    private void loadQuizQuestions() {
        String csvFilePath = "src/main/resources/quiz_question.csv";

        try {
            File csvFile = new File(csvFilePath);
            FileReader fileReader = new FileReader(csvFile);
            CSVReader csvReader = new CSVReader(fileReader);

            quizQuestions.clear();

            String[] line;
            while ((line = csvReader.readNext()) != null) {
                if (line.length == 6) {
                    String question = line[0];
                    String correctAnswer = line[1];
                    String option2 = line[2];
                    String option3 = line[3];
                    String option4 = line[4];
                    String options = line[5];

                    quizQuestions.add(new QuizQuestion(question, correctAnswer, option2, option3, option4, options));
                }
            }

            csvReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.shuffle(quizQuestions);

        if (quizQuestions.size() > 20) {
            quizQuestions = quizQuestions.subList(0, 20);
        }
    }

    private void showNextQuestion() {
        if (currentQuestionIndex < quizQuestions.size()) {
            QuizQuestion question = quizQuestions.get(currentQuestionIndex);

            questionNumberLabel.setText("Question " + (currentQuestionIndex + 1));
            questionLabel.setText(question.getQuestion());
            answerButton1.setText(question.getOption1());
            answerButton2.setText(question.getOption2());
            answerButton3.setText(question.getOption3());
            answerButton4.setText(question.getOption4());
            resultLabel.setText("");

            answerButton1.setDisable(false);
            answerButton2.setDisable(false);
            answerButton3.setDisable (false);
            answerButton4.setDisable(false);

            answerButton1.setStyle("");
            answerButton2.setStyle("");
            answerButton3.setStyle("");
            answerButton4.setStyle("");
        } else {
            questionLabel.setText("Quiz completed.");
            answerButton1.setDisable(true);
            answerButton2.setDisable(true);
            answerButton3.setDisable(true);
            answerButton4.setDisable(true);

            correctAnswersLabel.setText("Correct Answers: " + correctAnswers);
            incorrectAnswersLabel.setText("Incorrect Answers: " + incorrectAnswers);
        }
        currentQuestionIndex++;
    }

    @FXML
    private void checkAnswer(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String selectedAnswer = clickedButton.getText();
        QuizQuestion question = quizQuestions.get(currentQuestionIndex - 1);

        if (selectedAnswer.equals(question.getCorrectAnswer())) {
            correctAnswers++;
            resultLabel.setText("Correct!");
            clickedButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");
            answerButton1.setDisable(true);
            answerButton2.setDisable(true);
            answerButton3.setDisable(true);
            answerButton4.setDisable(true);
        } else {
            incorrectAnswers++;
            resultLabel.setText("Incorrect.");
            clickedButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");

            if (answerButton1.getText().equals(question.getCorrectAnswer())) {
                answerButton1.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");
            } else if (answerButton2.getText().equals(question.getCorrectAnswer())) {
                answerButton2.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");
            } else if (answerButton3.getText().equals(question.getCorrectAnswer())) {
                answerButton3.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");
            } else if (answerButton4.getText().equals(question.getCorrectAnswer())) {
                answerButton4.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");
            }

            answerButton1.setDisable(true);
            answerButton2.setDisable(true);
            answerButton3.setDisable(true);
            answerButton4.setDisable(true);
        }

        questionTimer.play();
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getIncorrectAnswers() {
        return incorrectAnswers;
    }
}