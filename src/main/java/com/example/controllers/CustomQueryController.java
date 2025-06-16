package com.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

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

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class CustomQueryController {

    @FXML private TextArea sqlInput;
    @FXML private TextArea resultOutput;
    @FXML private Button executeButton;
    @FXML private Button backButton;
    @FXML private Label statusLabel;
    private SessionFactory sessionFactory;

    private static final Pattern DROP_PATTERN = Pattern.compile("\\bDROP\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern TRUNCATE_PATTERN = Pattern.compile("\\bTRUNCATE\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern ALTER_PATTERN = Pattern.compile("\\bALTER\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern CREATE_PATTERN = Pattern.compile("\\bCREATE\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern DELETE_WO_WHERE_PATTERN = Pattern.compile("\\bDELETE\\b[\\s\\S]*?\\bWHERE\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern UPDATE_WO_WHERE_PATTERN = Pattern.compile("\\bUPDATE\\b[\\s\\S]*?\\bWHERE\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern MULTI_STATEMENT_PATTERN = Pattern.compile(";.*;", Pattern.CASE_INSENSITIVE);

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
    private void handleExecute() {
        String sql = sqlInput.getText().trim();
        
        if (sql.isEmpty()) {
            Main.showAlert("Ошибка", "SQL запрос не может быть пустым!");
            return;
        }
        
        if (!isQuerySafe(sql)) {
            statusLabel.setText("Запрос отклонён: опасная операция");
            Main.showAlert("Ошибка безопасности", "Запрос содержит опасные операции. Разрешены только SELECT, INSERT, UPDATE и DELETE с WHERE.");
            return;
        }
        
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            
            if (sql.toUpperCase().startsWith("SELECT")) {
                List<Object[]> results = session.createNativeQuery(sql, Object[].class).getResultList();
                
                StringBuilder output = new StringBuilder();
                for (Object[] row : results) {
                    for (Object cell : row) {
                        output.append(cell).append("\t");
                    }
                    output.append("\n");
                }
                
                String formattedOutput = output.toString().replace("\t", "    ");
                resultOutput.setText(formattedOutput);
                statusLabel.setText("Найдено строк: " + results.size());
            } else {
                int count = session.createNativeMutationQuery(sql).executeUpdate();
                session.getTransaction().commit();
                
                resultOutput.setText("Успешно выполнено. Затронуто строк: " + count);
                statusLabel.setText("Изменено строк: " + count);
            }
        } catch (Exception e) {
            statusLabel.setText("Ошибка выполнения запроса");
            resultOutput.setText("Ошибка: " + e.getMessage());
        }
    }

    private boolean isQuerySafe(String sql) {
        if (DROP_PATTERN.matcher(sql).find()) return false;
        if (TRUNCATE_PATTERN.matcher(sql).find()) return false;
        if (ALTER_PATTERN.matcher(sql).find()) return false;
        if (CREATE_PATTERN.matcher(sql).find()) return false;
        if (DELETE_WO_WHERE_PATTERN.matcher(sql).find()) return false;
        if (UPDATE_WO_WHERE_PATTERN.matcher(sql).find()) return false;
        if (MULTI_STATEMENT_PATTERN.matcher(sql).find()) return false;
        
        return true;
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