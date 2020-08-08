package application;


import application.ImageTextCell;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.net.URI;
import java.net.URISyntaxException;
//import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class libraryController {
	
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/elib?zeroDateTimeBehavior=convertToNull";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
	
	private Stage warningStage;
	
    private Product currentProduct;
    private ProductQueries productQueries;
    private List<Product> results;
    private List<Integer> alreadyPurchasedProducts;
    //private List<Integer> allProductIDs;
    private ObservableList<Product> products = FXCollections.observableArrayList();
    
    
    @FXML private AnchorPane mainAnchorPane;
    @FXML private ListView<Product> libraryListView;
    @FXML private ImageView firstImageView;
    @FXML private ImageView secondImageView;
    
    @FXML private ImageView coverImageView;
    @FXML private Label descriptionLabel;
    @FXML private Button cartButton;
    @FXML private Button addToCartButton;
    @FXML private VBox libraryVBox, imagesVBox;
    @FXML private Button readBtn;
   @FXML TextField searchtf;
    @FXML Label myName;
    
    
    
    public void initialize() {
    	myName.setText(Main.currentUserName);
        
        productQueries = new ProductQueries();
        alreadyPurchasedProducts = productQueries.getAlreadyPurchasedProducts(Main.currentUser);
        results = productQueries.getAllProducts();
      

        products.addAll(results);
        
        libraryListView.setItems(products);
        
        libraryListView.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                		if(newValue == null) {
                			newValue = oldValue;
                		}
                        descriptionLabel.setText(newValue.getDescription());
                        coverImageView.setImage(new Image(newValue.getCover()));
                        firstImageView.setImage(new Image(newValue.getImage1()));
                        secondImageView.setImage(new Image(newValue.getImage2()));
                        if(alreadyPurchasedProducts.contains(newValue.getProductID())) {
                            addToCartButton.setText("Already Purchased");
                            addToCartButton.setDisable(true);
                        } else {
                            addToCartButton.setText("Add To Cart");
                            addToCartButton.setDisable(false);
                        }
                        if(newValue.getPrice() == 0) {
                        	readBtn.setDisable(false);
                        }else {
                        	readBtn.setDisable(true);
                        }
                }        
        );
        libraryListView.getSelectionModel().select(0);
        if(libraryListView.getSelectionModel().getSelectedItem().getPrice() == 0) {
        	readBtn.setDisable(false);
        }else {
        	readBtn.setDisable(true);
        }
        libraryListView.setCellFactory((listview) -> new ImageTextCell());
        
        if(Main.count != 0) {
        	cartButton.setText("Cart (" + String.valueOf(Main.count) + ")");
        }else {
        	cartButton.setText("Cart");
        }
                
    }
    
    public Product getSelectedProduct() {
        currentProduct = libraryListView.getSelectionModel().getSelectedItem();
        return currentProduct;
    }
    
    
    public void addToCart(ActionEvent event) throws Exception {
    	ResultSet resultSet=null;
    	List<Integer> results=null;
        Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        Statement statement = connection.createStatement();
        Product product=getSelectedProduct();
        String query= "select * from cart where userid="+Main.currentUser+";";
       resultSet= statement.executeQuery(query);
       results=new ArrayList<Integer>();
        while(resultSet.next()) {
        	results.add(resultSet.getInt("isbn"));
        }
        if(results.contains(product.getProductID())) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(warningStage);
            alert.getDialogPane().setContentText("Each purchased softcopy is automatically added to your My-Books. You need not buy the same softcopy twice.");
            alert.getDialogPane().setHeaderText(null);
            alert.showAndWait().filter(response -> response == ButtonType.OK);
        } else {

            final String INSERT_QUERY = "INSERT INTO cart (userid, isbn) VALUES ('" + Main.currentUser + "','" + product.getProductID() + "')";
            statement.executeUpdate(INSERT_QUERY);
            Main.count += 1;
            
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
	public void read(ActionEvent event) throws IOException, URISyntaxException {
		Product product = getSelectedProduct();
		File file = new File(product.getPath());
		Desktop.getDesktop().open(file);
		
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
	public void search(MouseEvent event) {
		String str = searchtf.getText();
		
		results = productQueries.getProductsByTitle(str);
	      
		products.clear();
        products.addAll(results);
        
        libraryListView.setItems(products);

        libraryListView.getSelectionModel().select(0);
				
	}

}
