import javafx.application.Application;
import javafx.application.Platform;
// import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;

public class SceneHome extends GridPane {
    private Button btn;

    public SceneHome(Button btn) {
        super();
        this.btn = btn;
        this.add(btn, 0, 0);

        final ToggleGroup group = new ToggleGroup();
        RadioButton rb1 = new RadioButton("Facile");
        RadioButton rb2 = new RadioButton("Médium");
        RadioButton rb3 = new RadioButton("Difficile");
        RadioButton rb4 = new RadioButton("Expert");

        rb1.setToggleGroup(group);
        rb2.setToggleGroup(group);
        rb3.setToggleGroup(group);
        rb4.setToggleGroup(group);

        VBox radioBox = new VBox();
        radioBox.getChildren().addAll(rb1, rb2, rb3, rb4);

        TitledPane tBox = new TitledPane("Niveau de difficulté", radioBox);
        this.add(tBox, 0, 1);
    }
}
