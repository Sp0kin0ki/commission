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
import com.example.entities.OrderOfEnrollment;
import com.example.entities.Speciality;
import com.example.Main;
import com.example.entities.Application;
import com.example.entities.Documents;
import com.example.entities.Entrant;
import com.example.entities.EntrantAchievements;
import com.example.entities.Exam;
import com.example.entities.ExamResults;
import com.example.entities.Faculty;

public class CreateOrderOfEnrollmentController {
    @FXML
    private TextField entrantIdField;
    @FXML
    private TextField facultyIdField;
    @FXML
    private CheckBox enrolledCheck;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField priceField;
    @FXML
    private TextField statusField;
    @FXML
    private Button addButton;
    @FXML
    private Button backButton;
    @FXML
    private Label statusLabel;

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
    private void handleSave() {
        Long entrantId, facultyId;
        try {
            entrantId = Long.parseLong(entrantIdField.getText().trim());
            facultyId = Long.parseLong(facultyIdField.getText().trim());
        } catch (NumberFormatException e) {
            statusLabel.setText("ID должен быть числом");
            return;
        }
        if (datePicker.getValue() == null || priceField.getText().isEmpty() || statusField.getText().isEmpty()) {
            statusLabel.setText("Заполните все поля");
            return;
        }
        Double price;
        try {
            price = Double.parseDouble(priceField.getText().trim());
        } catch (NumberFormatException e) {
            statusLabel.setText("Некорректная цена");
            return;
        }
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Entrant entrant = session.get(Entrant.class, entrantId);
            Faculty faculty = session.get(Faculty.class, facultyId);
            if (entrant == null || faculty == null) {
                statusLabel.setText("Абитуриент или факультет не найдены");
                session.getTransaction().rollback();
                return;
            }
            OrderOfEnrollment order = new OrderOfEnrollment();
            order.setIdEntrant(entrant);
            order.setIdFaculty(faculty);
            order.setIsEnrolled(enrolledCheck.isSelected());
            order.setDate(java.sql.Date.valueOf(datePicker.getValue()));
            order.setPrice(price);
            order.setStatus(statusField.getText().trim());
            session.persist(order);
            session.getTransaction().commit();
            statusLabel.setText("Приказ добавлен");
        } catch (Exception ex) {
            statusLabel.setText("Ошибка при сохранении");
            ex.printStackTrace();
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