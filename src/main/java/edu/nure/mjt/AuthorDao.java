package edu.nure.mjt;

import java.util.List;

//Клас, що реалізує інтерфейс AuthorDAO ("контракт" з правилами, що може робити DAO-об'єкт з ORM)
public class AuthorDao extends CommonDao implements AuthorDaoInterface {

    //Додавання та збереження запису про автора книг до таблиці author
    public void persist(Author entity) {
        getCurrentSession().save(entity);
    }
    //Оновлення запису про автора книг у таблиці author
    public void update(Author entity) {
        getCurrentSession().update(entity);
    }
    //Знаходження запису про автора книг у таблиці author за id запису
    public Author findById(String id) {
        Author author = (Author) getCurrentSession().get(Author.class, id);
        return author;
    }
    //Видалення запису про автора книг за id у дужках
    public void delete(Author entity) {
        getCurrentSession().delete(entity);
    }
    //Виведення списку записів про всіх авторів книг (об'єкти класу-сутності Author) за допомогою виконання запиту HQL
    @SuppressWarnings("unchecked")
    public List<Author> findAll() {
        List<Author> authors = (List<Author>) getCurrentSession().createQuery("from Author", Author.class).list();
        return authors;
    }
    //Видалення всіх записів про авторів книг за допомогою for-each конструкції
    public void deleteAll() {
        List<Author> entityList = findAll();
        for (Author entity : entityList) {
            delete(entity);
        }
    }
}
