package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("FulfilmentCenterGUIFX");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();

        //Set on close
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            Controller myController = loader.getController();
            @Override
            public void handle(WindowEvent e) {
                //pop dialog
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Save changes");
                alert.setContentText("Do you want to save current state?");
                ButtonType buttonTypeYes = new ButtonType("Yes");
                ButtonType buttonTypeNo = new ButtonType("No");
                alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonTypeYes){
                    myController.saveCurrentState();
                }
                //exit
                Platform.exit();
                System.exit(0);
            }
        });

    }


}