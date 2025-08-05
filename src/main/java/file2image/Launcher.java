package file2image;

import java.io.File;
import java.nio.file.Path;

import file2image.methods.PureByte;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        TabPane tabs = new TabPane();

        TextField inputFilePathField = new TextField("Please select a file.");
        inputFilePathField.setEditable(false);
        inputFilePathField.setMinSize(320, 25);
        Button inputFileButton = new Button("...");
        inputFileButton.setOnAction(e -> {
            FileChooser loadUI = new FileChooser();
            // loadUI.setInitialFileName("out.png");

            inputFilePathField.setText(loadUI.showOpenDialog(primaryStage).getAbsolutePath());
        });

        TextField outputFilePathField = new TextField("Please select a file.");
        outputFilePathField.setEditable(false);
        outputFilePathField.setMinSize(320, 25);
        Button outputFileButton = new Button("...");
        outputFileButton.setOnAction(e -> {
            FileChooser saveUI = new FileChooser();
            saveUI.setInitialFileName("out.png");
            saveUI.getExtensionFilters().addAll(new ExtensionFilter("PNG Image", "*.png"),
                    new ExtensionFilter("JPEG Image", "*.jpeg", "*.jpg"), new ExtensionFilter("Bitmap Image", "*.bmp"));

            outputFilePathField.setText(saveUI.showSaveDialog(primaryStage).getAbsolutePath());
        });

        Label methodText = new Label("Selected Method:");
        ComboBox<String> methodSelection = new ComboBox<String>();
        methodSelection.getItems().addAll("Pure Byte");
        methodSelection.setValue(methodSelection.getItems().get(0));

        HBox inputStuff = new HBox(8, inputFilePathField, inputFileButton);
        inputStuff.setAlignment(Pos.CENTER);
        HBox outputStuff = new HBox(8, outputFilePathField, outputFileButton);
        outputStuff.setAlignment(Pos.CENTER);
        HBox methodStuff = new HBox(8, methodText, methodSelection);
        methodStuff.setAlignment(Pos.CENTER);

        Button convertButton = new Button("Convert");
        convertButton.setMinSize(380, 30);
        convertButton.setOnAction(e -> {
            new Thread(() -> new PureByte().Start(new File(inputFilePathField.getText()),
                    Path.of(outputFilePathField.getText()))).run();
        });

        VBox stuff = new VBox(10, inputStuff, outputStuff, methodStuff, convertButton);
        stuff.setAlignment(Pos.TOP_CENTER);
        stuff.setPadding(new Insets(20, 0, 0, 0));

        StackPane singleModePane = new StackPane(stuff);

        Tab SingleTab = new Tab("Single File");
        SingleTab.setClosable(false);
        SingleTab.setContent(singleModePane);
        tabs.getTabs().add(SingleTab);

        Scene scene = new Scene(tabs, 400, 195);

        primaryStage.getIcons().add(new Image(getClass().getResource("/icon.png").toExternalForm()));
        primaryStage.setTitle("File2Img");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
