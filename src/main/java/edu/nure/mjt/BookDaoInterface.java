package edu.nure.mjt;
import edu.nure.mjt.Book;//Клас об'єкта, з яким працюватимуть методи класу, який реалізує інтерфейс

//Інтерфейс, що успадковує "контракт" інтерфейсу CommonDaoInterface
public interface BookDaoInterface extends CommonDaoInterface<Book, String>{}

