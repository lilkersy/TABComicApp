package ifeanyi.com.tab.tabcomicapp.marvelapi.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by brainergysolutions on 10/21/16.
 */

public class Comic implements Serializable {

    public int id;
    public int digitalId;
    public String title;
    public double issueNumber;
    public String variantDescription;
    public String description;
    public Date modified;
    public String isbn;
    public String upc;
    public String diamondCode;
    public String ean;
    public String issn;
    public String format;
    public int pageCount;
    public List<TextObject> textObjects;
    public String resourceURI;
    public List<Url> urls;
    public Item series;
    public List<Item> variants;
    public List<Item> collection;
    public List<Item> collectedIssues;
    public List<IndustryDate> dates;
    public List<Price> prices;
    public ImageInfo thumbnail;
    public List<ImageInfo> images;
    public CreatorList creators;
    public CharacterList characters;
    public ItemList stories;
    public ItemList events;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDigitalId() {
        return digitalId;
    }

    public void setDigitalId(int digitalId) {
        this.digitalId = digitalId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(double issueNumber) {
        this.issueNumber = issueNumber;
    }

    public String getVariantDescription() {
        return variantDescription;
    }

    public void setVariantDescription(String variantDescription) {
        this.variantDescription = variantDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getDiamondCode() {
        return diamondCode;
    }

    public void setDiamondCode(String diamondCode) {
        this.diamondCode = diamondCode;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<TextObject> getTextObjects() {
        return textObjects;
    }

    public void setTextObjects(List<TextObject> textObjects) {
        this.textObjects = textObjects;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public List<Url> getUrls() {
        return urls;
    }

    public void setUrls(List<Url> urls) {
        this.urls = urls;
    }

    public Item getSeries() {
        return series;
    }

    public void setSeries(Item series) {
        this.series = series;
    }

    public List<Item> getVariants() {
        return variants;
    }

    public void setVariants(List<Item> variants) {
        this.variants = variants;
    }

    public List<Item> getCollection() {
        return collection;
    }

    public void setCollection(List<Item> collection) {
        this.collection = collection;
    }

    public List<Item> getCollectedIssues() {
        return collectedIssues;
    }

    public void setCollectedIssues(List<Item> collectedIssues) {
        this.collectedIssues = collectedIssues;
    }

    public List<IndustryDate> getDates() {
        return dates;
    }

    public void setDates(List<IndustryDate> dates) {
        this.dates = dates;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public ImageInfo getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ImageInfo thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<ImageInfo> getImages() {
        return images;
    }

    public void setImages(List<ImageInfo> images) {
        this.images = images;
    }

    public CreatorList getCreators() {
        return creators;
    }

    public void setCreators(CreatorList creators) {
        this.creators = creators;
    }

    public CharacterList getCharacters() {
        return characters;
    }

    public void setCharacters(CharacterList characters) {
        this.characters = characters;
    }

    public ItemList getStories() {
        return stories;
    }

    public void setStories(ItemList stories) {
        this.stories = stories;
    }

    public ItemList getEvents() {
        return events;
    }

    public void setEvents(ItemList events) {
        this.events = events;
    }
}
