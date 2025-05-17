package edu.nure.mjt;

import java.util.HashSet;//Пакет для реалізації об'єкта "об'экт множини - його ключ"
import java.util.Set;//Інтерфейс для створення множини (без повторень перелік об'єктів)
import jakarta.persistence.*;//Всі анотації для зв'язку об'єктів БД і Java (інтерфейс JPA)

//Оголошення класу-сутності Author
@Entity
//Відповідна таблиця в БД, де знаходитимуться записи про авторів книг
@Table(name = "author")
public class Author {
    //Первинний ключ - id автора, що є автоінкрементом (задається автоматично) і
    // відповідає стовпцю authorId в таблиці
    @Id
    @GeneratedValue
    @Column(name="authorId")
    private int id;
    //Поле імені автора, відповідає стовпцю name таблиці Author
    @Column(name="name")
    private String name;
    //Поле прізвища автора, відповідає стовпцю surname таблиці Author
    @Column(name="surname")
    private String surname;
    //Встановлення зв'язку "один автор може писати кілька книг" з таблицею book через поле "автор"
    //автор підвантажується у таблицю author разом (одразу) з додаванням книг до book
    @OneToMany(fetch = FetchType.EAGER, mappedBy="author")

    //Поле множини книг автора
    private Set<Book> books = new HashSet<Book>();

    //Конструктор об'єкта класу Author
    public Author() {
    }
    public Author(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
    //Геттери і сеттери для отримання або зміни списку книг автора, його id, імені та прізвища
    public Set<Book> getBooks() {
        return books;
    }
    public void setBooks(Set<Book> books) {
        this.books = books;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    //Перевизначений метод для розрахунку хеш-коду об'єкта (запису про автора)
    @Override
    public int hashCode() {
        final int prime = 31;
        //Змінна, що накопичує хеш-код
        int result = 1;
        result = prime * result + id;
        //Якщо name порожнє, то додаємо до prime*result 0 (аби не було виключення), а якщо name - заповнене, то додаємо хеш-код імені автора
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((surname == null) ? 0 : surname.hashCode());
        return result;
    }
    //Перевизначений метод для визначення, чи вже існує такий самий об'єкт
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        //Якщо об'єкти різних класів, то вони різні
        if (getClass() != obj.getClass())
            return false;
        //Перетворення об'єкта для порівняння на об'єкт класу author (кастинг для отримання доступу до полів Author)
        Author other = (Author) obj;
        //Перевірка подібності за списком книг автора з іншими авторами
        if (books == null) {
            if (other.books != null)
                return false;
        } else if (!books.equals(other.books))
            return false;
        //Перевірка подібності за id,іменем та прізвищем автора з іншими авторами
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (surname == null) {
            if (other.surname != null)
                return false;
        } else if (!surname.equals(other.surname))
            return false;
        return true;
    }
    //Перевизначений метод для виведення у консоль інформації про автора книг
    @Override
    public String toString() {
        return "authorid = " + id + ", name=" + name + ", surname=" + surname;



    }
}
