package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UserMain1Controller implements Initializable{
	@FXML private MediaView mv1;
	@FXML private MediaView mv2;
	private MediaPlayer mp1,mp2;
	private Media me1,me2;
	@FXML Button cartButton;
	@FXML Label myName;
	Stage helpStage;
	@FXML HBox text;
	
	public void library(ActionEvent event) throws IOException {
		((Node)event.getSource()).getScene().getWindow().hide();
		mp1.stop();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root = loader.load(getClass().getResource("/application/library.fxml").openStream());
		Scene sc = new Scene(root,1400,850);
		sc.getStylesheets().add(getClass().getResource("application1.css").toExternalForm());
		primaryStage.setScene(sc);
		primaryStage.show();
	}

	private List<String > list = new ArrayList<String>();
	private List<String> list1 = new ArrayList<String>();
 	@FXML ImageView imgView;
	@FXML Label lbl;
	int j=0;
	Image images[];
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		myName.setText(Main.currentUserName);
		Statement st = DBConnection.getStatement();
		String query = "select * from book order by isbn desc";
		ResultSet rs = null;
		try {
			rs = st.executeQuery(query);
			while(rs.next()) {
				String title = rs.getString("title");
				String cover = rs.getString("cover");
				
				list.add(cover);
				list1.add(title);
				
			}
			
			
			images = new Image[list.size()];
			for(int i=0;i<list.size();i++) {
				images[i] = new Image(list.get(i));
			}
			
			imgView.setImage(images[j]);
			lbl.setText(list1.get(j));
			
			Marquee marquee = new Marquee("e-library is an e-store where a user can buy and read books digitally.");
	        marquee.setColor("#049276"); 
	        marquee.setStyle("-fx-font: bold 20 arial;"); 
	        marquee.setBoundsFrom(text); 
	        marquee.moveDownBy(15);
	        marquee.setScrollDuration(20); 
	        
	        text.getChildren().add(marquee); 
	        marquee.run();
			
			
		}catch(SQLException ex) {
			System.out.println("Error in UserMain1 Controlller");
		}
		
		if(Main.count != 0) {
        	cartButton.setText("Cart (" + String.valueOf(Main.count) + ")");
        }else {
        	cartButton.setText("Cart");
        }
		
		String path1=new File("src/application/videos/mv1.mp4").getAbsolutePath();
		String path2=new File("src/application/videos/mv2.mp4").getAbsolutePath();
		me1=new Media(new File(path1).toURI().toString());
		mp1=new MediaPlayer(me1);
		mv1.setMediaPlayer(mp1);
		me2=new Media(new File(path2).toURI().toString());
		mp2=new MediaPlayer(me2);
		mv2.setMediaPlayer(mp2);
		mp1.setAutoPlay(true);
		
		
	}
	public void play(ActionEvent event)
	{
		mp1.play();
	}
	public void pause(ActionEvent event)
	{
		mp1.pause();
	}
	public void stop(ActionEvent event)
	{
		mp1.seek(mp1.getStartTime());
		mp1.stop();
	}
	public void plays(ActionEvent event)
	{
		mp2.play();
	}
	public void pauses(ActionEvent event)
	{
		mp2.pause();
	}
	public void stops(ActionEvent event)
	{
		mp2.seek(mp2.getStartTime());
		mp2.stop();
	}


	
	@FXML 
	public void left(ActionEvent event) {
		j = j - 1;
        if (j == 0 || j > list.size() + 1 || j == -1) {
            j = list.size() - 1;
        }
        imgView.setImage(images[j]);
        lbl.setText(list1.get(j));
	}
	
	@FXML
	public void right(ActionEvent event) {
		j = j + 1;
        if (j == list.size()) {
            j = 0;
        }
        imgView.setImage(images[j]);
        lbl.setText(list1.get(j));
	}
	
	@FXML
	public void my_books(ActionEvent event) throws IOException {
		((Node)event.getSource()).getScene().getWindow().hide();
		mp1.stop();
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
		mp1.stop();
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
		mp1.stop();
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
        mp1.stop();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root = loader.load(getClass().getResource("/application/UserMain1.fxml").openStream());
		Scene sc = new Scene(root,1400,850);
		sc.getStylesheets().add(getClass().getResource("application1.css").toExternalForm());
		primaryStage.setScene(sc);
		primaryStage.show();
	}
	
	@FXML
	public void logout(ActionEvent event) throws IOException {
		((Node)event.getSource()).getScene().getWindow().hide();
		mp1.stop();
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
        alert.initOwner(helpStage);
        alert.getDialogPane().setContentText("e-library is an e-store where a user can buy books digitally and then read them from the My_Books section. The user can browse through the available books by clicking on each item of the list. Then he can add the selected book to the Shopping Cart and proceed to Payment. Each book can be purchased only once due to its linking with the user's account.");
        alert.getDialogPane().setHeaderText(null);
        alert.showAndWait().filter(response -> response == ButtonType.CLOSE); 
	}

}
