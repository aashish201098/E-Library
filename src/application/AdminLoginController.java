package application;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdminLoginController {
	@FXML private TextField usernametf;
	@FXML private TextField passwordtf;
	@FXML private Hyperlink userloginlbl;
	@FXML private Label statuslbl;
	Stage helpStage;
	
	public void alert(String st)
	{
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(helpStage);
        alert.getDialogPane().setContentText(st);
        alert.getDialogPane().setHeaderText(null);
        alert.showAndWait().filter(response -> response == ButtonType.CLOSE);
	}
	
	public void adminLogin(ActionEvent event) throws IOException {
		if(usernametf.getText().equals("")) {
			//statuslbl.setText("Please enter the Username");
			alert("Please enter the Username");
		}else if(passwordtf.getText().equals("")) {
			//statuslbl.setText("Please enter the Password");
			alert("please enter the password");
		}else {
			String username = usernametf.getText();
			String pass = passwordtf.getText();
			if(username.equals("aashish") && pass.equals("aashish")) {
				//statuslbl.setText("Login Successful");
				
				gotoAdminHome(event);
				
			}else {
				//statuslbl.setText("Invalid Credentials Entered");
				alert("Invalid Credentials.");
			}
		}
	}
	
	public void gotoAdminHome(ActionEvent event) throws IOException {
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/application/deleteBookDetails.fxml").openStream());
		Scene sc = new Scene(root,1400,850);
		primaryStage.setScene(sc);
		primaryStage.show();
	}
	
	public void userLogin(MouseEvent event) throws IOException {
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/application/UserLog.fxml").openStream());
		Scene sc = new Scene(root,1280,720);
		primaryStage.setScene(sc);
		primaryStage.show();
	}
	
}
