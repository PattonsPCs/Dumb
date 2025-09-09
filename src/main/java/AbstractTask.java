import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

public abstract class AbstractTask implements Task {

    protected static AbstractTask deserialize(ByteBuffer data) {

        byte typeId = data.get();

        TaskRegistry registry = TaskRegistry.fromTypeId(typeId);

        if(registry == null){
            System.err.println("Unknown type Id: " + typeId);
            return null;
        }

        return registry.getDeserializer().apply(data);
    }


    @Getter @ Setter
    protected String name;
    @Getter @Setter
    protected Status status;
    @Getter
    protected String description;

    public AbstractTask( String name){
        this.name = name;
        this.status = Status.TODO;
    }

    public byte[] serialize() {
        byte[] name = this.name.getBytes();
        byte[] status = this.status.name().getBytes();
        byte[] description = this.description.getBytes();
        byte typeId = TaskRegistry.fromClass(this.getClass()).getTypeId();

        ByteBuffer buffer = ByteBuffer.allocate((1 + Integer.BYTES * 3) + name.length + status.length + description.length);
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
