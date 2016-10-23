package ifeanyi.com.tab.tabcomicapp.marvelapi.dao;

import java.util.List;

/**
 * Created by brainergysolutions on 10/21/16.
 */

public class CharacterList {
    public int available;
    public int returned;
    public String collectionURL;
    public List<CharacterSummary> items;

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

    public String getCollectionURL() {
        return collectionURL;
    }

    public void setCollectionURL(String collectionURL) {
        this.collectionURL = collectionURL;
    }

    public List<CharacterSummary> getItems() {
        return items;
    }

    public void setItems(List<CharacterSummary> items) {
        this.items = items;
    }
}
