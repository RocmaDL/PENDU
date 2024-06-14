import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * Permet de gérer un Text associé à une Timeline pour afficher un temps écoulé
 */
public class Chronometre extends Text {
    /**
     * timeline qui va gérer le temps
     */
    private Timeline timeline;
    /**
     * la fenêtre de temps
     */
    private KeyFrame keyFrame;
    /**
     * le contrôleur associé au chronomètre
     */
    private ControleurChronometre actionTemps;

    /**
     * Constructeur permettant de créer le chronomètre
     * avec un label initialisé à "0:0:0"
     * Ce constructeur créer la Timeline, la KeyFrame et le contrôleur
     */
    public Chronometre() {
        // A implémenter
        super();
        this.keyFrame = new KeyFrame(new Duration(1000));
        this.timeline = new Timeline(this.keyFrame);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.actionTemps = new ControleurChronometre(this);
    }

    /**
     * Permet au controleur de mettre à jour le text
     * la durée est affichée sous la forme m:s
     * 
     * @param tempsMillisec la durée depuis à afficher
     */
    public void setTime(long tempsMillisec) {
        // A implémenter

    }

    /**
     * Permet de démarrer le chronomètre
     */
    public void start() {
        // A implémenter
        timeline.play();
    }

    /**
     * Permet d'arrêter le chronomètre
     */
    public void stop() {
        // A implémenter
        timeline.pause();
    }

    /**
     * Permet de remettre le chronomètre à 0
     */
    public void resetTime() {
        // A implémenter
        timeline.stop();
        this.setText("0:0:0");
    }
}
