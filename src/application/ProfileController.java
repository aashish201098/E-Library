package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

public class ProfileController implements Initializable {
	
	@FXML TextField useridtf;
	@FXML TextField usernametf;
	@FXML TextField phonetf;
	@FXML TextField emailtf;
	@FXML TextField passtf;
	@FXML Label ccnlbl;
	@FXML Label balancelbl;
	@FXML Label successLabel;
	@FXML Button cartButton;
	@FXML Label myName;
	
	@FXML
	public void saveProfile(ActionEvent event) {
		if(usernametf.getText().equals(null)) {
			successLabel.setText("Enter the User Name");
		}else if(phonetf.getText().equals(null)) {
			successLabel.setText("Enter the Phone Number");
		}else if(emailtf.getText().equals(null)) {
			successLabel.setText("Enter the Email");
		}else if(passtf.getText().equals(null)) {
			successLabel.setText("Enter the Password");
		}else {
			String username = usernametf.getText();
			String phone = phonetf.getText();
			String email = emailtf.getText();
			String pass = passtf.getText();
			Statement st = DBConnection.getStatement();
			String query = "update user set name = '"+username+"', pass = '"+pass+"', phone = '"+phone+"', email = '"+email+"' where userid = "+Main.currentUser+";";
			
			try {
				st.executeUpdate(query);
				myName.setText(username);
				Main.currentUserName = username;
				successLabel.setText("Successfully Saved the changes.");
			} catch (SQLException e) {
				System.out.println("Error in Profile Controller");
			}
			
		}
	}
	
	
	@FXML
	public void cancelButton(ActionEvent event) throws IOException {
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/application/UserMain1.fxml").openStream());
		Scene sc = new Scene(root,1400,850);
		primaryStage.setScene(sc);
		primaryStage.show();
	}
	
	@FXML
	public void library(ActionEvent event) throws IOException {
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root = loader.load(getClass().getResource("/application/library.fxml").openStream());
		Scene sc = new Scene(root,1400,850);
		primaryStage.setScene(sc);
		primaryStage.show();
	}
	
	@FXML
	public void my_books(ActionEvent event) throws IOException {
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root = loader.load(getClass().getResource("/application/My-Books.fxml").openStream());
		Scene sc = new Scene(root,1400,850);
		primaryStage.setScene(sc);
		primaryStage.show();
	}
	
	@FXML
	public void profile(ActionEvent event) throws IOException {
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root = loader.load(getClass().getResource("/application/Profile.fxml").openStream());
		Scene sc = new Scene(root,1400,850);
		primaryStage.setScene(sc);
		primaryStage.show();
	}
	
	@FXML
	public void cart(ActionEvent event) throws IOException {
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root = loader.load(getClass().getResource("/application/Cart.fxml").openStream());
		Scene sc = new Scene(root,1400,850);
		primaryStage.setScene(sc);
		primaryStage.show();
	}

	@FXML
	public void homepage(MouseEvent event) throws IOException{
        ((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root = loader.load(getClass().getResource("/application/UserMain1.fxml").openStream());
		Scene sc = new Scene(root,1400,850);
		sc.getStylesheets().add(getClass().getResource("application1.css").toExternalForm());
		primaryStage.setScene(sc);
		primaryStage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		myName.setText(Main.currentUserName);
		int userid = Main.currentUser;
		Statement st = DBConnection.getStatement();
		String query = "select * from user where userid = "+ userid +";";
		ResultSet rs = null;
		try {
			rs = st.executeQuery(query);
			if(rs.next()) {
				useridtf.setText(String.valueOf(userid));
				usernametf.setText(rs.getString("name"));
				phonetf.setText(rs.getString("phone"));
				emailtf.setText(rs.getString("email"));
				ccnlbl.setText(rs.getString("ccn"));
				passtf.setText(rs.getString("pass"));
				balancelbl.setText(String.valueOf(rs.getInt("balance")));
			}else {
				
			}
		} catch (SQLException e) {
			System.out.println("Error in Profile Controller");
		}
		
		if(Main.count != 0) {
        	cartButton.setText("Cart (" + String.valueOf(Main.count) + ")");
        }else {
        	cartButton.setText("Cart");
        }
		
	}
	
	@FXML
	public void logout(ActionEvent event) throws IOException {
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/application/UserLog.fxml").openStream());
		Scene sc = new Scene(root,1280,720);
		primaryStage.setScene(sc);
		primaryStage.show();
	}
	
	@FXML
	public void help(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        Stage helpStage = null;
		alert.initOwner(helpStage );
        alert.getDialogPane().setContentText("e-library is an e-store where a user can buy books digitally and then read them from the My_Books section. The user can browse through the available books by clicking on each item of the list. Then he can add the selected book to the Shopping Cart and proceed to Payment. Each book can be purchased only once due to its linking with the user's account.");
        alert.getDialogPane().setHeaderText(null);
        alert.showAndWait().filter(response -> response == ButtonType.CLOSE); 
	}
	
	@FXML
	public void addBalance(ActionEvent event) throws IOException {
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/application/updateBalance.fxml").openStream());
		Scene sc = new Scene(root,500,318);
		primaryStage.setScene(sc);
		primaryStage.show();
		
	}
	
	
}
