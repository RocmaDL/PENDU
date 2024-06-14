import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ButtonBar.ButtonData;

import java.util.List;
import java.util.Arrays;
import java.io.File;
import java.util.ArrayList;

/**
 * Vue du jeu du pendu
 */
public class Pendu extends Application {

    /**
     * modèle du jeu
     **/
    private MotMystere modelePendu;
    /**
     * Liste qui contient les images du jeu
     */
    private ArrayList<Image> lesImages;
    /**
     * Liste qui contient les noms des niveaux
     */
    public List<String> niveaux;

    // les différents contrôles qui seront mis à jour ou consultés pour l'affichage
    /**
     * le dessin du pendu
     */
    private ImageView dessin;
    /**
     * le mot à trouver avec les lettres déjà trouvé
     */
    private Text motCrypte;
    /**
     * la barre de progression qui indique le nombre de tentatives
     */
    private ProgressBar pg;
    /**
     * le clavier qui sera géré par une classe à implémenter
     */
    private Clavier clavier;
    /**
     * le text qui indique le niveau de difficulté
     */
    private Text leNiveau;
    /**
     * le chronomètre qui sera géré par une clasee à implémenter
     */
    private Chronometre chrono;
    /**
     * le panel Central qui pourra être modifié selon le mode (accueil ou jeu)
     */
    private BorderPane panelCentral;
    /**
     * le bouton Paramètre / Engrenage
     */
    private Button boutonParametres;
    /**
     * le bouton Accueil / Maison
     */
    private Button boutonMaison;
    /**
     * le bouton qui permet de (lancer ou relancer une partie
     */
    private Button bJouer;

    private Stage stage;

    private Scene scene;

    /**
     * initialise les attributs (créer le modèle, charge les images, crée le chrono
     * ...)
     */
    @Override
    public void init() {
        this.modelePendu = new MotMystere("/usr/share/dict/french", 3, 10, MotMystere.FACILE, 10);
        this.lesImages = new ArrayList<Image>();
        this.chargerImages("./img");
        // A terminer d'implementer
        this.chrono = new Chronometre();
    }

    /**
     * @return le graphe de scène de la vue à partir de methodes précédantes
     */
    private Scene laScene() {
        this.panelCentral = new BorderPane();
        this.panelCentral.setTop(this.titre());
        this.scene = new Scene(this.panelCentral, 800, 600);
        return this.scene;
    }

    /**
     * @return le panel contenant le titre du jeu
     */
    private Pane titre() {
        Text titre = new Text("Jeu du pendu");
        titre.setFont(Font.font("Arial", 24));

        Button boutonParametres = new Button();
        Button boutonMaison = new Button();
        Button boutonInformation = new Button();

        boutonMaison.setGraphic(new ImageView(new Image("home.png", 30.0, 30.0, true, true)));
        boutonParametres.setGraphic(new ImageView(new Image("parametres.png", 30.0, 30.0, true, true)));
        boutonInformation.setGraphic(new ImageView(new Image("info.png", 30.0, 30.0, true, true)));

        boutonInformation.setMaxWidth(30.0);
        boutonInformation.setMaxHeight(30.0);
        boutonParametres.setMaxWidth(30.0);
        boutonParametres.setMaxHeight(30.0);
        boutonMaison.setMaxWidth(30.0);
        boutonMaison.setMaxHeight(30.0);

        boutonMaison.setId("maison");

        boutonMaison.setOnAction(new ControleurLancerPartie(modelePendu, this));

        BorderPane bTop = new BorderPane();
        bTop.setLeft(titre);
        bTop.setRight(new HBox(boutonParametres, boutonMaison, boutonInformation));
        return bTop;
    }

    // /**
    // * @return le panel du chronomètre
    // */
    // private TitledPane leChrono(){
    // A implementer
    // TitledPane res = new TitledPane();
    // return res;
    // }

