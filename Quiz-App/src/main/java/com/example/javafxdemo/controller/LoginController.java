package com.example.javafxdemo.controller;

import com.example.javafxdemo.Application;
import com.opencsv.CSVReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

public class LoginController {
    @FXML
    private TextField userEmail;
    @FXML
    private PasswordField userPassword;
    @FXML
    private Label resultLabel;

    private Application application;
    @FXML
    private Button switchToRegisterButton;

    @FXML
    private void switchToRegister(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("register-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) switchToRegisterButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    public void setApplication(Application application) {
        this.application = application;
    }

    public void login(ActionEvent event) throws IOException {
        String email = userEmail.getText();
        String password = userPassword.getText();
        if (validateLogin(email, password)) {
            String accountName = getAccountName(email);

            resultLabel.setText("Login Successful!!");
            resultLabel.getStyleClass().clear();
            resultLabel.getStyleClass().add("login-success");

            application.showDashboard(accountName);
        } else {
            resultLabel.setText("Invalid email or password");
            resultLabel.getStyleClass().clear();
            resultLabel.getStyleClass().add("login-error");
        }
    }

    public boolean validateLogin(String email, String password) {
        String csvPath = "src/main/resources/userData.csv";
        try (CSVReader reader = new CSVReader(new FileReader(csvPath))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                if (line.length == 7 && line[1].trim().equalsIgnoreCase(email) && passwordVerification(line[6].trim(), password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            logger.severe("Error reading CSV file: " + e.getMessage());
        }
        return false;
    }

    public boolean passwordVerification(String storedHash, String password) {
        try {
            return BCrypt.checkpw(password, storedHash);
        } catch (IllegalArgumentException e) {
            logger.severe("Password verification error: " + e.getMessage());
        }
        return false;
    }

    public String getAccountName(String email) {
        String accountName = "";

        String csvPath = "src/main/resources/userData.csv";
        try (CSVReader reader = new CSVReader(new FileReader(csvPath))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                if (line.length == 7 && line[1].trim().equalsIgnoreCase(email)) {
                    accountName = line[0].trim();
                    break;
                }
            }
        } catch (IOException e) {
            logger.severe("Error reading CSV file: " + e.getMessage());
        }
        return accountName;
    }
}
