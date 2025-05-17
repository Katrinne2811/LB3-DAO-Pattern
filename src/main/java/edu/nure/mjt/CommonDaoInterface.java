package edu.nure.mjt;
//Клас для серіалізації даних(запису даних у вигляді байтів та їх передачі і збереження)
import java.io.Serializable;
//Інтерфейс, що дозволяє створювати список елементів
import java.util.List;

//Оголошення інтерфейсу, в якому наведено методи для виконання CRUD-операцій над об'єктами
// (для збереження та передачі об'єктів класу Book id має серіалізуватися)
public interface CommonDaoInterface<T, Id extends Serializable> {
    //Метод, що зберігає сутність (запис) у БД
    public void persist(T entity);
    //Метод, що оновлює наявну сутність (запис) у БД
    public void update(T entity);
    //Метод, шо повертає об'єкт вказаного типу за значенням первинного ключа id
    public T findById(Id id);
    //Метод для видалення сутності(запису) з БД
    public void delete(T entity);
    //Метод, що повертає список з усіма об'єктами вказаного типу
    public List<T> findAll();
    //Метод для видалення всіх записів з БД
    public void deleteAll();
}
