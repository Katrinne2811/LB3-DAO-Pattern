package edu.nure.mjt;
import edu.nure.mjt.Author;//Клас об'єкта, з яким працюватимуть методи класу, який реалізує інтерфейс

//Інтерфейс, що умпадковує "контракт" інтерфейсу CommonDaoInterface
public interface AuthorDaoInterface extends CommonDaoInterface<Author, String>{}