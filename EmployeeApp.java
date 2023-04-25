/* Employee Management System 2023
 * Created by: Waleed Khan
 * Last modified: March 29, 2023
 * This is the Application class for a JavaFX FXML project for managing employees. */
package employee_manager_Khan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EmployeeApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root =
            FXMLLoader.load(getClass().getResource("EmployeeView.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Waleed's Employee Management System");
        stage.setScene(scene);
        stage.show();
    }//end start

    public static void main(String[] args) {
        launch(args);
    }//end main

}//end class EmployeeApp
