package application;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CartController {
	
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/elib?zeroDateTimeBehavior=convertToNull";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    @FXML private TableView<Product> cartTable;
    @FXML private TableColumn<Product, Product> coverCol;
    @FXML private TableColumn<Product, String> titleCol;
    @FXML private TableColumn<Product, String> authorCol;
    @FXML private TableColumn<Product, Double> priceCol;    
    @FXML private TableColumn<Product, Product> removeCol;
    @FXML private Label subtotalLabel;    
    @FXML Label myName;
    double total = 0 ;
    public Button cartButton;
    private ObservableList<Product> cartItems = FXCollections.observableArrayList();
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
	
	public void initialize() throws Exception
	{
		myName.setText(Main.currentUserName);
    	ResultSet resultSet=null;
    	List<Product> results=null;
        Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        Statement statement = connection.createStatement();
        String query= "select * from book inner join cart using(isbn) where userid="+Main.currentUser+";";
       resultSet= statement.executeQuery(query);
       results = new ArrayList<Product>();
       while (resultSet.next()) {
           results.add(new Product(
                   resultSet.getInt("isbn"),
                   resultSet.getString("title"),
                   resultSet.getString("author"),
                   resultSet.getInt("price"),
                   resultSet.getString("cover"),
                   resultSet.getString("description"),
                   //resultSet.getString("video"),
                   resultSet.getString("image1"),
                   resultSet.getString("image2"),
                   resultSet.getString("paths")
                   //resultSet.getString("image3"),
                   //resultSet.getString("image4"),
                   //resultSet.getString("exeFile")
           ));
       }
       cartItems.addAll(results);
       cartTable.setItems(cartItems);
       
		for (Product product : cartTable.getItems()) {
            total = total + product.getPrice();
        }
        String currencyPrice = currencyFormatter.format(total);
        subtotalLabel.setText(currencyPrice);    
        
        coverCol.setCellValueFactory(img -> new ReadOnlyObjectWrapper<>(img.getValue()));
        coverCol.setCellFactory(img -> new TableCell<Product, Product>(){
            private final ImageView coverImageView = new ImageView();
            @Override 
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);
                if(product == null) {
                    setGraphic(null);
                    return;
                }
                coverImageView.setImage(new Image(product.getCover()));
                coverImageView.setFitHeight(120);
                coverImageView.setFitWidth(100);
                setGraphic(coverImageView);
            }
        });
        
        
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setCellFactory(col -> new TableCell<Product, Double>(){
            @Override
            public void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if(empty) {
                    setGraphic(null);
                } else {
                    String currencyPrice = currencyFormatter.format(price);
                    Label priceLabel = new Label(currencyPrice);
                    //priceLabel.alignmentProperty();
                    setGraphic(priceLabel);
                }
            }
        });
        
        
        removeCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        removeCol.setCellFactory(param -> new TableCell<Product,Product>(){
            private final Hyperlink removeFromCart = new Hyperlink("");
            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);
                if(product == null) {
                    setGraphic(null);
                    return;
                }
                HBox removeHBox = new HBox();
                removeHBox.setAlignment(Pos.CENTER);
                removeHBox.getChildren().addAll(removeFromCart);
                setGraphic(removeHBox);
                Image deleteIcon = new Image(getClass().getResourceAsStream("images/ic_delete_black_18dp_1x.png"));
                //System.out.println(total);
                removeFromCart.setGraphic(new ImageView(deleteIcon));
                removeFromCart.setStyle("-fx-text-fill: black;");
                removeFromCart.setOnAction(e -> {
                    getTableView().getItems().remove(product);
                    String query1="delete from cart where userid="+Main.currentUser+" and isbn="+product.getProductID()+";";
                    try {
						statement.executeUpdate(query1);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
                    total = total - product.getPrice();
                    String currencyPrice = currencyFormatter.format(total);
                    subtotalLabel.setText(currencyPrice);
                    Main.count --;
                    if(Main.count != 0) {
                    	cartButton.setText("Cart (" + String.valueOf(Main.count) + ")");
                    }else {
                    	cartButton.setText("Cart");
                    }
                });
            }
        });
        
        if(Main.count != 0) {
        	cartButton.setText("Cart (" + String.valueOf(Main.count) + ")");
        }else {
        	cartButton.setText("Cart");
        }
        
	}
	
	public void checkOut(ActionEvent event) throws IOException {
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root = loader.load(getClass().getResource("/application/checkout.fxml").openStream());
		Scene sc = new Scene(root,1400,850);
		sc.getStylesheets().add(getClass().getResource("application1.css").toExternalForm());
		primaryStage.setScene(sc);
		primaryStage.show();
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
	public void continueShopping(ActionEvent event) throws IOException {
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root = loader.load(getClass().getResource("/application/library.fxml").openStream());
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
