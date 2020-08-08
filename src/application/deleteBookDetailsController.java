package application;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class deleteBookDetailsController {
	
	
	private Product currentProduct;
    private ProductQueries productQueries;
    private List<Product> results;
    AlertType type = AlertType.INFORMATION;
    Stage stage;
    //private List<Integer> alreadyPurchasedProducts;
    //private List<Integer> allProductIDs;
    private ObservableList<Product> products = FXCollections.observableArrayList();
    //private ObservableList<Product> cartItems = FXCollections.observableArrayList();
    
    @FXML private AnchorPane mainAnchorPane;
    @FXML private ListView<Product> libraryListView;
    @FXML private ImageView firstImageView;
    @FXML private ImageView secondImageView;
    //@FXML private ImageView thirdImageView;
    //@FXML private ImageView fourthImageView;
    @FXML private ImageView coverImageView;
    @FXML private Label descriptionLabel;
    //@FXML private Hyperlink cartButton;
    @FXML private Button deleteButton;
    @FXML private VBox libraryVBox, imagesVBox;
   // @FXML private Hyperlink videoHyperlink;
    
    
    
    public void initialize() {
        // defines the database connection and sets the PreparedStatements
        productQueries = new ProductQueries();
        //alreadyPurchasedProducts = productQueries.getAlreadyPurchasedProducts(userID);
        results = productQueries.getAllProducts();
        //numberOfEntries = results.size();

        products.addAll(results);
        
        libraryListView.setItems(products);
        
        libraryListView.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                        descriptionLabel.setText(newValue.getDescription());
                        /*videoHyperlink.setText(newValue.getVideo());
                        videoHyperlink.setOnAction((ActionEvent e) -> {
                            try {
                                Desktop.getDesktop().browse(new URI(videoHyperlink.getText()));
                            } catch (URISyntaxException | IOException ex) {
                                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });*/
                        //System.out.println(newValue.getCover());
                        coverImageView.setImage(new Image(newValue.getCover()));
                        firstImageView.setImage(new Image(newValue.getImage1()));
                        secondImageView.setImage(new Image(newValue.getImage2()));
                        //thirdImageView.setImage(new Image(newValue.getImage3()));
                        //fourthImageView.setImage(new Image(newValue.getImage4()));
                        /*if(alreadyPurchasedProducts.contains(newValue.getProductID())) {
                            addToCartButton.setText("Already Purchased");
                            addToCartButton.setDisable(true);
                        } else {
                            addToCartButton.setText("Add To Cart");
                            addToCartButton.setDisable(false);
                        };*/
                }        
        );
        libraryListView.getSelectionModel().select(0);
        libraryListView.setCellFactory((listview) -> new ImageTextCell());
                
    }
    
    public Product getSelectedProduct() {
        currentProduct = libraryListView.getSelectionModel().getSelectedItem();
        return currentProduct;
    }

    
    @FXML
    public void delete(ActionEvent event) {
    	currentProduct = getSelectedProduct();
    	libraryListView.getItems().remove(currentProduct);
    	String query = "delete from book where isbn = "+currentProduct.getProductID()+";";
    	Statement st = DBConnection.getStatement();
    	try {
			st.executeUpdate(query);
		    createInfoAlert();
			
		} catch (SQLException e) {
			System.out.println("Error in deleteBooksController");
		}
    	
    	
    }
    
    protected Alert createInfoAlert() {
        Alert alert = new Alert(type, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);
        alert.getDialogPane().setContentText("Book Details Deleted Successfully.");
        alert.getDialogPane().setHeaderText("Information");
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK);
        return alert;
    }
    
    @FXML
    public void logout(ActionEvent event) throws IOException {
    	((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/application/AdminLogin.fxml").openStream());
		Scene sc = new Scene(root,1280,720);
		primaryStage.setScene(sc);
		primaryStage.show();
    }
	
    
    @FXML
    public void addBooks(ActionEvent event) throws IOException {
    	((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/application/addDetails.fxml").openStream());
		Scene sc = new Scene(root,700,720);
		primaryStage.setScene(sc);
		primaryStage.show();
    }
    
    
}


