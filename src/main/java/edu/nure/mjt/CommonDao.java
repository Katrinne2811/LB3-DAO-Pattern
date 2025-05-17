package edu.nure.mjt;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import com.google.common.annotations.VisibleForTesting;

//Абстрактний клас для створення DAO-об'єкта
public abstract class CommonDao {

    //Поля поточної сесії для виконання операцій з об'єктами класу-сутності
    // та транзакції (набору CRUD-операцій з даними)
    private Session currentSession;
    private Transaction currentTransaction;

    //Метод створення поточної сесії на фабриці та її відкриття для роботи з об'єктами класу-сутності Book
    public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }

    //Метод створення та відкриття поточної сесії з можливістю транзакцій (CRUD-операцій, що потім будуть разом збережні)
    public Session openCurrentSessionwithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    //Метод закриття поточної сесії роботи з ORM-об'єктами
    public void closeCurrentSession() {
        currentSession.close();
    }

    //Метод для збереження змін, зроблених у БД в результаті транзакції, та закриття поточної сесії
    public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }
    //Метод створення фабрики сесій розроблено для полегшення процесу тестування, а не для логіки основної програми
    @VisibleForTesting
    private static SessionFactory sessionFactory;
    //Створення фабрики сесій
    public static SessionFactory getSessionFactory() {
        // Перевіряємо, чи sessionFactory уже створений
        if (sessionFactory == null) {
            // Створення об'єкту, який "будує" інший об'єкт з вказаними налаштуваннями
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml").build();
            // Створення сховища для метаданих (даних про класи-сутності, таблиці у БД)
            Metadata metadata = new MetadataSources(standardRegistry).getMetadataBuilder().build();
            // Ініціалізуємо sessionFactory
            sessionFactory = metadata.getSessionFactoryBuilder().build();
        }
        // Повертаємо sessionFactory
        return sessionFactory;
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }}