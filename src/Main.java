import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        stage.initStyle(StageStyle.DECORATED);
        BorderPane root = new BorderPane();
        Button poker = new Button("Vietnamese Northern Poker");
        Button threeCard = new Button("Three Cards Game");

        HBox hbox = new HBox(5, poker, threeCard);

        root.setCenter(hbox);

        Scene scene = new Scene(root, 300, 200);

        stage.setScene(scene);
        stage.setTitle("Card Kingdom");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
