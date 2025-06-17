package com.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.example.Main;
import com.example.dto.EntrantFacultyDTO;
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
import java.util.ArrayList;
import java.util.List;

public class FindEntrantsByFacultyController {

    @FXML private TextField facultyField;
    @FXML private Button searchButton;
    @FXML private Button backButton;
    @FXML private TableView<EntrantFacultyDTO> entrantTable;
    @FXML private TableColumn<EntrantFacultyDTO, String> name;
    @FXML private TableColumn<EntrantFacultyDTO, String> last_name;
    @FXML private TableColumn<EntrantFacultyDTO, String> surname;
    @FXML private TableColumn<EntrantFacultyDTO, String> application_status;

    @FXML private Label statusLabel;
    private SessionFactory sessionFactory;

    @FXML
    public void initialize() {
        initializeHibernate();

        configureTableColumns();
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
            Main.showAlert("Ошибка", "Не удалось инициализировать Hibernate: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void configureTableColumns() {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        last_name.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        application_status.setCellValueFactory(new PropertyValueFactory<>("applicationStatus"));
    }

    @FXML
    private void handleSearch() {
        String facultyName = facultyField.getText().trim();

        if (facultyName.isEmpty()) {
            Main.showAlert("Ошибка", "Поле факультета не может быть пустым!");
            return;
        }

        statusLabel.setText("Поиск...");
        entrantTable.getItems().clear();

        try (Session session = sessionFactory.openSession()) {
            String sql = "SELECT e.name, e.last_name, e.surname, " +
                    "a.application_status, f.name " +
                    "FROM entrant e " +
                    "JOIN application a ON e.id_entrant = a.id_entrant " +
                    "JOIN faculty f ON a.id_faculty = f.id_faculty " +
                    "WHERE f.name = :facultyName";

            List<Object[]> results = session.createNativeQuery(sql, Object[].class)
                    .setParameter("facultyName", facultyName)
                    .getResultList();

            List<EntrantFacultyDTO> entrantResults = new ArrayList<>();
            for (Object[] row : results) {
                entrantResults.add(new EntrantFacultyDTO(
                        (String) row[0],
                        (String) row[1],
                        (String) row[2],
                        (String) row[3],
                        (String) row[4]));
            }

            if (entrantResults.isEmpty()) {
                statusLabel.setText("Абитуриенты не найдены");
                Main.showAlert("Результат", "По вашему запросу ничего не найдено");
            } else {
                ObservableList<EntrantFacultyDTO> data = FXCollections.observableArrayList(entrantResults);
                entrantTable.setItems(data);
                statusLabel.setText("Найдено: " + entrantResults.size() + " записей");
            }
        } catch (Exception e) {
            statusLabel.setText("Ошибка выполнения запроса");
            Main.showAlert("Ошибка", "Произошла ошибка при выполнении запроса: " + e.getMessage());
            e.printStackTrace();
        }
        facultyField.clear();
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
}