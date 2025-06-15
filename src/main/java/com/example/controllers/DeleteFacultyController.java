package com.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.example.entities.Application;
import com.example.entities.Documents;
import com.example.entities.Entrant;
import com.example.entities.EntrantAchievements;
import com.example.entities.Exam;
import com.example.entities.ExamResults;
import com.example.entities.Faculty;
import com.example.entities.OrderOfEnrollment;
import com.example.entities.Speciality;

public class DeleteFacultyController {
    @FXML private TextField facultyIdField;
    @FXML private Button deleteButton;
    @FXML private Label statusLabel;
    @FXML private Button backButton;
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
            showAlert(AlertType.WARNING,"Ошибка", "Не удалось инициализировать Hibernate: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDelete() {
        String text = facultyIdField.getText().trim();
        if (text.isEmpty()) {
            showAlert(AlertType.WARNING, "Ошибка", "ID факультета не может быть пустым");
            return;
        }
        Long id;
        try {
            id = Long.parseLong(text);
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Ошибка", "Некорректный ID факультета");
            return;
        }

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            int deletedApps = session.createMutationQuery(
                "DELETE FROM Application a WHERE a.faculty.idFaculty = :id"
            )
            .setParameter("id", id)
            .executeUpdate();

            int deletedFac = session.createMutationQuery(
                "DELETE FROM Faculty f WHERE f.idFaculty = :id"
            )
            .setParameter("id", id)
            .executeUpdate();

            session.getTransaction().commit();

            statusLabel.setText("Удалено заявок: " + deletedApps + ", факультетов: " + deletedFac);
            showAlert(AlertType.INFORMATION, "Успех", "Записи успешно удалены");
        } catch (Exception e) {
            statusLabel.setText("Ошибка при удалении");
            showAlert(AlertType.ERROR, "Ошибка", "Не удалось удалить записи: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack() {
        closeSessionFactory();

        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
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

    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
