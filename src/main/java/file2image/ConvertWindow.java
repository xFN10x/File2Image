package file2image;

import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConvertWindow extends Stage {

    public ConvertWindow(Scene scene) {
        super();
        getIcons().add(new Image(getClass().getResource("/icon.png").toExternalForm()));
        setTitle("Converting...");
        setScene(scene);
        initModality(Modality.APPLICATION_MODAL);
        //show();
    }
}
