package edu.nure.mjt.test;

import java.util.Collections;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;//Можливість "мокати" частину об'єктів, а не весь код
import org.mockito.junit.jupiter.MockitoExtension;
import edu.nure.mjt.CommonDao;
import edu.nure.mjt.AuthorDao;
import edu.nure.mjt.Author;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorDaoTest {
    @Mock
    private Session currentSession;

    @Mock
    private Transaction currentTransaction;

    @Mock
    private SessionFactory sessionFactory;

    @Spy
    //Одразу додає моки в усі методи класу BookDao
    @InjectMocks
    private AuthorDao authorDao = new AuthorDao();

    private MockedStatic<CommonDao> mockedStatic;
    @BeforeEach
    public void setUp() {
            mockedStatic = mockStatic(CommonDao.class);
            //Мокування статичного методу getSessionFactory() із CommonDao
            mockedStatic.when(CommonDao::getSessionFactory).thenReturn(sessionFactory);
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
        authorDao.openCurrentSession();
        verify(sessionFactory).openSession();
    }
    //Тест відкриття сесії та початку транзакції після створення її моку
    @Test
    public void openCurrentSessionWithTransaction() {
        when(currentSession.beginTransaction()).thenReturn(currentTransaction);
        authorDao.openCurrentSessionwithTransaction();
        verify(sessionFactory).openSession();
        verify(currentSession).beginTransaction();
    }
    //Перевірка, що метод викликає закриття поточної сесії
    @Test
    public void closeCurrentSession() {
        authorDao.closeCurrentSession();
        verify(currentSession).close();
    }

    //Перевірка, що метод викликає закриття поточної сесії з транзакцією
    @Test
    public void closeCurrentSessionWithTransaction() {
        authorDao.closeCurrentSessionwithTransaction();
        verify(currentTransaction).commit();
        verify(currentSession).close();
    }


    @Test
    public void persistShouldSaveEntity() {
        Author author = new Author();
        authorDao.persist(author);
        verify(currentSession).save(author);
    }

    @Test
    public void updateShouldUpdateEntity() {
        Author author = new Author();
        authorDao.update(author);
        verify(currentSession).update(author);
    }

    @Test
    public void deleteShouldDeleteEntity() {
        Author author = new Author();
        authorDao.delete(author);
        verify(currentSession).delete(author);
    }

    @Test
    public void findByIdShouldReturnAuthorWithSpecifiedId() {
        Author author = new Author();
        author.setId(1);
        when(currentSession.get(Author.class, "1")).thenReturn(author);
        Author authorById = authorDao.findById("1");
        assertEquals(author, authorById);
    }

    @Test
    public void findAllShouldReturnAllAuthorFromRepository() {
        Author author = new Author();
        @SuppressWarnings("unchecked")
        org.hibernate.query.Query<Author> query = mock(org.hibernate.query.Query.class);

        when(currentSession.createQuery(anyString(), eq(Author.class))).thenReturn(query);
        when(query.list()).thenReturn(Collections.singletonList(author));
        List<Author> allAuthors = authorDao.findAll();
        assertEquals(Collections.singletonList(author), allAuthors);
    }
}