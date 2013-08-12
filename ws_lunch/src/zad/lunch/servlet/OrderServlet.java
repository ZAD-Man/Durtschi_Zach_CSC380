package zad.lunch.servlet;

import zad.lunch.jaxb.LunchService;
import zad.lunch.jaxb.MenuItem;
import zad.lunch.jaxb.Restaurant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        File file = new File("LunchOrders.txt");

        if (!file.exists()){
            file.createNewFile();
        }

        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file, true));
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(LunchService.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            LunchService order = (LunchService)unmarshaller.unmarshal(request.getInputStream());

            Restaurant restaurant = order.getRestaurant().get(0);
            MenuItem menuItem = restaurant.getMenuItem().get(0);

            fileWriter.append(String.format("Order: %s - %s\r\n", restaurant.getName(), menuItem.getName()));
            fileWriter.flush();
            fileWriter.close();

        } catch (Exception e) {
            System.out.println("There was an error in the order: ");
            e.printStackTrace();
        }
    }

}
