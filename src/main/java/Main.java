import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
    public static void main (String[] args) {
        launch();
        Controller controller = new Controller();
        controller.run();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.show();
    }
}