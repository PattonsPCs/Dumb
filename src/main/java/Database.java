public interface Database {

    void createTable();
    void createTask(int id, Task task);
    AbstractTask readTask(int id);
    void saveTask(int id, String neededStatus);
    void deleteTask(int id);
    void deleteData();
    void dropTable();
}
