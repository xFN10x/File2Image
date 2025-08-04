package file2image;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        TabPane tabs = new TabPane();

        TextField inputFilePathField = new TextField("Please select a file.");

        

        Pane singleModePane = new Pane(inputFilePathField);

        Tab SingleTab = new Tab("Single File");
        SingleTab.setClosable(false);
        SingleTab.setContent(singleModePane);
        tabs.getTabs().add(SingleTab);

        Scene scene = new Scene(tabs, 400, 400);

        primaryStage.getIcons().add(new Image(getClass().getResource("/icon.png").toExternalForm()));
        primaryStage.setTitle("File2Img");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
