package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.City;
import model.User;

public class SQLiteConnection {
	static String url = 
		"jdbc:sqlite:/Users/apple/eclipse-workspace/weather_api/src/main/java/database/cities_db.db";
	
//	Adding User object to the database		
	public static void DB_Add_City (User user) throws SQLException {
//			Make (Get) connection to the Database 
			try (Connection conn = DriverManager.getConnection(url)) {
				boolean isValid = conn.isValid(0);
				System.out.println("Do we have a valid db connection? = " + isValid);
//				Trying each city in the user's CityList (List of the favorite cities)		
				for (City city : user.getCityList() ) {
//			        select city-user from database to check if it already exist
			        PreparedStatement selectStatement = conn.prepareStatement
			        	("select * from Favorite_Cities where UserName =  ? AND CityName = ?");
			        selectStatement.setString(1, user.getUsername());
			        selectStatement.setString(2, city.getName());
//			        this will return a ResultSet
			        ResultSet rs = selectStatement.executeQuery();
			        if (rs.next()) { 
			        	System.out.println("The City "+city.getName()+
			        		" is alredy in the favorite list of the user "+ user.getUsername());
			        } else {
//				        Add rows to the Database
				        PreparedStatement statement = conn.prepareStatement
				        	("insert into Favorite_Cities (UserName, CityName, Lattitude, Longtitude) values (?,?,?,?)");
				       
				        statement.setString(1, user.getUsername());
				        statement.setString(2, city.getName());
				        statement.setDouble(3, city.getLattitude());
				        statement.setDouble(4, city.getLongtitude());
				        statement.executeUpdate();
				        System.out.println("The City "+city.getName()+" is JUST ADDED to the favorite list of the user "+ user.getUsername());
			        }
				}
			}
		}

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		User user1 = new User("Roni");
		City city1=new City("New York", 1.4, 2.3);
		City city2=new City("Rio", 15.0, 7.6);
		City city3=new City("Berlin", 10.33, 17.2);
		user1.addCity(city1);
		user1.addCity(city2);
		user1.addCity(city3);
		DB_Add_City(user1);

	}

}
