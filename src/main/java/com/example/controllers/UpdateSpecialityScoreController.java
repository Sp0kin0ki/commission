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

public class UpdateSpecialityScoreController {

    @FXML private TextField specialtyNameField;
    @FXML private TextField newScoreField;
    @FXML private Button updateButton;
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
    private void handleUpdate() {
        String nameOfSpeciality = specialtyNameField.getText().trim();
        String newScoreText = newScoreField.getText().trim();

        if (nameOfSpeciality.isEmpty() || newScoreText.isEmpty()) {
            Main.showAlert("Ошибка", "Все поля должны быть заполнены!");
            return;
        }

        try {
            int newScore = Integer.parseInt(newScoreText);
            
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                
                String hql = "UPDATE Speciality SET score = :newScore WHERE nameOfSpeciality = :name";
                int updatedCount = session.createMutationQuery(hql)
                        .setParameter("newScore", newScore)
                        .setParameter("name", nameOfSpeciality)
                        .executeUpdate();
                
                session.getTransaction().commit();
                
                if (updatedCount > 0) {
                    statusLabel.setText("Проходной балл обновлен!");
                    Main.showAlert("Успех", "Проходной балл для специальности '" + nameOfSpeciality + "' успешно обновлен на " + newScore);
                } else {
                    statusLabel.setText("Специальность не найдена");
                    Main.showAlert("Ошибка", "Специальность '" + nameOfSpeciality + "' не найдена");
                }
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Некорректный балл");
            Main.showAlert("Ошибка", "Проходной балл должен быть целым числом");
        } catch (Exception e) {
            statusLabel.setText("Ошибка обновления");
            Main.showAlert("Ошибка", "Произошла ошибка при обновлении: " + e.getMessage());
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