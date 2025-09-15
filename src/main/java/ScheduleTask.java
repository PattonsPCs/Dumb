import lombok.Getter;

import java.nio.ByteBuffer;

public class ScheduleTask extends AbstractTask{

    @Getter // Use a long for milliseconds, why need time like this
    //
    protected long deadline;

    // Erm why the frick u using the non-primitive long
    public ScheduleTask(String name,Status status, String description, long deadline){
        super(name);
        this.deadline = deadline;
        this.status = status;
        this.description = description;
        this.id = 2;
    }



    @Override
    public byte[] serialize(){
        byte typeId = TaskRegistry.fromClass(this.getClass()).getTypeId();
        byte[] name = this.name.getBytes();
        byte[] status = this.status.name().getBytes();
        byte[] description = this.description.getBytes();


        ByteBuffer buffer = ByteBuffer.allocate(1 + (Integer.BYTES * 3) + name.length + status.length + description.length + Long.BYTES);
        buffer.put(typeId);
        buffer.putInt(name.length);
        buffer.put(name);
        buffer.putInt(status.length);
        buffer.put(status);
        buffer.putInt(description.length);
        buffer.put(description);
        buffer.putLong(deadline);
        return buffer.array();
    }



    protected static ScheduleTask deserialize(ByteBuffer buffer){
        int nameLen = buffer.getInt();
        byte[] name = new byte[nameLen];
        buffer.get(name);
        String nameStr = new String(name);

        int statusLen = buffer.getInt();
        byte[] status = new byte[statusLen];
        buffer.get(status);
        String statusStr = new String(status);

        int descriptionLen = buffer.getInt();
        byte[] description = new byte[descriptionLen];
        buffer.get(description);
        String descriptionStr = new String(description);

        long deadline = buffer.getLong();


        return new ScheduleTask(nameStr, Status.valueOf(statusStr), descriptionStr, deadline);
    }





    public boolean isActive(){
        return System.currentTimeMillis() < deadline;
    }

    @Override
    public String toString(){
        return "Name: " + name + "\nId: " + getId() + "\nStatus: " + getStatus() + "\nDescription: " + getDescription() + "\nDeadline: " + getDeadline() + "\nLate: " + isActive();
    }
}
