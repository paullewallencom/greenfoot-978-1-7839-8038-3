import greenfoot.*;

public class FileCommands implements MenuCommands {   
    public void execute(int idx, World w) {
        switch(idx) {
            case 0:
                System.out.println("Running New command");
                break;
            case 1:
                System.out.println("Running Open command");
                break;
            case 2:
                System.out.println("Running Save command");
                break;
            case 3:
                System.out.println("Running Close command");
                break;
            case 4:
                System.out.println("Running Exit command");
                break;
        }
    }
}
