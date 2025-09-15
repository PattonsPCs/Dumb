import java.nio.ByteBuffer;

public class TaskUtil {

    public static String readString(ByteBuffer buffer){
        int stringLength = buffer.getInt();
        byte[] stringBytes = new byte[stringLength];
        buffer.get(stringBytes);
        return new String(stringBytes);
    }


}
