package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MyBooksController implements Initializable {
	
	@FXML TilePane libraryTilePane;
	@FXML Button cartButton;
	@FXML Label myName;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		myName.setText(Main.currentUserName);
		Statement st = DBConnection.getStatement();
		String query = "select title,paths,cover from book INNER JOIN orders on orders.isbn = book.isbn where userid = "+Main.currentUser+";";
		
		ResultSet rs = null;
		
		try {
			rs = st.executeQuery(query);
			if(!rs.isBeforeFirst()) {
				System.out.println(rs);
				Label lbl = new Label("Only the books that you have purchased will appear in this section. You can find the images in Library.");
				lbl.setPadding(new Insets(10, 10, 10, 10));
				libraryTilePane.getChildren().add(lbl);
			}
			
			while(rs.next()) {
				String title = rs.getString("title");
				String path = rs.getString("paths");
				String cover = rs.getString("cover");
				
				Image coverImg = new Image(cover);
				ImageView coverImgView = new ImageView(coverImg);
				coverImgView.setFitHeight(120);
				coverImgView.setFitWidth(100);
				
				
				final Tooltip tooltip = new Tooltip("Download " + title);
                Hyperlink downloadBook = new Hyperlink(title);
                downloadBook.setContentDisplay(ContentDisplay.TOP);
                downloadBook.setGraphic(coverImgView);
                downloadBook.getStyleClass().add("library-tiles");
                Tooltip.install(downloadBook, tooltip);
                downloadBook.setCursor(Cursor.DEFAULT);
				downloadBook.setPrefWidth(140);
				downloadBook.setPrefHeight(140);
				
                libraryTilePane.getChildren().add(downloadBook);
                
                downloadBook.setOnAction((ActionEvent e) -> {
                    try {
//                        Desktop.getDesktop().browse(new URI(path));
                    	Desktop.getDesktop().open(new File(path));
                    } catch (IOException ex) {
                        System.out.println("Error in MyBooksController");
                    }
                });
				
				
			}
			
			
		} catch (SQLException e) {
			System.out.println("Error in MyBooksController");
		}
		
		if(Main.count != 0) {
        	cartButton.setText("Cart (" + String.valueOf(Main.count) + ")");
        }else {
        	cartButton.setText("Cart");
        }
		
	}
	
	public void cart(ActionEvent event) throws IOException {
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root = loader.load(getClass().getResource("/application/Cart.fxml").openStream());
		Scene sc = new Scene(root,1400,850);
		sc.getStylesheets().add(getClass().getResource("application1.css").toExternalForm());
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
	
	
}
