package edu.nure.mjt.test;

import java.util.Collections;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;//Анотація для методу, що виконується перед кожним тестом
import org.junit.jupiter.api.AfterEach;//Анотація для методу, що виконується після кожного тесту
import org.junit.jupiter.api.Test;//Анотація для тестового методу
import org.junit.jupiter.api.extension.ExtendWith;//Анотація для підключення розширення
import org.mockito.InjectMocks;
import org.mockito.Mock;//Створюємо мок для тестування - імітацію БД без її підключення, для тестування об'єкта DAO
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import edu.nure.mjt.CommonDao;
import edu.nure.mjt.BookDao;
import edu.nure.mjt.Author;
import edu.nure.mjt.Book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;//Пакет для роботи з моками (імітаціями DAO-об'єкта)

//Підключення розширення Mockito до модулю тестування для роботи з моками (створення, створення шпіонів Spy,
// ін'єкції моків)
@ExtendWith(MockitoExtension.class)
public class BookDaoTest {
    //Мок поточної сесії
    @Mock
    private Session currentSession;
    //Мок поточної транзакції
    @Mock
    private Transaction currentTransaction;
   //Мок фабрики сесій
    @Mock
    private SessionFactory sessionFactory;
    //
    @Spy
    //Додає моки реальних об'єктів у всі методи класи BookDao
    @InjectMocks
    private BookDao bookDao = new BookDao();

    //Змінна для імітації (мокування) статичних методів класу CommonDao
    private MockedStatic<CommonDao> mockedStatic;
    //Перед кожним тестом
    @BeforeEach
    public void setUp() {
        mockedStatic = mockStatic(CommonDao.class);
        //Створення моку для статичного методу створення фабрики сесій для генерації
        //поточної сесії підключення до БД
        mockedStatic.when(CommonDao::getSessionFactory).thenReturn(sessionFactory);
        //Створення моку для статичного методу відкриття поточної сесії для виконання всіх тестів
        lenient().when(sessionFactory.openSession()).thenReturn(currentSession);
    }

    //Після кожного тесту мок статичних методів закривається (закривається поточна сесія та фабрика сесій)
    @AfterEach
    public void tearDown() {
        mockedStatic.close();
    }

    //Тест методу - перевірка, що відкриття сесії викликано
    @Test
    public void openCurrentSession() {
        bookDao.openCurrentSession();
        verify(sessionFactory).openSession();
    }
//Тест відкриття сесії та початку транзакції після створення її моку
    @Test
    public void openCurrentSessionWithTransaction() {
        when(currentSession.beginTransaction()).thenReturn(currentTransaction);
        bookDao.openCurrentSessionwithTransaction();
        verify(sessionFactory).openSession();
        verify(currentSession).beginTransaction();
    }
   //Перевірка, що метод викликає закриття поточної сесії
    @Test
    public void closeCurrentSession() {
        bookDao.closeCurrentSession();
        verify(currentSession).close();
    }

    //Перевірка, що метод викликає закриття поточної сесії з транзакцією
    @Test
    public void closeCurrentSessionWithTransaction() {
        bookDao.closeCurrentSessionwithTransaction();
        verify(currentTransaction).commit();
        verify(currentSession).close();
    }

    //Перевірка додавання запису про книгу до таблиці
    @Test
    public void persistShouldSaveEntity() {
        Book book = new Book();
        bookDao.persist(book);
        verify(currentSession).save(book);
    }
    //Перевірка оновлення запису про книгу до таблиці
    @Test
    public void updateShouldUpdateEntity() {
        Book book = new Book();
        bookDao.update(book);
        verify(currentSession).update(book);
    }
    //Перевірка видалення запису про книгу з таблиці
    @Test
    public void deleteShouldDeleteEntity() {
        Book book = new Book();
        bookDao.delete(book);
        verify(currentSession).delete(book);
    }
    //Перевірка знаходження запису про книгу у таблиці за вказаним id
    @Test
    public void findByIdShouldReturnBookWithSpecifiedId() {
        Author author = new Author();
        Book book = new Book("1", "Title", author);
        //Налаштування моку поточної сесії на повернення книги - змінної book
        when(currentSession.get(Book.class, "1")).thenReturn(book);
        Book bookById = bookDao.findById("1");
        //Первірка співпадіння книги, що очікується та той, що отримано в результаті тесту методу
        assertEquals(book, bookById);
    }

    //Перевірка знаходження всіх книг у таблиці
    @Test
    public void findAllShouldReturnAllBooks() {
        Book book = new Book();
        @SuppressWarnings("unchecked")
        //Створення моку для виконання HQL-запиту - об'єкта класу Query
        org.hibernate.query.Query<Book> query = mock(org.hibernate.query.Query.class);
        //Налаштуання моку запиту повертати книгу з таблиці
        lenient().when(currentSession.createQuery(anyString(), eq(Book.class))).thenReturn(query);
        //Налаштування моку повернення списку з 1 книгою
        lenient().when(query.list()).thenReturn(Collections.singletonList(book));
        List<Book> allBooks = bookDao.findAll();
        //Перевірка, чи дійсно метод повернув очікуваний список як у налаштуванні моку
        assertEquals(Collections.singletonList(book), allBooks);
    }
}

