import picocli.CommandLine;

@CommandLine.Command(
        name = "task",
        subcommands = {CreateTask.class, ReadTask.class, DeleteTask.class}
)

public class Main implements Runnable{

    @Override
    public void run(){
        System.out.println("Specify a subcommand: create, read, or delete");

    }

    public static void main(String[] args){
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }


}