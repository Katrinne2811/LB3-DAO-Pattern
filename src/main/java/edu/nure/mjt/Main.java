package edu.nure.mjt;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Створення об'єкта "сервісу" для роботи із таблицею book у БД за допомогою DAO
        BookService bookService = new BookService();
        //Створення об'єкта "сервісу" для роботи із таблицею book у БД за допомогою DAO
        AuthorService authorService = new AuthorService();

        //Видалення всіх записів про книги з таблиці book у БД
        if(bookService.findAll().size() > 0)
        {System.out.println("*** DeleteAll - start ***");
        bookService.deleteAll();
        //Виведення кількості записів про книги, що лишилися у таблиці book у БД
        System.out.println("Number of books found are now " + bookService.findAll().size());}
        if(authorService.findAll().size() > 0)
        {//Видалення всіх записів про авторів книг з таблиці book у БД
        authorService.deleteAll();
        //Виведення кількості записів про авторів книг, що лишилися у таблиці authors у БД
        System.out.println("Number of authors found are now " + authorService.findAll().size());
        System.out.println("*** DeleteAll - end ***");}

        //Створення 3 записів про авторів книг
        Author author1 = new Author("Fedor", "Dostoevsky");
        Author author2 = new Author("Leo", "Tolstoy");
        Author author3 = new Author("Jane", "Ostin");

       System.out.println("*** Persist - start ***");
       //Додавання до таблиці author БД записів про авторів
        authorService.persist(author1);
        authorService.persist(author2);
        authorService.persist(author3);
        System.out.println("Authors persisted are :");
       //Виведення на консоль всіх авторів, що є у таблиці author
       List<Author> authors = authorService.findAll();
       for(Author a : authors) {
           System.out.println("-" + a.toString());
       }

       //Створення 4 записів про книги
       Book book1 = new Book("1", "The Brothers Karamazov", author1);
        Book book2 = new Book("2", "War and Peace", author2);
        Book book3 = new Book("3", "Pride and Prejudice", author3);
        Book book4 = new Book("4", "Anna Karenina", author2);

        //Додавання та збереження у таблиці book у БД об'єктів класу-сутності (записів про книги)
        System.out.println("*** Persist - start ***");
        bookService.persist(book1);
        bookService.persist(book2);
        bookService.persist(book3);
       bookService.persist(book4);

       //Виведення списку всіх книг у таблиці БД
       List<Book> books1 = bookService.findAll();
       System.out.println("Books Persisted are :");
       for (Book b : books1) {
           System.out.println("-" + b.toString());
       }
        System.out.println("*** Persist - end ***");

        //Виведення запису про Льва Толстого за id запису та перелік його книг
        Author testAuthor = authorService.findById(author2.getId()+ "");
        System.out.println("*** Books by " + testAuthor.getName() + " "
                + testAuthor.getSurname() + " ***");
        System.out.println(testAuthor.getBooks());

        //Оновлення записів про книги у таблиці book у БД
        System.out.println("*** Update - start ***");
        //Зміна назви книги з id=1
        book1.setTitle("The Idiot");
        bookService.update(book1);
        System.out.println("Book Updated is =>"
                + bookService.findById(book1.getId()).toString());
        System.out.println("*** Update - end ***");

        //Знаходження книги за id у таблиці book
        System.out.println("*** Find - start ***");
        String id1 = book1.getId();
       //Об'єкт класу Book (запис про книгу у таблиці book), знайдений за id=id1
        Book another = bookService.findById(id1);
        //Виведення інформації про знайдену книгу у форматі рядка, що прописано у перевизнач. методі класу Book
        System.out.println("Book found with id " + id1 + " is =>" + another.toString());
        System.out.println("*** Find - end ***");

        //Видалення запису про книгу з id=3
        System.out.println("*** Delete - start ***");
       String id3 = book3.getId();
       bookService.delete(id3);
        System.out.println("Deleted book with id " + id3 + ".");
        //Виведення кількості записів про книги, що лишилися у таблиці book у БД
        System.out.println("Now all books are " + bookService.findAll().size() + ".");
        System.out.println("*** Delete - end ***");

        //Виведення списку всіх книг, записи про які лишилися в БД
        System.out.println("*** FindAll - start ***");
        List<Book> books2 = bookService.findAll();
        System.out.println("Books found are :");
        for (Book b : books2) {
            System.out.println("-" + b.toString());
        }
        //Виведення списку всіх авторів книг, що лишилися, з таблиці у БД
        System.out.println("*** FindAll - start ***");
        List<Author> authorsList = authorService.findAll();
        System.out.println("Authors found are :");
        for (Author a : authorsList) {
            System.out.println("-" + a.toString());
       }
        System.out.println("*** FindAll - end ***");

        System.exit(0);
    }
}