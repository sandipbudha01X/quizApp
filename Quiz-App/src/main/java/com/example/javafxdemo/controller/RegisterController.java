package com.example.javafxdemo.controller;

import com.example.javafxdemo.Application;
import com.opencsv.CSVWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RegisterController {
    @FXML
    private TextField userName;
    @FXML
    private TextField email;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private ComboBox<String> userGender;
    @FXML
    private ComboBox<String> userNationality;
    @FXML
    private TextField userAddress;
    @FXML
    private PasswordField userPassword;
    @FXML
    private Button registerBtn;
    @FXML
    private Label emailError;
    @FXML
    private Button switchToLoginButton;

    private Application application;
    private boolean isFormValid;



    public void setApplication(Application application) {
        this.application = application;
    }

    public void register(ActionEvent event) throws IOException {
        String name = userName.getText();
        String userEmail = email.getText();
        String birthYear = dateOfBirth.getValue().toString();
        String gender = userGender.getValue();
        String nationality = userNationality.getValue();
        String address = userAddress.getText();
        String password = userPassword.getText();
        String pathToCSV = "src/main/resources/userData.csv";

        if (userEmail.isEmpty()) {
            emailError.setText("Email can't be empty");
            email.getStyleClass().add("error-background");
            isFormValid = false;
        } else {
            isFormValid = true;
        }

        if (isFormValid) {
            try {
                FileWriter fileWriter = new FileWriter(pathToCSV, true);
                CSVWriter csvWriter = new CSVWriter(fileWriter);

                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

                String[] csvData = {name, userEmail, birthYear, gender, nationality, address, hashedPassword};
                csvWriter.writeNext(csvData);
                csvWriter.close();
                clearFormElements();
            } catch (IOException e) {
                e.printStackTrace();
            }
            application.loginScene();
        }
    }

    @FXML
    private void switchToLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("login-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) switchToLoginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearFormElements() {
        userName.clear();
        email.clear();
        dateOfBirth.setValue(null);
        userGender.getSelectionModel().clearSelection();
        userNationality.getSelectionModel().clearSelection();
        userAddress.clear();
        userPassword.clear();
    }
}
