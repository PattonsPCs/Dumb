package com.anthony;

import java.sql.Connection;
import java.util.List;

public interface Database {

    void createTable();
    void createTask(int id, Task task);
    AbstractTask readTask(int id);
    List<Integer> getAllTaskIds();
    void saveTask(int id, Task task);
    void deleteTask(int id);
    void deleteData();
    void dropTable();
}
