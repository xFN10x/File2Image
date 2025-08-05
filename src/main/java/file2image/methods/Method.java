package file2image.methods;

import java.io.File;
import java.nio.file.Path;

import file2image.data.SortingMode;
import javafx.scene.Scene;

public interface Method {

    void Start(Scene scene, File Input, Path Output, SortingMode sorting);
}
