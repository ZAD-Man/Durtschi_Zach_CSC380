package zad.lunch.servlet;

import zad.lunch.LunchServiceHolder;
import zad.lunch.jaxb.LunchService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.*;

@WebServlet("/LunchServlet")
public class LunchServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setContentType("text/xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(LunchService.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            LunchService lunchService = LunchServiceHolder.getLunchService();
            marshaller.marshal(lunchService, response.getWriter());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
