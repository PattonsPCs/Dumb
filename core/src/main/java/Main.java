import java.time.LocalTime;

public class Main {

    public static void main(String[] args){
        LocalTime now = LocalTime.now();

        long millisSinceMidnight = now.toSecondOfDay() * 1000L + now.getNano() / 1000_000;
        System.out.println(millisSinceMidnight);
    }
}
