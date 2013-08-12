package zad.lunch;

import zad.lunch.jaxb.LunchService;
import zad.lunch.jaxb.MenuItem;
import zad.lunch.jaxb.Restaurant;



public class LunchServiceHolder {
    private static LunchService lunchService;

    public static LunchService getLunchService() {
        return lunchService;
    }

    static{
        lunchService = new LunchService();

        Restaurant restaurant1 = new Restaurant();
        restaurant1.setName("Red Hot");
        MenuItem menuItem1 = new MenuItem();
        menuItem1.setName("Chili Dog");
        menuItem1.setPrice(4.49);
        MenuItem menuItem2 = new MenuItem();
        menuItem2.setName("Veggie Dog");
        menuItem2.setPrice(3.99);
        restaurant1.getMenuItem().add(menuItem1);
        restaurant1.getMenuItem().add(menuItem2);

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setName("Neumont Cafe");
        MenuItem menuItem3 = new MenuItem();
        menuItem3.setName("Egg");
        menuItem3.setPrice(1.29);
        MenuItem menuItem4 = new MenuItem();
        menuItem4.setName("Burrito");
        menuItem4.setPrice(1.49);
        restaurant2.getMenuItem().add(menuItem3);
        restaurant2.getMenuItem().add(menuItem4);

        lunchService.getRestaurant().add(restaurant1);
        lunchService.getRestaurant().add(restaurant2);
    }
}
