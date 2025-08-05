package file2image.methods;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import file2image.ConvertWindow;
import file2image.data.SortingMode;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PureByte implements Method {

    private ConvertWindow[] convRef = new ConvertWindow[1];

    @Override
    public void Start(Scene scene, File Input, Path Output, SortingMode sorting) {
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
            //System.out.println(pixelAmount);
            int dimension = (int) Math.ceil(Math.sqrt(pixelAmount));
            BufferedImage BI = new BufferedImage(dimension, dimension, BufferedImage.TYPE_INT_RGB);
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
                        // System.out.println(index);
                        // System.out.println(line);
                        if (index >= dimension) {
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

                    int lastColor = 0;
                    int currentColor = 0;

                    int passes = 1000;

                    switch (sorting) {
                        case SortingMode.brightness:
                            for (int col = 0; col < BI.getWidth(); col++) {
                                for (int i = 0; i < passes; i++) {
                                    convRef[0].addLog("Sorting pass", i * col, passes * BI.getWidth());
                                    for (int y = 1; y < BI.getHeight(); y++) {
                                        lastColor = currentColor;
                                        currentColor = BI.getRGB(col, y);

                                        int r = (currentColor >> 16) & 0xFF;
                                        int g = (currentColor >> 8) & 0xFF;
                                        int b = currentColor & 0xFF;

                                        int re = (lastColor >> 16) & 0xFF;
                                        int gr = (lastColor >> 8) & 0xFF;
                                        int bl = lastColor & 0xFF;

                                        if ((r + g + b) < (re + gr + bl)) {
                                            BI.setRGB(col, y, lastColor);
                                            BI.setRGB(col, y - 1, currentColor);
                                        }
                                    }
                                }
                            }
                            break;

                        case SortingMode.red:
                            for (int col = 0; col < BI.getWidth(); col++) {
                                for (int i = 0; i < passes; i++) {
                                    convRef[0].addLog("Sorting pass", i * col, passes * BI.getWidth());
                                    for (int y = 1; y < BI.getHeight(); y++) {
                                        lastColor = currentColor;
                                        currentColor = BI.getRGB(col, y);

                                        int r = (currentColor >> 16) & 0xFF;

                                        int re = (lastColor >> 16) & 0xFF;

                                        if ((r) < (re)) {
                                            BI.setRGB(col, y, lastColor);
                                            BI.setRGB(col, y - 1, currentColor);
                                        }
                                    }
                                }
                            }
                            break;

                        case SortingMode.green:
                            for (int col = 0; col < BI.getWidth(); col++) {
                                for (int i = 0; i < passes; i++) {
                                    convRef[0].addLog("Sorting pass", i * col, passes * BI.getWidth());
                                    for (int y = 1; y < BI.getHeight(); y++) {
                                        lastColor = currentColor;
                                        currentColor = BI.getRGB(col, y);

                                        int b = currentColor & 0xFF;

                                        int bl = lastColor & 0xFF;

                                        if ((b) < (bl)) {
                                            BI.setRGB(col, y, lastColor);
                                            BI.setRGB(col, y - 1, currentColor);
                                        }
                                    }
                                }
                            }
                            break;

                        case SortingMode.blue:
                            for (int col = 0; col < BI.getWidth(); col++) {
                                for (int i = 0; i < passes; i++) {
                                    convRef[0].addLog("Sorting pass", i * col, passes * BI.getWidth());
                                    for (int y = 1; y < BI.getHeight(); y++) {
                                        lastColor = currentColor;
                                        currentColor = BI.getRGB(col, y);

                                        int g = (currentColor >> 8) & 0xFF;

                                        int gr = (lastColor >> 8) & 0xFF;

                                        if ((g) < (gr)) {
                                            BI.setRGB(col, y, lastColor);
                                            BI.setRGB(col, y - 1, currentColor);
                                        }
                                    }
                                }
                            }
                            break;

                        case SortingMode.hue:
                            for (int col = 0; col < BI.getWidth(); col++) {
                                for (int i = 0; i < passes; i++) {
                                    convRef[0].addLog("Sorting pass", i * col, passes * BI.getWidth());
                                    for (int y = 1; y < BI.getHeight(); y++) {
                                        lastColor = currentColor;
                                        currentColor = BI.getRGB(col, y);

                                        int r = (currentColor >> 16) & 0xFF;
                                        int g = (currentColor >> 8) & 0xFF;
                                        int b = currentColor & 0xFF;

                                        int re = (lastColor >> 16) & 0xFF;
                                        int gr = (lastColor >> 8) & 0xFF;
                                        int bl = lastColor & 0xFF;

                                        float[] currentHSB = Color.RGBtoHSB(r, g, b, null);
                                        float currenthue = currentHSB[0]; // 0.0 to 1.0

                                        float[] lastHSB = Color.RGBtoHSB(re, gr, bl, null);
                                        float lasthue = lastHSB[0]; // 0.0 to 1.0

                                        if ((currenthue) < (lasthue)) {
                                            BI.setRGB(col, y, lastColor);
                                            BI.setRGB(col, y - 1, currentColor);
                                        }
                                    }
                                }
                            }
                            break;

                        default:
                            break;
                    }
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
