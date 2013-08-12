package zad.lunch.client;

import zad.lunch.jaxb.LunchService;
import zad.lunch.jaxb.MenuItem;
import zad.lunch.jaxb.Restaurant;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class LunchClient {
    public static final String SERVLET_ADDRESS = "http://localhost:8080/LunchServlet";
    public static final String ORDER_SERVLET_ADDRESS = "http://localhost:8080/OrderServlet";

    public static void main(String[] args) {
        try {
            HttpURLConnection getConnection = (HttpURLConnection) (new URL(SERVLET_ADDRESS)).openConnection();
            getConnection.setDoOutput(true);
            getConnection.setDoInput(true);
            getConnection.setInstanceFollowRedirects(false);
            getConnection.setRequestMethod("GET");

            File data = new File("LunchServiceData.xml");

            if (!data.exists()) {
                data.createNewFile();
            }

            BufferedReader dataReader = new BufferedReader(new InputStreamReader(getConnection.getInputStream()));
            BufferedWriter dataWriter = new BufferedWriter(new FileWriter(data.getAbsoluteFile()));
            Scanner userInput = new Scanner(System.in);

            String readData;
            readData = dataReader.readLine();
            while (readData != null) {
                dataWriter.write(readData);
                readData = dataReader.readLine();
            }

            dataReader.close();
            dataWriter.flush();
            dataWriter.close();
            getConnection.disconnect();

            JAXBContext jaxbContext = JAXBContext.newInstance(LunchService.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            LunchService lunchService = (LunchService) unmarshaller.unmarshal(data);
            List<Restaurant> restaurants = lunchService.getRestaurant();

            System.out.println("Please input the restaurant number, then the menu item number:");
            for (int i = 0; i < restaurants.size(); i++) {
                Restaurant restaurant = restaurants.get(i);
                System.out.printf("%d) %s%n", i + 1, restaurant.getName());

                List<MenuItem> menuItems = restaurant.getMenuItem();
                for (int j = 0; j < menuItems.size(); j++) {
                    MenuItem menuItem = menuItems.get(j);
                    System.out.printf("\t%d: %s - $%s%n", j + 1, menuItem.getName(), menuItem.getPrice());
                }
            }

            int restaurantID = Integer.parseInt(userInput.nextLine()) - 1;
            int menuItemID = Integer.parseInt(userInput.nextLine()) - 1;

            HttpURLConnection postConnection = (HttpURLConnection) (new URL(ORDER_SERVLET_ADDRESS)).openConnection();

            postConnection.setRequestMethod("POST");
            postConnection.setDoOutput(true);
            postConnection.setDoInput(true);
            postConnection.setRequestProperty("Content-Type", "text/xml");

            LunchService order = new LunchService();

            Restaurant orderRestaurant = new Restaurant();
            orderRestaurant.setName(lunchService.getRestaurant().get(restaurantID).getName());

            MenuItem orderMenuItem = new MenuItem();
            orderMenuItem.setName(lunchService.getRestaurant().get(restaurantID).getMenuItem().get(menuItemID).getName());
            orderMenuItem.setPrice(lunchService.getRestaurant().get(restaurantID).getMenuItem().get(menuItemID).getPrice());

            orderRestaurant.getMenuItem().add(orderMenuItem);
            order.getRestaurant().add(orderRestaurant);

            Marshaller marshaller = jaxbContext.createMarshaller();

            OutputStreamWriter postWriter = new OutputStreamWriter(postConnection.getOutputStream());

            marshaller.marshal(order, postWriter);

            postWriter.flush();
            postWriter.close();

            String responseMessage = postConnection.getResponseMessage();

            if (responseMessage != null){
                System.out.println(responseMessage);
            }

            postConnection.disconnect();

            System.out.println("Thank you for your order!");

        } catch (Exception e) {
            System.out.println("There was an error, sorry");
            e.printStackTrace();
        }
    }


}
