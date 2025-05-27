package com.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.example.Entities.Entrant;

import java.util.Properties;


public class HibernateRunner {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .addProperties(new Properties()) 
                .addAnnotatedClass(Entrant.class)
                .buildSessionFactory();

        try (Session session = factory.openSession()) {
            // 2. Начинаем транзакцию
            session.beginTransaction();

            Entrant entrant = new Entrant();
            entrant.setSeries_and_number("4619454225");
            entrant.setName("Иван");
            entrant.setLast_name("Иванов");
            entrant.setSurname("Иванович");
            entrant.setGold_medal(true);
            entrant.setYear_of_admission(2024);
            entrant.setFinal_score(295);

            session.persist(entrant);

            session.getTransaction().commit();

            System.out.println("Абитуриент добавлен с ID: " + entrant.getId_entrant());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            factory.close();
        }
    }

    
}

