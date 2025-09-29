package com.anthony;

import lombok.Getter;
import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Arrays;

@Getter
public enum TaskRegistry {

    BASIC((byte) 1, BasicTask.class),
    SCHEDULE((byte) 2, ScheduleTask.class);

    private final byte typeId;
    private final Class<? extends AbstractTask> taskClass;

    TaskRegistry(byte typeId, Class<? extends AbstractTask> taskClass){
        this.typeId = typeId;
        this.taskClass = taskClass;
    }

    @SneakyThrows
    public AbstractTask deserialize(ByteBuffer buffer) {
        Method method = taskClass.getDeclaredMethod("deserialize", ByteBuffer.class);
        return (AbstractTask) method.invoke(null, buffer);
    }

    public static TaskRegistry fromClass(Class<?> clazz) {
        return Arrays.stream(values())
                .filter(type -> type.getTaskClass().equals(clazz))
                .findFirst().orElseThrow();
    }

    public static TaskRegistry fromTypeId(byte typeId) {
        return Arrays.stream(values())
                .filter(type -> type.getTypeId() == typeId)
                .findFirst().orElseThrow();
    }

}
