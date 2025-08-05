package file2image;

import javafx.scene.control.TextArea;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ConvertWindow extends Stage {

    protected final ProgressBar prog = new ProgressBar();
    protected final TextArea log = new TextArea();
    protected final StringBuilder logText = new StringBuilder("Log Start");
    public final double totalBytes;
    public  double amount;
    protected final Timer timer = new Timer();

    public void dispose() {
        setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {
                return;
            }

        });
        timer.cancel();
        close();
    }

    public void setBytesRead(double amount) {
        this.amount = amount;
    }

    public ConvertWindow(long byteAmount) {
        super();
        totalBytes = byteAmount;
    }

    public void init() {
        log.setEditable(false);

        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                Platform.runLater(() -> {
                    log.setText(logText.toString());

                    prog.setProgress((amount / totalBytes));
                    logText.insert(0, "Wrote pixel: " + String.valueOf((long) amount) + "/"
                            + String.valueOf((long) totalBytes) + "\n");
                    if (logText.length() > 1000) {
                        logText.setLength(500);
                    }
                    System.out.println(prog.getProgress());
                });
            }

        }, 0, 1);

        log.textProperty().addListener(c -> {
            // log.setScrollTop(Double.MIN_VALUE);
            log.setMaxHeight(100);
        });

        log.setMaxHeight(70);
        prog.setSnapToPixel(false);
        prog.setMinWidth(300);

        VBox stuff = new VBox(log, prog);
        VBox.setVgrow(log, Priority.NEVER);

        StackPane mainPane = new StackPane(stuff);

        Scene scene = new Scene(mainPane, 300, 100);
        setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {
                event.consume();
            }

        });

        getIcons().add(new Image(getClass().getResource("/icon.png").toExternalForm()));
        setTitle("Converting...");
        setScene(scene);
        initModality(Modality.APPLICATION_MODAL);

    }
}
