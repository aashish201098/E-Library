package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UserRegisterController {
	
	@FXML TextField usernametf;
	@FXML TextField phonetf;
	@FXML TextField emailtf;
	@FXML TextField ccntf;
	@FXML TextField passtf;
	@FXML TextField cpasstf;
	@FXML Label statuslbl;
	@FXML TextField cvvtf;

	public static final Pattern VALIDEMAIL = 
	        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	    public static boolean validate(String emailStr) {
	            Matcher matcher = VALIDEMAIL.matcher(emailStr);
	            return matcher.find();
	    }
	
	public void UserRegister(ActionEvent event) {
		
		if(usernametf.getText().equals("")) {
			statuslbl.setText("Enter Username");
		}else if(phonetf.getText().equals("") || phonetf.getText().length() != 10) {
			statuslbl.setText("Enter valid phone Number");
		}else if(emailtf.getText().equals("") || !validate(emailtf.getText())) {
			statuslbl.setText("Enter Valid Email");
		}else if(ccntf.getText().equals("") || ccntf.getText().length() != 16) {
			statuslbl.setText("Enter valid 16 digits Credit Card Number");
		}else if(passtf.getText().equals("")) {
			statuslbl.setText("Enter Password");
		}else if(cvvtf.getText().equals("")) {
			statuslbl.setText("Enter CVV Number");
		}else if(!cpasstf.getText().equals(passtf.getText())) {
			statuslbl.setText("Password and Confirm Password does not Match.");
		}else {
			String username = usernametf.getText();
			String phone = phonetf.getText();
			String email = emailtf.getText();
			String ccn = ccntf.getText();
			String pass = passtf.getText();
			int cvv = Integer.parseInt(cvvtf.getText());
			
			Statement st = DBConnection.getStatement();
			String query;
			query = "select * from user where email = '"+email+"';";
			ResultSet rs = null;
			
			try {
				rs = st.executeQuery(query);
				if(rs.next()) {
					statuslbl.setText("Email ID already exists.");
					emailtf.setText(null);
				}else {
					query = "insert into user(cvv,name,pass,phone,email,ccn) values("+cvv+",'"+username+"','"+pass+"','"+phone+"','"+email+"','"+ccn+"');";
					try {
						st.executeUpdate(query);
						statuslbl.setText("Successfully Registered");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					usernametf.setText("");
					phonetf.setText("");
					emailtf.setText("");
					ccntf.setText("");
					passtf.setText("");
					cpasstf.setText("");
					cvvtf.setText("");
					//statuslbl.setText("");
				}
			} catch (SQLException e1) {
				System.out.println("error in userregister email validation");
			}
		}
		
	}
	
	public void proceedToLogin(ActionEvent event) throws IOException {
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/application/UserLog.fxml").openStream());
		Scene sc = new Scene(root,1280,720);
		primaryStage.setScene(sc);
		primaryStage.show();
	}
	
	
}
