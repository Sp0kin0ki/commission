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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.example.entities.Entrant;
import com.example.entities.EntrantAchievements;
import com.example.entities.Exam;
import com.example.entities.ExamResults;
import com.example.Main;
import com.example.dto.EntrantSpecialtyDTO;
import com.example.entities.Application;
import com.example.entities.Documents;
import com.example.entities.Faculty;
import com.example.entities.OrderOfEnrollment;
import com.example.entities.Speciality;

public class FindEntrantsBySpecialtyController {

    @FXML private TextField specialtyField;
    @FXML private Button searchButton;
    @FXML private Button backButton;
    @FXML private TableView<EntrantSpecialtyDTO> entrantTable;
    @FXML private TableColumn<Character, String> name;
    @FXML private TableColumn<Character, String> last_name;
    @FXML private TableColumn<Character, String> surname;
    @FXML private TableColumn<Character, String> application_status;
    @FXML private TableColumn<Character, String> name_of_specialty;
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
        name_of_specialty.setCellValueFactory(new PropertyValueFactory<>("specialtyName"));
    }

    @FXML
    private void handleSearch() {
        String specialty = specialtyField.getText().trim();
        
        if (specialty.isEmpty()) {
            Main.showAlert("Ошибка", "Поле специальности не может быть пустым!");
            return;
        }

        statusLabel.setText("Поиск...");
        entrantTable.getItems().clear();

        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT e.name, e.lastName, e.surname, " +
                "a.applicationStatus, f.name " +
                "FROM Entrant e " +
                "JOIN e.applications a " +
                "JOIN a.faculty f " +
                "JOIN f.speciality s " +
                "WHERE s.nameOfSpeciality = :specialityName";

            
            List<Object[]> rows = session.createQuery(hql, Object[].class)
                .setParameter("specialityName", specialty)
                .getResultList();
            
            List<EntrantSpecialtyDTO> results = new ArrayList<>();
            for (Object[] row : rows) {
                results.add(new EntrantSpecialtyDTO(
                    (String) row[0],
                    (String) row[1],
                    (String) row[2],
                    (String) row[3],
                    (String) row[4]
                ));
            }

            if (results.isEmpty()) {
                statusLabel.setText("Абитуриенты не найдены");
                Main.showAlert("Результат", "По вашему запросу ничего не найдено");
            } else {
                ObservableList<EntrantSpecialtyDTO> data = FXCollections.observableArrayList(results);
                entrantTable.setItems(data);
                statusLabel.setText("Найдено: " + results.size() + " записей");
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