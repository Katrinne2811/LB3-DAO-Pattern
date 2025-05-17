package edu.nure.mjt;

import java.util.List;


//Клас, що реалізує інтерфейс BookDAO ("контракт" з правилами, що може робити DAO-об'єкт з ORM)
public class BookDao extends CommonDao implements BookDaoInterface {

    //Додавання та збереження запису про книгу до таблиці book
    public void persist(Book entity) {
        getCurrentSession().save(entity);
    }
    //Оновлення запису про книгу у таблиці book
    public void update(Book entity) {
        getCurrentSession().update(entity);
    }
    //Знаходження запису про книгу у таблиці book за id запису
    public Book findById(String id) {
        Book book = (Book) getCurrentSession().get(Book.class, id);
        return book;
    }
    //Видалення запису про книгу за id у дужках
    public void delete(Book entity) {
        getCurrentSession().delete(entity);
    }
    //Виведення списку записів про всі книги (об'єкти класу-сутності Book) за допомогою виконання запиту HQL
    @SuppressWarnings("unchecked")
    public List<Book> findAll() {
        List<Book> books = (List<Book>) getCurrentSession().createQuery("from Book", Book.class).list();
        return books;
    }
    //Видалення всіх записів про книги за допомогою for-each конструкції
    public void deleteAll() {
        List<Book> entityList = findAll();
        for (Book entity : entityList) {
            delete(entity);
        }
    }
}
