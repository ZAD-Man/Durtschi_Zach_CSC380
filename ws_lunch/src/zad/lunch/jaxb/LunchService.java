
package zad.lunch.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <pre>
 * &lt;complexType name="lunchService">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="restaurant" type="{}restaurant" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlRootElement
@XmlType(name = "lunchService", propOrder = {
    "restaurant"
})
public class LunchService {

    public List<Restaurant> restaurant;

    public List<Restaurant> getRestaurant() {
        if (restaurant == null) {
            restaurant = new ArrayList<Restaurant>();
        }
        return this.restaurant;
    }

}
