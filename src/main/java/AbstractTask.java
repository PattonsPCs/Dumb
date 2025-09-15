import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

@Getter
public abstract class AbstractTask implements Task {

    protected static AbstractTask deserialize(ByteBuffer data) {
        byte typeId = data.get();
        TaskRegistry registry = TaskRegistry.fromTypeId(typeId);

        return registry.deserialize(data);
    }

    @Setter
    protected String name;
    @Setter
    protected Status status;
    protected String description;
    protected int id;

    public AbstractTask(String name){
        this.name = name;
        this.status = Status.TODO;


    }

    public byte[] serialize() {
        byte typeId = TaskRegistry.fromClass(this.getClass()).getTypeId();
        byte[] name = this.name.getBytes();
        byte[] status = this.status.name().getBytes();
        byte[] description = this.description.getBytes();

        ByteBuffer buffer = ByteBuffer.allocate(1 + (Integer.BYTES * 3) + name.length + status.length + description.length);
        buffer.put(typeId);
        buffer.putInt(name.length);
        buffer.put(name);
        buffer.putInt(status.length);
        buffer.put(status);
        buffer.putInt(description.length);
        buffer.put(description);
        return buffer.array();
    }




}
