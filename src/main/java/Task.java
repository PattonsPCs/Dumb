

public interface Task {

    int id();
    String name();
    // String description();
    // TODO replace with status enum
    Status status();
    // TODO placeholder for future displaying of tasks
    // String display();
    byte[] serialize();


}