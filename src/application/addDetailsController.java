package application;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class addDetailsController {
	@FXML private TextField titletf;
	@FXML private TextField authortf;
	@FXML private TextField pricetf;
	@FXML private TextField choosefiletf;
	@FXML private TextField img1tf;
	@FXML private TextField img2tf;
	@FXML private TextField pathtf;
	@FXML private Label statuslb;
	@FXML private TextArea desctf;
	
	public void addDetails(ActionEvent event) {
		if(titletf.getText().equals("")) {
			statuslb.setText("Please enter the title of book");
		}
		else if(authortf.getText().equals("")) {
			statuslb.setText("Please enter the name of authors of book");
		}
		else if(pricetf.getText().equals("")) {
			statuslb.setText("Please enter the price of book");
		}
		else if(choosefiletf.getText().equals("")) {
			statuslb.setText("Please choose a cover page of book");
		}
		else {
			String title = titletf.getText();
			String aname = authortf.getText();
			int price = Integer.parseInt(pricetf.getText());
			String file = choosefiletf.getText();
			String desc = desctf.getText();
			String img1 = img1tf.getText();
			String img2 = img2tf.getText();
			String path = pathtf.getText();
			
			Statement st = DBConnection.getStatement();
			String query = "insert into book(title,author,price,cover,Description,image1,image2,paths) values('"+ title + "','"+ aname + "',"+ price + ", '" + file + "','"+desc+"','"+img1+"','"+img2+"','"+path+"');";
			try {
				st.executeUpdate(query);
				statuslb.setText("Book details successfully added.");
				
				titletf.setText(null);
				authortf.setText(null);
				pricetf.setText(null);
				choosefiletf.setText(null);
				desctf.setText(null);
				img1tf.setText(null);
				img2tf.setText(null);
				pathtf.setText(null);
				
			} catch (SQLException e) {
				System.out.println("Error in sql syntax");
			}
			
		}
	}
	
	
	public void chooseFile(MouseEvent event) {
		FileChooser file = new FileChooser();
		File selectedFile = file.showOpenDialog(null);
		if(selectedFile != null) {
			String path = selectedFile.getAbsolutePath();
			StringBuffer sb = new StringBuffer("file:///");
			for(int i=0;i<path.length();i++) {
				if(path.charAt(i) == '\\') {
					sb.append("/");
				}else {
					sb.append(path.charAt(i));
				}
			}
			choosefiletf.setText(sb.toString());
		}else {
			System.out.println("File is not Valid");
		}
	}
	
	public void chooseImg1(MouseEvent event) {
		FileChooser file = new FileChooser();
		File selectedFile = file.showOpenDialog(null);
		if(selectedFile != null) {
			String path = selectedFile.getAbsolutePath();
			StringBuffer sb = new StringBuffer("file:///");
			for(int i=0;i<path.length();i++) {
				if(path.charAt(i) == '\\') {
					sb.append("/");
				}else {
					sb.append(path.charAt(i));
				}
			}
			img1tf.setText(sb.toString());
		}else {
			System.out.println("File is not Valid");
		}
	}
	
	public void chooseImg2(MouseEvent event) {
		FileChooser file = new FileChooser();
		File selectedFile = file.showOpenDialog(null);
		if(selectedFile != null) {
			String path = selectedFile.getAbsolutePath();
			StringBuffer sb = new StringBuffer("file:///");
			for(int i=0;i<path.length();i++) {
				if(path.charAt(i) == '\\') {
					sb.append("/");
				}else {
					sb.append(path.charAt(i));
				}
			}
			img2tf.setText(sb.toString());
		}else {
			System.out.println("File is not Valid");
		}
	}
	
	public void choosePath(MouseEvent event) {
		FileChooser file = new FileChooser();
		File selectedFile = file.showOpenDialog(null);
		if(selectedFile != null) {
			String path = selectedFile.getAbsolutePath();
			StringBuffer sb = new StringBuffer("");
			for(int i=0;i<path.length();i++) {
				if(path.charAt(i) == '\\') {
					sb.append("/");
				}else {
					sb.append(path.charAt(i));
				}
			}
			pathtf.setText(sb.toString());
		}else {
			System.out.println("File is not Valid");
		}
	}
	
    @FXML
    public void goToHomePage(ActionEvent event) throws IOException {
    	((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/application/deleteBookDetails.fxml").openStream());
		Scene sc = new Scene(root,1400,850);
		primaryStage.setScene(sc);
		primaryStage.show();
    }
	
}
