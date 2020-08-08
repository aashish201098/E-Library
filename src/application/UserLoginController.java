package application;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UserLoginController {
	@FXML private TextField usernametf;
	@FXML private TextField passwordtf;
	@FXML private Hyperlink newuserlbl;
	@FXML private Hyperlink adminloginlbl;
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
	
	public void userLogin(ActionEvent event) throws IOException {
		if(usernametf.getText().equals("")) {
			//statuslbl.setText("Please enter the Username");
			alert("Please enter the email id.");
		}else if(passwordtf.getText().equals("")) {
			//statuslbl.setText("Please enter the Password");
			alert("Please enter the Password.");
		}else {
			String username = usernametf.getText();
			
			String pass = passwordtf.getText();
			
			
			Statement st = DBConnection.getStatement();
			String query = "select * from user where email = '"+  username + "' and pass = '"+ pass+" ';";
			ResultSet rs = null;
			try {
				rs = st.executeQuery(query);
				
				if(rs.next()) {

					Main.currentUser = rs.getInt("userid");
					Main.loggedIn = true;
					Main.currentUserName = rs.getString("name");
				
					query = "select userid from cart where userid = "+Main.currentUser+";";
					rs = st.executeQuery(query);
					int count=0;
					while(rs.next()) {
						count++;
					}
					Main.count = count;
					
					((Node)event.getSource()).getScene().getWindow().hide();
					Stage primaryStage = new Stage();
					FXMLLoader loader = new FXMLLoader();
					Pane root = loader.load(getClass().getResource("/application/UserMain1.fxml").openStream());
					Scene sc = new Scene(root,1400,850);
					primaryStage.setScene(sc);
					primaryStage.show();
					
				}else {
					alert("Invalid Credentials.");
				}
			} catch (SQLException e) {
				System.out.println("Error in sql query");
			}
			
		}
	}
	
	
	public void newUser(MouseEvent event) throws IOException {
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/application/UserRegister.fxml").openStream());
		Scene sc = new Scene(root,700,750);
		primaryStage.setScene(sc);
		primaryStage.show();
	}
	
	public void adminLogin(MouseEvent event) throws IOException {
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/application/AdminLogin.fxml").openStream());
		Scene sc = new Scene(root,1280,720);
		primaryStage.setScene(sc);
		primaryStage.show();
	}
}
