import controller.Controller;
import model.MeleeMilitary;

public class Main {
    public static void main(String[] args) {

        MeleeMilitary meleeMilitary =new MeleeMilitary(1,1,null,1,1,null);
        meleeMilitary.move(1,2);

        Controller controller = new Controller();
        controller.run();
    }
}
