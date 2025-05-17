package edu.nure.mjt;


import java.util.List;

public class AuthorService {
    //Поле об'єкта доступа до даних DAO
    private static AuthorDao authorDao;
    //Конструктор об'єкту сервісу для роботи з об'єктами-сутностями (записами про книги) у БД
    public AuthorService() {
        //Створення поля об'єкту для доступу до даних (зв'язку з БД з таблицею book)
        authorDao = new AuthorDao();
    }

    //Метод для відкриття сесії з транзакцією та додавання нової сутності (запису про книгу) у таблицю БД
    public void persist(Author entity) {
        authorDao.openCurrentSessionwithTransaction();
        authorDao.persist(entity);
        authorDao.closeCurrentSessionwithTransaction();
    }
    //Метод для відкриття сесії з транзакцією та оновлення сутності (запису про книгу) до таблиці БД
    public void update(Author entity) {
        authorDao.openCurrentSessionwithTransaction();
        authorDao.update(entity);
        authorDao.closeCurrentSessionwithTransaction();
    }
    //Метод для відкриття сесії та знаходження сутності (запису про книгу) у БД за id
    public Author findById(String id) {
        authorDao.openCurrentSession();
        Author author = authorDao.findById(id);
        authorDao.closeCurrentSession();
        return author;
    }
    //Метод для відкриття сесії з транзакцією та видалення сутності (запису про книгу) у БД з вказаним id
    public void delete(String id) {
        authorDao.openCurrentSessionwithTransaction();
        Author author = authorDao.findById(id);
        authorDao.delete(author);
        authorDao.closeCurrentSessionwithTransaction();
    }
    //Метод, що повертає список всіх записів про книги у БД
    public List<Author> findAll() {
        authorDao.openCurrentSession();
        List<Author> authors = authorDao.findAll();
        authorDao.closeCurrentSession();
        return authors;
    }
    //Метод для видалення всіх записів про книги у БД
    public void deleteAll() {
        authorDao.openCurrentSessionwithTransaction();
        authorDao.deleteAll();
        authorDao.closeCurrentSessionwithTransaction();
    }

    //Метод для повернення створеного об'єкту DAO
    public AuthorDao authorDao() {
        return authorDao;
    }
}
