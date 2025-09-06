import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;

public abstract class AbstractTask implements Task {

    protected static AbstractTask deserialize(ByteBuffer data) {
        int taskId = data.getInt();
        int nameLength = data.getInt();

        byte[] nameBytes = new byte[nameLength];
        data.get(nameBytes);
        String name = new String(nameBytes);

        int statusLength = data.getInt();
        byte[] statusBytes = new byte[statusLength];
        data.get(statusBytes);
        String statusName = new String(statusBytes);

        int typeNameLength = data.getInt();
        byte[] typeNameBytes = new byte[typeNameLength];
        data.get(typeNameBytes);
        String typeName = new String(typeNameBytes);

        try{
            Class<?> taskClass = Class.forName(typeName);
            java.lang.reflect.Constructor<?> constructor = taskClass.getConstructor(int.class, String.class, Status.class);
            return (AbstractTask) constructor.newInstance(taskId, name, Status.valueOf(statusName));
        } catch(ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
            System.err.println("Failed to get Task: " + e);
        }
        return null;
    }

    private final int id;
    private final String name;
    private final Status status;

    public AbstractTask(int id, String name){
        this.id = id;
        this.name = name;
        this.status = Status.TODO;
    }

    public byte[] serialize() {
        byte[] typeName = this.getClass().getName().getBytes();
        byte[] name = this.name.getBytes();
        byte[] status = this.status.name().getBytes();
        ByteBuffer buffer = ByteBuffer.allocate((Integer.BYTES * 4) + typeName.length + name.length + status.length);
        buffer.putInt(id);
        buffer.putInt(name.length);
        buffer.put(name);
        buffer.putInt(status.length);
        buffer.put(status);
        buffer.putInt(typeName.length);
        buffer.put(typeName);
        return buffer.array();
    }



}
