package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.City;
import model.User;

public class SQLiteConnection {
	static String url = 
		"jdbc:sqlite:src/main/resources/cities_db.db";
	
//	Adding User object to the database		
	public static void DB_Add_City (User user) throws SQLException {
//			Make (Get) connection to the Database 
			try (Connection conn = DriverManager.getConnection(url)) {
				boolean isValid = conn.isValid(0);
//				System.out.println("Do we have a valid db connection? = " + isValid);
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
//				        System.out.println("The City "+city.getName()+" is JUST ADDED to the favorite list of the user "+ user.getUsername());
			        }
				}
			}
		}

	public static ArrayList<User> DB_SelectUsersCities () throws SQLException {
//		Make (Get) connection to the Database
		ArrayList<User> usersList = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(url)) {
			boolean isValid = conn.isValid(0);
//			System.out.println("Do we have a valid db connection? = " + isValid);
//		        select user from database to check if it already exist
			PreparedStatement selectStatement = conn.prepareStatement
					("select distinct UserName from Favorite_Cities ");
//		        this will return a ResultSet
			ResultSet rs = selectStatement.executeQuery();

//		       Add data to the ArrayLise
			while (rs.next()) {
				String name = rs.getString("UserName");

				User user = new User(name);
				usersList.add(user);
			}

			for (User user1 : usersList ) {
//				System.out.println("\n"+user1.getUsername());  //Print for testing - delete after debugging

				PreparedStatement selectStatement1 = conn.prepareStatement
						("select * from Favorite_Cities where UserName =  ?");
				selectStatement1.setString(1, user1.getUsername());
				//			        this will return a ResultSet
				ResultSet rs1 = selectStatement1.executeQuery();

				//			       Add data to the ArrayList
				while (rs1.next()) {
					String cityName = rs1.getString("CityName");
					Double lattitude = rs1.getDouble("Lattitude");
					Double longtitude = rs1.getDouble("Longtitude");
					City city = new City(cityName,lattitude, longtitude);
					user1.addCity(city);
//					System.out.print		//Print for testing - delete after debugging
//							(city.getName()+"\t"+ city.getLattitude()+"\t"+ city.getLongtitude()+ "\n");
				}
			}
		}
		catch (SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
		}
//		System.out.println("\nUsers List"); //Print for testing - delete after debugging
//		for (User user2 : usersList) {   //Print for testing - delete after debugging
//			System.out.println("\n"+ user2.getUsername());  //Print for testing - delete after debugging
//			System.out.print("\n"+ user2.getCityList());  //Print for testing - delete after debugging
//		}
		return usersList;
	}


}
