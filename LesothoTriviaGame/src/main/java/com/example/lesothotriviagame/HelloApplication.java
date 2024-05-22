package com.example.lesothotriviagame;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private int score = 0;
    private int questionIndex = 0;

    private final String[] questions = {
            "What is the highest point in Lesotho?",
            "Which traditional Basotho garment is commonly worn by both men and women?",
            "Who was the founder of the Basotho nation?",
            "Who is the father of King Letsie the 3rd?",
            "Which animal is native to Lesotho and is also depicted on the country's coat of arms?"
    };

    private final String[] correctAnswers = {
            "Thabana-Ntlenyana",
            "Mokorotlo",
            "King Moshoeshoe I",
            "King Moshoeshoe II",
            "Crocodile"
    };

    private final String[][] options = {
            {"Mount Qiloane", "Thaba-Bosiu", "Thabana-Ntlenyana", "Sani Pass"},
            {"Kobo", "Mokorotlo", "Lesiba", "Tseea"},
            {"Mokhachane", "Lepoqo", "Mohlomi", "King Moshoeshoe I"},
            {"King Moshoeshoe I", "King Letsie III", "King Moshoeshoe II", "King Mosothoane"},
            {"Snake", "Lion", "Crocodile", "Giraffe"}
    };

    // File paths for images
    private final String[] mediaFiles = {
            "/images/Thabana-Ntlenyana.jpg",
            "/images/Mokorotlo oa Basotho.jpg",
            "/images/King Moshoeshoe1.jpg",
            "/images/MOSHOESHOE2.jpg",
            "/images/crocodile.jpg"
    };

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Lesotho Trivia Game");

        Label questionLabel = new Label();
        ImageView imageView = new ImageView();
        Label feedbackLabel = new Label();

        Button[] optionButtons = new Button[4];
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i] = new Button();
            optionButtons[i].setStyle("-fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
            int finalI1 = i;
            optionButtons[i].setOnMouseEntered(e -> optionButtons[finalI1].setStyle("-fx-background-color: #45a049; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-text-fill: white;"));
            optionButtons[i].setOnMouseExited(e -> optionButtons[finalI1].setStyle("-fx-background-color: #4CAF50; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-text-fill: white;"));
        }

        Button nextButton = new Button("Next Question");
        nextButton.setStyle("-fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-color: BLACK; -fx-text-fill: white;");
        nextButton.setOnMouseEntered(e -> nextButton.setStyle("-fx-background-color: #45a049; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-text-fill: white;"));
        nextButton.setOnMouseExited(e -> nextButton.setStyle("-fx-background-color: #4CAF50; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-text-fill: white;"));

        nextButton.setOnAction(e -> {
            if (questionIndex < questions.length - 1) {
                questionIndex++;
                showQuestion(questionLabel, imageView, optionButtons, feedbackLabel, nextButton);
            } else {
                showScore(primaryStage);
            }
        });

        VBox questionBox = new VBox(10);
        questionBox.setAlignment(Pos.CENTER);
        questionBox.getChildren().addAll(questionLabel, imageView, feedbackLabel);

        HBox optionsBox = new HBox(10);
        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.getChildren().addAll(optionButtons);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(nextButton);

        BorderPane layout = new BorderPane();
        layout.setTop(questionBox);
        layout.setCenter(optionsBox);
        layout.setBottom(buttonBox);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f0f0f0;");

        Scene scene = new Scene(layout, 600, 400);

        Button startButton = new Button("Start");
        startButton.setStyle("-fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        startButton.setOnMouseEntered(e -> startButton.setStyle("-fx-background-color: #45a049; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-text-fill: white;"));
        startButton.setOnMouseExited(e -> startButton.setStyle("-fx-background-color: #4CAF50; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-text-fill: white;"));
        startButton.setOnAction(e -> {
            questionIndex = 0;
            score = 0;
            showQuestion(questionLabel, imageView, optionButtons, feedbackLabel, nextButton);
            primaryStage.setScene(scene);
        });

        VBox welcomeBox = new VBox(10);
        welcomeBox.setAlignment(Pos.CENTER);
        welcomeBox.getChildren().addAll(new Label(" Lesotho Trivia Games:Welcome"), startButton);

        Scene welcomeScene = new Scene(welcomeBox, 600, 400);

        primaryStage.setScene(welcomeScene);
        primaryStage.show();
    }

    private void showQuestion(Label questionLabel, ImageView imageView, Button[] optionButtons, Label feedbackLabel, Button nextButton) {
        questionLabel.setText(questions[questionIndex]);

        // Load and resize the image
        Image image = new Image(getClass().getResourceAsStream(mediaFiles[questionIndex]));
        if (image.isError()) {
            System.out.println("Error loading image: " + mediaFiles[questionIndex]);
        }
        imageView.setImage(image);
        imageView.setFitWidth(200); // Set desired width
        imageView.setPreserveRatio(true); // Preserve aspect ratio

        // Set options
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setText(options[questionIndex][i]);
            optionButtons[i].setDisable(false); // Re-enable the button
            int index = i; // Capture the value of i
            optionButtons[i].setOnAction(e -> checkAnswer(optionButtons[index].getText(), optionButtons, feedbackLabel, nextButton));
        }

        // Initially disable "Next Question" until an answer is selected
        nextButton.setDisable(true);
    }

    private void checkAnswer(String selectedAnswer, Button[] optionButtons, Label feedbackLabel, Button nextButton) {
        String correctAnswer = correctAnswers[questionIndex];
        boolean isCorrect = selectedAnswer.equals(correctAnswer);

        feedbackLabel.setText(isCorrect ? "Correct" : "Wrong Answer");
        feedbackLabel.setTextFill(isCorrect ? javafx.scene.paint.Color.GREEN : javafx.scene.paint.Color.RED);

        // Disable all option buttons
        for (Button button : optionButtons) {
            button.setDisable(true);
        }

        // Enable the "Next Question" button once an answer is selected
        nextButton.setDisable(false);

        // If correct, update the score
        if (isCorrect) {
            score++;
        }
    }

    private void showScore(Stage primaryStage) {
        Label scoreLabel = new Label("Your score: " + score + "/" + questions.length);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> primaryStage.close());

        VBox scoreBox = new VBox(10);
        scoreBox.setAlignment(Pos.CENTER);
        scoreBox.getChildren().addAll(scoreLabel, closeButton);

        Scene scoreScene = new Scene(scoreBox, 600, 400);
        primaryStage.setScene(scoreScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
