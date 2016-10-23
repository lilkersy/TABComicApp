package ifeanyi.com.tab.tabcomicapp.marvelapi.dao;

import java.util.List;

/**
 * Created by brainergysolutions on 10/21/16.
 */

public class ItemList {
    public int available;
    public int returned;
    public String collectionURI;
    public List<Item> items;

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getReturned() {
        return returned;
    }

    public void setReturned(int returned) {
        this.returned = returned;
    }

    public String getCollectionURI() {
        return collectionURI;
    }

    public void setCollectionURI(String collectionURI) {
        this.collectionURI = collectionURI;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
