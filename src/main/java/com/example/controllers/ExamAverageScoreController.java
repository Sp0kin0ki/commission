package com.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
import com.example.Main;
import com.example.dto.ExamAverageScoreDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExamAverageScoreController {

    @FXML private TableView<ExamAverageScoreDTO> examTable;
    @FXML private TableColumn<ExamAverageScoreDTO, String> examNameColumn;
    @FXML private TableColumn<ExamAverageScoreDTO, Double> averageScoreColumn;
    @FXML private Button backButton;
    
    private SessionFactory sessionFactory;

    @FXML
    public void initialize() {
        initializeHibernate();
        configureTableColumns();
        loadData();
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


    private void configureTableColumns() {
        examNameColumn.setCellValueFactory(new PropertyValueFactory<>("examName"));
        averageScoreColumn.setCellValueFactory(new PropertyValueFactory<>("averageScore"));
        
        averageScoreColumn.setCellFactory(column -> new TableCell<ExamAverageScoreDTO, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", item));
                }
            }
        });
    }
    @FXML
    private void loadData() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT e.examName, ROUND(AVG(er.score), 2) " +
                     "FROM ExamResults er " + 
                     "JOIN er.idExam e " + 
                     "GROUP BY e.examName";
            
            List<Object[]> results = session.createQuery(hql, Object[].class).getResultList();
            
            List<ExamAverageScoreDTO> data = new ArrayList<>();
            for (Object[] row : results) {
                data.add(new ExamAverageScoreDTO(
                    (String) row[0],
                    (Double) row[1]
                ));
            }
            
            ObservableList<ExamAverageScoreDTO> observableList = FXCollections.observableArrayList(data);
            examTable.setItems(observableList);
        } catch (Exception e) {
            Main.showAlert("Ошибка", "Произошла ошибка при загрузке данных: " + e.getMessage());
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
}