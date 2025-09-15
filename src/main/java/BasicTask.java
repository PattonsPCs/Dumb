import lombok.Setter;

import java.nio.ByteBuffer;

public class BasicTask extends AbstractTask {

    protected static BasicTask deserialize(ByteBuffer buffer){
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

        return new BasicTask(nameStr, Status.valueOf(statusStr), descriptionStr);
    }

    public BasicTask(String name, Status status, String description){
        super(name);
        this.status = status;
        this.description = description;
        this.id = 1;
    }




    @Override
    public String toString(){
       return "Name: " + name + "\nId: " + getId() + "\nStatus: " + getStatus() + "\nDescription: " + getDescription();
    }

}
