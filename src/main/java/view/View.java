package view;

import database.SQLiteConnection;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import model.City;
import model.User;
import model.WeatherDataCity;
import utils.JsonMapping;
import utils.WeatherAPI;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class View {

    String RESET = "\u001B[0m";
    String RED = "\u001B[31m";
    String GREEN = "\u001B[32m";
    String YELLOW = "\u001B[33m";

    private User activeUser;

    public View() {
    }

    public View(User activeUser) {
        this.activeUser = activeUser;
    }

    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    public void printWelcomeMenu() {
        System.out.println(GREEN +  "Welcome to Weather Info" +
                "\n\tPlease choose one of the following options (enter respective numbers) :" +
                "\n\t-1- Existing User" +
                "\n\t-2- New User" +
                "\n\t-3- Exit" + RESET);
    }

    public User chooseExistingUserMenu(Scanner scanner) {
        List<User> existingUsers = new ArrayList<>();
        //TODO Database get Users
        try {
            existingUsers = SQLiteConnection.DB_SelectUsersCities();
        }
        catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }

        StringBuilder output = new StringBuilder();
        output.append(GREEN + "\n\tPlease choose one of the following users (enter username) :" + RESET);
        for (User existingUser : existingUsers) {
            output.append(YELLOW +"\n\t" + existingUser.getUsername() + RESET);
        }
        System.out.println(output);

        while (true) {
            String inputUserName = scanner.nextLine();
            for (User existingUser : existingUsers) {
                if (existingUser.getUsername().equals(inputUserName)) {
                    return existingUser;
                }
            }

            System.out.println(RED + "\n\tUsername is incorrect or user doesn`t exist:" + RESET);
        }
    }

    public User createNewUser(Scanner scanner) throws IOException {
        System.out.println(GREEN + "Please enter UserName: " + RESET);
        String userName = scanner.nextLine();
        User newUser = new User(userName);

        Set<City> cityList = this.addCityList(scanner);

        for (City city : cityList) {
            newUser.addCity(city);
        }

        try {
            SQLiteConnection.DB_Add_City(newUser);
            System.out.println(GREEN + "\n\tUser " +newUser.getUsername()
                                + " successfully added to DB" + RESET);
        }

        catch (SQLException e) {
            System.out.println(RED + "\n\t" + "SQL Error" + RESET);
        }

        return newUser;

    }

    private Set<City> addCityList(Scanner scanner) throws IOException {
        Set<City> cityList = new HashSet<>();
        boolean running = true;
        while (running) {
            System.out.println(GREEN + "Please enter name of the city to add (enter -exit- to stop): " + RESET);
            String answer = scanner.nextLine().toLowerCase();
            switch (answer) {
                case "exit":
                    running = false;
                    break;
                default:
                    try{
                        City newCity = JsonMapping.getCity(WeatherAPI.requestCity(answer));
                        cityList.add(newCity);
                        System.out.println(GREEN + "City " +newCity.getName() + " added successfully" + RESET);
                    }
                    catch (Exception e) {
                        System.out.println(RED + "Invalid city entered, please try again" + RESET);
                    }
                    break;
            }
        }
        return cityList;
    }

    public void renderViewForUser() throws IOException {
        User activeUser = this.getActiveUser();

        Set<City> cityList = activeUser.getCityList();

        AsciiTable asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow("Name", "Yesterday", "3 Day Average");
        asciiTable.addRule();
        for (City city : cityList) {
            WeatherDataCity weatherDataCity = new WeatherDataCity(city);
            asciiTable.addRow(city.getName(),
                              weatherDataCity.getTemperatureYesterday(),
                              weatherDataCity.getTemperatureLastNDays());
            asciiTable.addRule();
        }
        asciiTable.setTextAlignment(TextAlignment.CENTER);
        String render = asciiTable.render();
        System.out.println(render);


    }

}