    /**
     * @return la fenêtre de jeu avec le mot crypté, l'image, la barre
     *         de progression et le clavier
     */
    private Pane fenetreJeu() {
        // A implementer
        BorderPane scene = new BorderPane();
        // Mise en place du centre
        VBox center = new VBox();
        Text tfMotATrouve = new Text(this.modelePendu.getMotCrypte());
        tfMotATrouve.setFont(Font.font("Arial", 20));
        int tentatives = this.modelePendu.getNbErreursMax() - this.modelePendu.getNbErreursRestants();
        System.out.println(tentatives);
        ImageView img = new ImageView(new Image(
                "pendu" + tentatives + ".png"));
        ProgressBar pBar = new ProgressBar(tentatives / Double.valueOf(this.modelePendu.getNbErreursMax()));
        System.out.println(tentatives / 10);
        this.clavier = new Clavier("AZERTYUIOPQSDFGHJKLMWXCVBN", new ControleurLettres(this.modelePendu, this));
        clavier.desactiveTouches(this.modelePendu.getLettresEssayees());
        center.getChildren().addAll(tfMotATrouve, img, pBar, clavier);
        scene.setCenter(center);
        // Mise en place de la partie droite
        VBox right = new VBox();
        String niveau = "";
        if (this.modelePendu.getNiveau() == MotMystere.FACILE) {
            niveau = "Facile";
        } else if (this.modelePendu.getNiveau() == MotMystere.MOYEN) {
            niveau = "Moyen";
        } else if (this.modelePendu.getNiveau() == MotMystere.DIFFICILE) {
            niveau = "Difficile";
        } else if (this.modelePendu.getNiveau() == MotMystere.EXPERT) {
            niveau = "Expert";
        }
        Text tfDifficulte = new Text("Niveau " + niveau);
        TitledPane tBox = new TitledPane("Chronomètre", new Text("00:00")); // ! A connecter au chrono
        Button btnNewMot = new Button("Nouveau mot");
        btnNewMot.setId("rejouer");
        btnNewMot.setOnAction(new ControleurLancerPartie(this.modelePendu, this));
        right.getChildren().addAll(tfDifficulte, tBox, btnNewMot);
        scene.setRight(right);
        return scene;
    }

    // /**
    // * @return la fenêtre d'accueil sur laquelle on peut choisir les paramètres de
    // jeu
    // */
    private Pane fenetreAccueil() {
        this.bJouer = new Button("Lancer une partie");
        this.bJouer.setOnAction(new ControleurLancerPartie(this.modelePendu, this));
        this.bJouer.setId("lancer");
        GridPane scene = new GridPane();
        scene.add(bJouer, 0, 0);

        final ToggleGroup group = new ToggleGroup();
        RadioButton rb1 = new RadioButton("Facile");
        RadioButton rb2 = new RadioButton("Médium");
        RadioButton rb3 = new RadioButton("Difficile");
        RadioButton rb4 = new RadioButton("Expert");

        rb1.setOnAction(new ControleurNiveau(this.modelePendu));
        rb2.setOnAction(new ControleurNiveau(this.modelePendu));
        rb3.setOnAction(new ControleurNiveau(this.modelePendu));
        rb4.setOnAction(new ControleurNiveau(this.modelePendu));

        rb1.setToggleGroup(group);
        rb2.setToggleGroup(group);
        rb3.setToggleGroup(group);
        rb4.setToggleGroup(group);

        if (this.modelePendu.getNiveau() == MotMystere.FACILE) {
            rb1.setSelected(true);
            this.modelePendu.setNiveau(MotMystere.FACILE);
        } else if (this.modelePendu.getNiveau() == MotMystere.MOYEN) {
            rb2.setSelected(true);
            this.modelePendu.setNiveau(MotMystere.MOYEN);
        } else if (this.modelePendu.getNiveau() == MotMystere.DIFFICILE) {
            rb3.setSelected(true);
            this.modelePendu.setNiveau(MotMystere.DIFFICILE);
        } else if (this.modelePendu.getNiveau() == MotMystere.EXPERT) {
            rb4.setSelected(true);
            this.modelePendu.setNiveau(MotMystere.EXPERT);
        }

        VBox radioBox = new VBox();
        radioBox.getChildren().addAll(rb1, rb2, rb3, rb4);

        TitledPane tBox = new TitledPane("Niveau de difficulté", radioBox);
        scene.add(tBox, 0, 1);
        return scene;
    }

