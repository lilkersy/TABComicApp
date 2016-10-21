package ifeanyi.com.tab.tabcomicapp.marvelapi.dao;

import java.io.Serializable;

/**
 * Created by brainergysolutions on 10/21/16.
 */

public class Price implements Serializable {

        public String type;
        public double price;

        public Price() {}

        public Price(String type, double price) {
        this.type = type;
        this.price = price;
    }
}
