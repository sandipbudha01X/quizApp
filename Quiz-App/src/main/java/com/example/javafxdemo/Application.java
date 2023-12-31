// Application.java
package com.example.javafxdemo;

import com.example.javafxdemo.controller.DashboardController;
import com.example.javafxdemo.controller.LoginController;
import com.example.javafxdemo.controller.RegisterController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class Application extends javafx.application.Application {
    private Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        registerScene();
    }

    public void loginScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets().add(getClass().getResource("css/login.css").toExternalForm());
        stage.setTitle("QUIZ APP!!");
        stage.setScene(scene);
        stage.show();

        LoginController loginController = fxmlLoader.getController();
        loginController.setApplication(this);
    }

    public void registerScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("register-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 750);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets().add(getClass().getResource("css/register.css").toExternalForm());
        stage.setTitle("QUIZ APP!");
        stage.setScene(scene);
        stage.show();

        RegisterController registerController = fxmlLoader.getController();
        registerController.setApplication(this);
    }

    public void showDashboard(String accountName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("dashboard-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets().add(getClass().getResource("css/dashboard.css").toExternalForm());
        stage.setTitle("QUIZ APP!!");
        stage.setScene(scene);
        stage.show();

        DashboardController dashboardController = fxmlLoader.getController();
        dashboardController.setAccountName(accountName);
    }

    public static void main(String[] args) {
        launch();
    }
}
