package org.example.dao;

import org.example.entity.User;

import java.util.List;

public interface CrudDAO <T> extends SuperDAO{
    public boolean save (T object);
    public boolean update (T object);
    public boolean delete (String id);
    public T search (String id);
    public T get (T object);
    List<T> getAll();
}
