package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class updateBalanceController {
	@FXML private TextField ccntf;
	@FXML private TextField cvvtf;
	@FXML private TextField balancetf;
	
	@FXML
	public void updateBalance(ActionEvent event) throws IOException {
		ResultSet rs = null;
		int userid = Main.currentUser;
		
		if(ccntf.getText().equals("") || cvvtf.getText().equals("") || balancetf.getText().equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
            alert.initModality(Modality.APPLICATION_MODAL);
            Stage warningStage = null;
			alert.initOwner(warningStage);
            alert.getDialogPane().setContentText("Please enter all the details");
            alert.getDialogPane().setHeaderText(null);
            alert.showAndWait().filter(response -> response == ButtonType.OK);
		}else {
		
			String ccn = ccntf.getText();
			String cvv = cvvtf.getText();
			int bal = Integer.parseInt(balancetf.getText());
			
			Statement st = DBConnection.getStatement();
			String query = "select ccn,cvv,balance from user where userid="+userid+";";
			
			try {
				rs = st.executeQuery(query);
				if(rs.next()) {
					int adder= rs.getInt("balance");
					adder += bal;
					if(rs.getString("ccn").equals(ccn) && String.valueOf(rs.getInt("cvv")).equals(cvv)) {
						query = "update user set balance = "+adder+" where userid = "+userid+";";
						st.executeUpdate(query);
						Alert alert = new Alert(AlertType.WARNING);
			            alert.initModality(Modality.APPLICATION_MODAL);
			            Stage warningStage = null;
						alert.initOwner(warningStage);
			            alert.getDialogPane().setContentText("Balance Added Succesfully.");
			            alert.getDialogPane().setHeaderText(null);
			            alert.showAndWait().filter(response -> response == ButtonType.OK);
						((Node)event.getSource()).getScene().getWindow().hide();
						Stage primaryStage = new Stage();
						FXMLLoader loader = new FXMLLoader();
						Pane root = loader.load(getClass().getResource("/application/Profile.fxml").openStream());
						Scene sc = new Scene(root,1400,850);
						primaryStage.setScene(sc);
						primaryStage.show();
					}else {
						Alert alert = new Alert(AlertType.WARNING);
			            alert.initModality(Modality.APPLICATION_MODAL);
			            Stage warningStage = null;
						alert.initOwner(warningStage);
			            alert.getDialogPane().setContentText("Invlaid Credentials. Please Enter Valid Details.");
			            alert.getDialogPane().setHeaderText(null);
			            alert.showAndWait().filter(response -> response == ButtonType.OK);
					}
				}
				
			} catch (SQLException e) {
				System.out.println("Error in updateBalance");
			}
		}
		
		
	}
	
}
