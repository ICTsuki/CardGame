import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javafx.scene.input.MouseEvent;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        stage.initStyle(StageStyle.DECORATED);
        BorderPane root = new BorderPane();
        Button poker = new Button("Vietnamese Northern Poker");
        Button threeCard = new Button("Three Cards Game");

        poker.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
            }
        });

        HBox hbox = new HBox(5, poker, threeCard);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(10));


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
