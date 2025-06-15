package com.example.controllers;

import java.util.Optional;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class StartScreenController {

    public void onEntrant(ActionEvent event) { 
        
        openNextScreen(event, "/FindEntrantsBySpecialtyScreen.fxml"); 
    }

    public void onEntrantByFaculty(ActionEvent event) {
        openNextScreen(event, "/FindEntrantByFacultyScreen.fxml");
    }

    public void onNewScore(ActionEvent event) {
        openNextScreen(event, "/UpdateSpecialityScoreScreen.fxml");
    }

    public void onDeleteEntrant(ActionEvent event) {
        openNextScreen(event, "/DeleteApplicationScreen.fxml");
    }

    public void onAddNewSpeciality(ActionEvent event) {
        openNextScreen(event, "/AddSpecialityScreen.fxml");
    }

    public void onCustomQuery(ActionEvent event) {
        openNextScreen(event, "/CustomQueryScreen.fxml");
    }

    public void onFacultyBySpeciality(ActionEvent event) {
        openNextScreen(event, "/FindFacultiesBySpecialtyScreen.fxml");
    }

    public void onDeleteFaculty(ActionEvent event) {
        openNextScreen(event, "/DeleteFaculty.fxml");
    }

    public void onEntrantsWithGoldMedal(ActionEvent event) {
        openNextScreen(event, "/FindAchievementsEntrantsControllerScreen.fxml");
    }

    public void onCreateOrderOfEnrollmentScreen(ActionEvent event) {
        openNextScreen(event, "/CreateOrderOfEnrollmentScreen.fxml");
    }

    public void onExamAverageScoreController(ActionEvent event) {
        openNextScreen(event, "/ExamAverageScoreScreen.fxml");
    }

    private void openNextScreen(ActionEvent event, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load(), 1200, 800);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void onHandleExit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение выхода");
        alert.setHeaderText(null);
        alert.setContentText("Вы действительно хотите выйти?");

        ButtonType yesButton = new ButtonType("Да", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("Нет", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == yesButton) {
            Platform.exit();
        }
    }
}
