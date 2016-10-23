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

        public String getType() {
                return type;
        }

        public void setType(String type) {
                this.type = type;
        }

        public double getPrice() {
                return price;
        }

        public void setPrice(double price) {
                this.price = price;
        }
}
