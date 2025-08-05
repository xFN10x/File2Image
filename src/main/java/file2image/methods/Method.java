package file2image.methods;

import java.io.File;
import java.nio.file.Path;

import javafx.scene.Scene;

public interface Method {

    void Start(Scene scene, File Input, Path Output);
}
