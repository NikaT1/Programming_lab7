package com.console_project.server.repository;

import javax.naming.OperationNotSupportedException;
import java.util.List;

public interface DataRepository<T> {

    List<T> getAll();

    boolean add(T object);

    boolean clear(String key);

    boolean remove(T object);

    boolean update(int id, T object);

    int find(T t);
}
