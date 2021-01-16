import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TabPane;


public class AppController {
    @FXML
    private Canvas townCanvas;

    @FXML
    public void initialize() {
        //tabPane.setTabDragPolicy(TabPane.TabDragPolicy.REORDER);
    }

    public GraphicsContext getTownGraphics() {
        return townCanvas.getGraphicsContext2D();
    }
}
