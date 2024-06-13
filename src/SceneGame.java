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
import javafx.scene.control.ProgressBar;
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

public class SceneGame extends BorderPane {
    private String motMystere;
    private String difficulte;
    private String touches;
    private int nbTentatives;
    private Clavier clavier;

    public SceneGame(String motMystere, String difficulte, String touches, int nbTentatives, Clavier clavier) {
        super();
        this.motMystere = motMystere;
        this.difficulte = difficulte;
        this.touches = touches;
        this.nbTentatives = nbTentatives;
        this.clavier = clavier;
        bCenter();
        bRight();
    }

    public void bCenter() {
        VBox center = new VBox();
        Text tfMotATrouve = new Text("***MALI*E*");
        tfMotATrouve.setFont(Font.font("Arial", 20));
        ImageView img = new ImageView(new Image("pendu" + this.nbTentatives + ".png"));
        ProgressBar pBar = new ProgressBar(this.nbTentatives / 10.0);
        center.getChildren().addAll(tfMotATrouve, img, pBar, clavier);
        this.setCenter(center);
    }

    public void bRight() {
        VBox right = new VBox();
        Text tfDifficulte = new Text("Niveau " + this.difficulte);
        TitledPane tBox = new TitledPane("Chronomètre", new Text("00:00"));
        Button btnNewMot = new Button("Nouveau mot");

        right.getChildren().addAll(tfDifficulte, tBox, btnNewMot);
        this.setRight(right);
    }

}
