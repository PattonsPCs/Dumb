import lombok.Getter;

import java.nio.ByteBuffer;
import java.util.function.Function;

public enum TaskRegistry {
    BASIC((byte) 1, BasicTask.class, BasicTask::new);
    @Getter
    private final byte typeId;
    @Getter
    private final Class<? extends AbstractTask> taskClass;
    @Getter
    private final Function<ByteBuffer, ? extends AbstractTask> deserializer;

    TaskRegistry(byte typeId, Class<? extends AbstractTask> taskClass, Function<ByteBuffer, ? extends AbstractTask> deserializer){
        this.typeId = typeId;
        this.taskClass = taskClass;
        this.deserializer = deserializer;
    }

    public static TaskRegistry fromClass(Class<?> clazz){
        for(TaskRegistry entry : values()){
            if(entry.getTaskClass().equals(clazz)){
                return entry;
            }
        }
        return null;
    }

    public static TaskRegistry fromTypeId(byte typeId){
        for(TaskRegistry entry : values()){
            if(entry.getTypeId() == typeId){
                return entry;
            }
        }
        return null;
    }







}
