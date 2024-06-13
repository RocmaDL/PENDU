import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Génère la vue d'un clavier et associe le contrôleur aux touches
 * le choix ici est d'un faire un héritié d'un TilePane
 */
public class Clavier extends TilePane {
    /**
     * il est conseillé de stocker les touches dans un ArrayList
     */
    private List<Button> clavier;

    /**
     * constructeur du clavier
     * 
     * @param touches       une chaine de caractères qui contient les lettres à
     *                      mettre sur les touches
     * @param actionTouches le contrôleur des touches
     * @param tailleLigne   nombre de touches par ligne
     */
    public Clavier(String touches, EventHandler<ActionEvent> actionTouches) {
        super();
        this.clavier = new ArrayList<Button>();
        for (int i = 0; i < touches.length(); i++) {
            Button b = new Button(touches.charAt(i) + "");
            b.setOnAction(actionTouches);
            clavier.add(b);
        }
        this.getChildren().addAll(clavier);
    }

    public Clavier(String touches, EventHandler<ActionEvent> actionTouches, int tailleLigne) {
        this(touches, actionTouches);
        this.setPrefColumns(tailleLigne);
    }

    /**
     * permet de désactiver certaines touches du clavier (et active les autres)
     * 
     * @param touchesDesactivees une chaine de caractères contenant la liste des
     *                           touches désactivées
     */
    public void desactiveTouches(Set<String> touchesDesactivees) {
        for (Button b : clavier) {
            if (touchesDesactivees.contains(b.getText())) {
                b.setDisable(true);
            }
        }
    }
}
