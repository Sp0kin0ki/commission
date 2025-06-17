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
import com.example.dto.FacultyDTO;
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

public class FindFacultiesBySpecialtyController {

    @FXML private TextField specialtyField;
    @FXML private Button searchButton;
    @FXML private Button backButton;
    @FXML private TableView<FacultyDTO> facultyTable;
    @FXML private TableColumn<Character, String> name;
    @FXML private TableColumn<Character, String> numberOfBudgetPlaces;
    @FXML private TableColumn<Character, String> numberOfPaidPlaces;
    @FXML private TableColumn<Character, String> numberOfSpecialPlaces;
    @FXML private Label statusLabel;
    private SessionFactory sessionFactory;

    @FXML
    public void initialize() {
        initializeHibernate();
        configureTableColumns();
        facultyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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
        numberOfBudgetPlaces.setCellValueFactory(new PropertyValueFactory<>("numberOfBudgetPlaces"));
        numberOfPaidPlaces.setCellValueFactory(new PropertyValueFactory<>("numberOfPaidPlaces"));
        numberOfSpecialPlaces.setCellValueFactory(new PropertyValueFactory<>("numberOfSpecialPlaces"));
    }

    @FXML
    private void handleSearch() {
        String specialityName = specialtyField.getText().trim();

        if (specialityName.isEmpty()) {
            Main.showAlert("Ошибка", "Поле специальности не может быть пустым!");
            return;
        }

        statusLabel.setText("Поиск...");
        facultyTable.getItems().clear();

        try (Session session = sessionFactory.openSession()) {
            String hql = """
                SELECT f.name, f.numberOfBudgetPlaces, f.numberOfPaidPlaces, f.numberOfSpecialPlaces
                FROM Faculty f
                JOIN f.speciality s
                WHERE s.nameOfSpeciality = :specialityName
                ORDER BY f.name
            """;

            List<Object[]> rows = session.createQuery(hql, Object[].class)
                .setParameter("specialityName", specialityName)
                .getResultList();

            List<FacultyDTO> dtoList = new ArrayList<>();
            for (Object[] row : rows) {
                dtoList.add(new FacultyDTO(
                    (String) row[0],
                    ((Number) row[1]).intValue(),
                    ((Number) row[2]).intValue(),
                    ((Number) row[3]).intValue()
                ));
            }

        if (dtoList.isEmpty()) {
            statusLabel.setText("Факультеты не найдены");
            Main.showAlert("Результат", "По вашему запросу ничего не найдено");
        } else {
            ObservableList<FacultyDTO> data =
                FXCollections.observableArrayList(dtoList);
            facultyTable.setItems(data);
            statusLabel.setText("Найдено факультетов: " + dtoList.size());
        }

    } catch (Exception e) {
        statusLabel.setText("Ошибка выполнения запроса");
        Main.showAlert("Ошибка", "Произошла ошибка при выполнении запроса: " + e.getMessage());
        e.printStackTrace();
        }
        specialtyField.clear();
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