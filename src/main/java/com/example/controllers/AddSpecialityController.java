package com.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.example.Main;
import com.example.entities.Application;
import com.example.entities.Documents;
import com.example.entities.Entrant;
import com.example.entities.EntrantAchievements;
import com.example.entities.Exam;
import com.example.entities.ExamResults;
import com.example.entities.Faculty;
import com.example.entities.OrderOfEnrollment;
import com.example.entities.Speciality;

public class AddSpecialityController {

    @FXML private TextField specialtyNameField;
    @FXML private TextField specialtyScoreField;
    @FXML private Button addButton;
    @FXML private Button backButton;
    @FXML private Label statusLabel;
    private SessionFactory sessionFactory;

    @FXML
    public void initialize() {
        initializeHibernate();
    }

    private void initializeHibernate() {
        try {
            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/commission");
            configuration.setProperty("hibernate.connection.username", "postgres");
            configuration.setProperty("hibernate.connection.password", "admin");
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.format_sql", "true");
            configuration.setProperty("hibernate.highlight_sql", "true");

            configuration.addAnnotatedClass(Application.class)
                    .addAnnotatedClass(Documents.class)
                    .addAnnotatedClass(Entrant.class)
                    .addAnnotatedClass(EntrantAchievements.class)
                    .addAnnotatedClass(Exam.class)
                    .addAnnotatedClass(ExamResults.class)
                    .addAnnotatedClass(Faculty.class)
                    .addAnnotatedClass(OrderOfEnrollment.class)
                    .addAnnotatedClass(Speciality.class);

            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            sessionFactory = configuration.buildSessionFactory(registry);
        } catch (Exception e) {
            Main.showAlert("Ошибка", "Не удалось инициализировать Hibernate: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdd() {
        String specialtyName = specialtyNameField.getText().trim();
        String scoreText = specialtyScoreField.getText().trim();

        if (specialtyName.isEmpty() || scoreText.isEmpty()) {
            Main.showAlert("Ошибка", "Все поля должны быть заполнены!");
            return;
        }

        try {
            int score = Integer.parseInt(scoreText);
            
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                
                Speciality newSpeciality = new Speciality();
                newSpeciality.setNameOfSpeciality(specialtyName);
                newSpeciality.setScore(score);
                
                session.persist(newSpeciality);
                session.getTransaction().commit();
                
                statusLabel.setText("Специальность добавлена!");
                Main.showAlert("Успех", "Специальность '" + specialtyName + "' успешно добавлена с баллом " + score);
                
                specialtyNameField.clear();
                specialtyScoreField.clear();
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Некорректный балл");
            Main.showAlert("Ошибка", "Проходной балл должен быть целым числом");
        } catch (Exception e) {
            statusLabel.setText("Ошибка добавления");
            Main.showAlert("Ошибка", "Произошла ошибка при добавлении: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack() {
        closeSessionFactory();
        Stage stage = (Stage) backButton.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/StartScreen.fxml"));
            stage.setScene(new Scene(root, 1200, 800));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeSessionFactory() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }

}