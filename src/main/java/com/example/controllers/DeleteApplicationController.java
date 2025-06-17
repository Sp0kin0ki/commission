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

public class DeleteApplicationController {

    @FXML private TextField passportField;
    @FXML private Button deleteButton;
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
    private void handleDelete() {
        String passport = passportField.getText().trim();

        if (passport.isEmpty()) {
            Main.showAlert("Ошибка", "Поле паспорта не может быть пустым!");
            return;
        }

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            
            Entrant entrant = session.createQuery(
                    "FROM Entrant WHERE seriesAndNumber = :passport", Entrant.class)
                    .setParameter("passport", passport)
                    .uniqueResult();
            
            if (entrant == null) {
                statusLabel.setText("Абитуриент не найден");
                Main.showAlert("Ошибка", "Абитуриент с паспортом '" + passport + "' не найден");
                session.getTransaction().rollback();
                return;
            }
            
            int deletedCount = session.createMutationQuery(
                    "DELETE FROM Application a WHERE a.idEntrant = :entrant")
                    .setParameter("entrant", entrant)
                    .executeUpdate();
            
            session.getTransaction().commit();
            
            if (deletedCount > 0) {
                statusLabel.setText("Удалено заявлений: " + deletedCount);
                Main.showAlert("Успех", "Удалено " + deletedCount + " заявлений для абитуриента: " + passport);
            } else {
                statusLabel.setText("Заявления не найдены");
                Main.showAlert("Результат", "У абитуриента нет заявлений");
            }
        } catch (Exception e) {
            statusLabel.setText("Ошибка удаления");
            Main.showAlert("Ошибка", "Произошла ошибка при удалении: " + e.getMessage());
            e.printStackTrace();
        } 
        passportField.clear();
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