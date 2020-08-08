package application;

import java.text.NumberFormat;
import java.util.Locale;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class ImageTextCell extends ListCell<Product> {
    
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
    
    @Override
    protected void updateItem(Product item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
        } else {
            
            HBox hbox = new HBox(10.0); 
            hbox.setAlignment(Pos.CENTER_RIGHT);

            
            ImageView coverImageView = new ImageView(new Image(item.getCover()));
            coverImageView.setPreserveRatio(false);
            coverImageView.setFitHeight(60.0);
            coverImageView.setFitWidth(50.0);

           
            Label title = new Label(item.getTitle());
            title.setFont(Font.font("System", FontWeight.BOLD, 16));
            title.setTextAlignment(TextAlignment.LEFT);
            
            Label author = new Label(item.getAuthor());
            author.setFont(Font.font("System", 14));
            author.setTextAlignment(TextAlignment.LEFT);
            
            Label price = new Label();
            String currencyPrice = currencyFormatter.format(item.getPrice());
            price.setText(currencyPrice);
            price.setFont(Font.font("System", FontWeight.BOLD, 16));
                       
            VBox vb = new VBox(2.0);
            
            Pane pane = new Pane();
            
            vb.getChildren().addAll(title, author);
            
            hbox.getChildren().addAll(coverImageView, vb, pane, price);
            HBox.setHgrow(pane, Priority.ALWAYS);
            setGraphic(hbox);
            setPrefWidth(USE_PREF_SIZE);
        }
    }
}