    /**
     * charge les images à afficher en fonction des erreurs
     * 
     * @param repertoire répertoire où se trouvent les images
     */
    private void chargerImages(String repertoire) {
        for (int i = 0; i < this.modelePendu.getNbErreursMax() + 1; i++) {
            File file = new File(repertoire + "/pendu" + i + ".png");
            System.out.println(file.toURI().toString());
            this.lesImages.add(new Image(file.toURI().toString()));
        }
    }

    public void modeAccueil() {
        this.panelCentral.setCenter(this.fenetreAccueil());
        this.scene.setRoot(this.panelCentral);
        this.chrono.stop();
    }

    public void modeJeu() {
        this.panelCentral.setCenter(this.fenetreJeu());
        this.scene.setRoot(this.panelCentral);
        this.chrono.start();
    }

    public void modeParametres() {
        // A implémenter
    }

    /** lance une partie */
    public void lancePartie() {
        // A implementer
        this.modelePendu = new MotMystere("/usr/share/dict/french", 3, 10, this.modelePendu.getNiveau(), 10);
    }

    /**
     * raffraichit l'affichage selon les données du modèle
     */
    public void majAffichage() {
        System.out.println(this.modelePendu.getMotATrouve());
        // A implementer
        modeJeu();
        if (this.modelePendu.gagne()) {
            Alert alert = popUpMessageGagne();
            alert.showAndWait();
        } else if (this.modelePendu.perdu()) {
            Alert alert = popUpMessagePerdu();
            alert.showAndWait();
        }

    }

    /**
     * accesseur du chronomètre (pour les controleur du jeu)
     * 
     * @return le chronomètre du jeu
     */
    public Chronometre getChrono() {
        // A implémenter
        return null; // A enlever
    }

    public Alert popUpPartieEnCours() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "La partie est en cours!\n Etes-vous sûr de l'interrompre ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Attention");
        return alert;
    }

    public Alert popUpReglesDuJeu() {
        // A implementer
        String messageTitre = "Règles du jeu";
        String message = "Le jeu du pendu consiste à deviner un mot en proposant des lettres.\n"
                + "Le joueur a droit à un certain nombre d'erreurs avant de perdre la partie.\n"
                + "Le nombre d'erreurs est défini par le niveau de difficulté choisi.\n"
                + "Le joueur peut choisir un niveau de difficulté parmi les 4 proposés.\n"
                + "Le joueur peut relancer une partie à tout moment.\n" + "Bonne chance!";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(messageTitre);
        alert.setContentText(message);
        return alert;
    }

    public Alert popUpMessageGagne() {
        // A implementer
        String messageTitre = "Vous avez gagné!";
        String message = "Bravo! Vous avez trouvé le mot: " + this.modelePendu.getMotATrouve();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(messageTitre);
        alert.setContentText(message);
        return alert;
    }

    public Alert popUpMessagePerdu() {
        // A implementer
        String messageTitre = "Vous avez perdu!";
        String message = "Vous avez perdu" + "\n" + "Le mot était: " + this.modelePendu.getMotATrouve();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(messageTitre);
        alert.setContentText(message);
        return alert;

    }

    /**
     * créer le graphe de scène et lance le jeu
     * 
     * @param stage la fenêtre principale
     */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        this.stage.setTitle("IUTEAM'S - La plateforme de jeux de l'IUTO");
        this.stage.setScene(this.laScene());

        this.modeAccueil();
        this.stage.show();
    }

    /**
     * Programme principal
     * 
     * @param args inutilisé
     */
    public static void main(String[] args) {
        launch(args);
    }

    public MotMystere getModelePendu() {
        return modelePendu;
    }

    public ArrayList<Image> getLesImages() {
        return lesImages;
    }

    public List<String> getNiveaux() {
        return niveaux;
    }

    public ImageView getDessin() {
        return dessin;
    }

    public Text getMotCrypte() {
        return motCrypte;
    }

    public ProgressBar getPg() {
        return pg;
    }

    public Clavier getClavier() {
        return clavier;
    }

    public Text getLeNiveau() {
        return leNiveau;
    }

    public BorderPane getPanelCentral() {
        return panelCentral;
    }

    public Button getBoutonParametres() {
        return boutonParametres;
    }

    public Button getBoutonMaison() {
        return boutonMaison;
    }

    public Button getbJouer() {
        return bJouer;
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
    }

}
