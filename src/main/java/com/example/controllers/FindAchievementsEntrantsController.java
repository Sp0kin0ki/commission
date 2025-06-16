package com.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.example.dto.EntrantDTO;
import com.example.entities.Application;
import com.example.entities.Documents;
import com.example.entities.Entrant;
import com.example.entities.EntrantAchievements;
import com.example.entities.Exam;
import com.example.entities.ExamResults;
import com.example.entities.Faculty;
import com.example.entities.OrderOfEnrollment;
import com.example.entities.Speciality;

import java.io.IOException;
import java.util.List;

public class FindAchievementsEntrantsController {
    @FXML private TableView<EntrantDTO> entrantTable;
    @FXML private TableColumn<EntrantDTO, String> nameColumn;
    @FXML private TableColumn<EntrantDTO, String> lastNameColumn;
    @FXML private TableColumn<EntrantDTO, String> surnameColumn;
    @FXML private TableColumn<EntrantDTO, String> achievementColumn;
    @FXML private Button searchButton;
    @FXML private Label statusLabel;
    @FXML private Button backButton;

    private SessionFactory sessionFactory;

     @FXML
    public void initialize() {
        initializeHibernate();
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        achievementColumn.setCellValueFactory(new PropertyValueFactory<>("achievementName"));
        entrantTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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
            statusLabel.setText("Ошибка инициализации Hibernate");
            showAlert(AlertType.ERROR, "Ошибка", "Не удалось инициализировать Hibernate: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onSearchButtonClicked() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT e.name, e.lastName, e.surname, ea.nameAchievements " +
                         "FROM Entrant e " +
                         "JOIN e.achievements ea " +
                         "JOIN e.applications a";

            List<Object[]> rows = session.createQuery(hql, Object[].class).getResultList();
            if (rows.isEmpty()) {
                statusLabel.setText("Абитуриенты не найдены");
                showAlert(AlertType.INFORMATION, "Результат", "Нет абитуриентов с достижениями");
            } else {
                ObservableList<EntrantDTO> data = FXCollections.observableArrayList();
                for (Object[] row : rows) {
                    data.add(new EntrantDTO(
                        (String) row[0],
                        (String) row[1],
                        (String) row[2],
                        (String) row[3]
                    ));
                }
                entrantTable.setItems(data);
                statusLabel.setText("Найдено: " + data.size() + " достижений");
            }
        } catch (Exception e) {
            statusLabel.setText("Ошибка выполнения запроса");
            showAlert(AlertType.ERROR, "Ошибка", "Не удалось выполнить запрос: " + e.getMessage());
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

    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}