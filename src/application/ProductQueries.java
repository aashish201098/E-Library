package application;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductQueries {
    private static final String URL = "jdbc:mysql://localhost:3306/elib";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    
    private Connection connection; // manages the connection
    private PreparedStatement selectAllBooks;
    private PreparedStatement selectAlreadyPurchasedProducts;
    
    // Constructor
    public ProductQueries() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            
            // creates the query for all the records in Product table
            selectAllBooks = connection.prepareStatement("SELECT * FROM book");
                        
            // creates the query for all purchased products
            selectAlreadyPurchasedProducts = connection.prepareStatement("SELECT isbn FROM orders WHERE userid = ?");
            
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
    
    public List<Product> getAllProducts() {
        List<Product> results = null;
        ResultSet resultSet = null;
        
        try {
        // executeQuery returns a ResultSet that contains the desired records
        resultSet = selectAllBooks.executeQuery();
        results = new ArrayList<Product>();
        while (resultSet.next()) {
            results.add(new Product(
                    resultSet.getInt("isbn"),
                    resultSet.getString("title"),
                    resultSet.getString("author"),
                    resultSet.getInt("price"),
                    resultSet.getString("cover"),
                    resultSet.getString("description"),
                    resultSet.getString("image1"),
                    resultSet.getString("image2"),
                    resultSet.getString("paths")
            ));
        }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                close();
            }
        }
        
        return results;
    }
    
    public List<Product> getProductsByTitle(String name){
    	List<Product> results = null;
    	ResultSet rs = null;
    	
    	Statement st = DBConnection.getStatement();
    	String query = "select * from book where title LIKE '%"+name+"%';";
    	try {
    		 results = new ArrayList<Product>();
			rs = st.executeQuery(query);
			while(rs.next()) {
				results.add(new Product(
	                    rs.getInt("isbn"),
	                    rs.getString("title"),
	                    rs.getString("author"),
	                    rs.getInt("price"),
	                    rs.getString("cover"),
	                    rs.getString("description"),
	                    rs.getString("image1"),
	                    rs.getString("image2"),
	                    rs.getString("paths")
	            ));
			}
		} catch (SQLException e) {
			System.out.println("error in product title");
		}
    	return results;
    }
    
    public List<Integer> getAlreadyPurchasedProducts(int userid) {
        List<Integer> results = null;
        ResultSet resultSet = null;
        
        try {
            selectAlreadyPurchasedProducts.setInt(1, userid);
            resultSet = selectAlreadyPurchasedProducts.executeQuery();
            results = new ArrayList<Integer>();
            
            while(resultSet.next()) {
                int isbn = resultSet.getInt("isbn");
                results.add(isbn);
            }
            
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
            
        } finally {
            try {
                resultSet.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                close();
            }
        }
        
        return results;
    }
    
    
    // closes the connection with the database
    public void close() {
        try {
            connection.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
