package edu.nure.mjt;


import java.util.List;

public class BookService {
    //Поле об'єкта доступа до даних DAO
    private static BookDao bookDao;
    //Конструктор об'єкту сервісу для роботи з об'єктами-сутностями (записами про книги) у БД
    public BookService() {
        //Створення поля об'єкту для доступу до даних (зв'язку з БД з таблицею book)
        bookDao = new BookDao();
    }

    //Метод для відкриття сесії з транзакцією та додавання нової сутності (запису про книгу) у таблицю БД
    public void persist(Book entity) {
        bookDao.openCurrentSessionwithTransaction();
        bookDao.persist(entity);
        bookDao.closeCurrentSessionwithTransaction();
    }
    //Метод для відкриття сесії з транзакцією та оновлення сутності (запису про книгу) до таблиці БД
    public void update(Book entity) {
        bookDao.openCurrentSessionwithTransaction();
        bookDao.update(entity);
        bookDao.closeCurrentSessionwithTransaction();
    }
    //Метод для відкриття сесії та знаходження сутності (запису про книгу) у БД за id
    public Book findById(String id) {
        bookDao.openCurrentSession();
        Book book = bookDao.findById(id);
        bookDao.closeCurrentSession();
        return book;
    }
    //Метод для відкриття сесії з транзакцією та видалення сутності (запису про книгу) у БД з вказаним id
    public void delete(String id) {
        bookDao.openCurrentSessionwithTransaction();
        Book book = bookDao.findById(id);
        bookDao.delete(book);
        bookDao.closeCurrentSessionwithTransaction();
    }
    //Метод, що повертає список всіх записів про книги у БД
    public List<Book> findAll() {
        bookDao.openCurrentSession();
        List<Book> books = bookDao.findAll();
        bookDao.closeCurrentSession();
        return books;
    }
    //Метод для видалення всіх записів про книги у БД
    public void deleteAll() {
        bookDao.openCurrentSessionwithTransaction();
        bookDao.deleteAll();
        bookDao.closeCurrentSessionwithTransaction();
    }

    //Метод для повернення створеного об'єкту DAO
    public BookDao bookDao() {
        return bookDao;
    }
}
