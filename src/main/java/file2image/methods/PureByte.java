package file2image.methods;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import file2image.ConvertWindow;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PureByte implements Method {

    private ConvertWindow[] convRef = new ConvertWindow[1];

    @Override
    public void Start(Scene scene, File Input, Path Output) {
        try {

            Platform.runLater(() -> {
                try {
                    ConvertWindow convertWin = new ConvertWindow((int) (Files.size(Input.toPath()) / 3));
                    convertWin.init();
                    convertWin.show();
                    convRef[0] = convertWin;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            int pixelAmount = (int) (Files.size(Input.toPath()) / 3);
            // byte[] InputBytes = Files.readAllBytes(Input.toPath()); too much ram
            System.out.println(pixelAmount);
            int width = 128;
            int height = (int) Math.ceil((double) pixelAmount / width);
            BufferedImage BI = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            File outFile = Output.toFile();
            long starttime = System.currentTimeMillis();

            Thread main = new Thread(() -> {
                try {

                    java.util.List<Byte> bytes = new ArrayList<Byte>();
                    int index = 0;
                    int line = 0;
                    InputStream stream = Files.newInputStream(Input.toPath());
                    byte[] pixelByteTrio = new byte[3];
                    for (int i = 0; i < pixelAmount; i++) {
                        stream.readNBytes(pixelByteTrio, 0, 3);
                        final long bytesread = i;

                        if (convRef[0] != null)
                            convRef[0].setBytesRead(bytesread);

                        if (index >= width) {
                            line++;
                            index = 0;
                        }

                        Color colour = new Color(
                                Byte.toUnsignedInt(pixelByteTrio[0]),
                                Byte.toUnsignedInt(pixelByteTrio[1]),
                                Byte.toUnsignedInt(pixelByteTrio[2]));
                        BI.setRGB(index, line, colour.getRGB());
                        index++;
                        bytes.clear();

                    }
                    stream.close();

                    if (!outFile.exists())
                        outFile.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            main.run();

            ImageIO.write(BI, "png", outFile);
            Platform.runLater(() -> {
                convRef[0].dispose();

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Conversion Success");
                alert.setContentText(
                        "The file was converted in " + Math.abs(System.currentTimeMillis() - starttime) + " ms.");
                alert.show();
            });
        } catch (Exception e) {
            e.printStackTrace();
            Platform.runLater(() -> {
                convRef[0].dispose();

                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Conversion Failed");
                alert.setHeaderText(e.getMessage());
                alert.setContentText("The conversion failed.");
                alert.show();
            });
            return;
        }
    }

}
