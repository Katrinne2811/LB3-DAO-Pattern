package edu.nure.mjt;

//Анотації стовпця, сутності, первинного ключа, таблиці БД тощо з пакету jakarta.persistence
import jakarta.persistence.*;


@Entity
@Table(name = "book")
//Клас Book - це модель об'єкта, про який робиться запис у таблиці book у БД
public class Book {
    //Приватне поле з ідентифікатором книги, що відповідає стовпцю id в таблиці book
    @Id//Первинний ключ запису про книгу у БД
    @Column(name = "id")
    private String id;
    //Приватне текстове поле з назвою книги, що відповідає стовпцю title в таблиці book
    @Column(name = "title")
    private String title;
    //Зв'язок "багато книг можуть мати одного автора"
    @ManyToOne
    @JoinColumn(name="authorId")
    //Об'єкт автора книги в таблиці book
    private Author author;

    //Конструктор об'єкта класу book (книги)
    public Book() {
    }
    public Book(String id, String title, Author author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }
    public Book(String title, Author author) {
        this.title = title;
        this.author = author;
    }
    //Методи-геттери для отримання номеру-ідентифікатора, назви чи автора книги у БД
    //+Методи-сеттери для зміни номеру-ідентифікатора, назви чи автора книги у БД
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Author getAuthor() {
        return author;
    }
    public void setAuthor(Author author) {
        this.author = author;
    }

    //Перевизначений метод для розрахунку хеш-коду кожної книги у списку Books
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        //Якщо поле порожнє (NULL), то до prime*result додаємо 0
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }
    //Перевизначений метод для порівняння двох книг
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        //Метод класу obj перевизначається як об'єкт класу Book для отримання доступу до його полей
        Book other = (Book) obj;
        if (author == null) {
            if (other.author != null)
                return false;

        } else if (!author.equals(other.author))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;

        } else if (!title.equals(other.title))
            return false;
        return true;
    }
    //Перевизначений метод для подання інформації з таблиці book (запису) у вигляді текстового рядка
    @Override
    public String toString() {
        return "Book: id =" + id + ", title =" + title + ", author: " + author;
    }
}
