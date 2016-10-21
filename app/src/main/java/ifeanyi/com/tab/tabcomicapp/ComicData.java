package ifeanyi.com.tab.tabcomicapp;

import java.io.Serializable;
import java.util.List;

import ifeanyi.com.tab.tabcomicapp.marvelapi.dao.CreatorList;
import ifeanyi.com.tab.tabcomicapp.marvelapi.dao.Price;

/**
 * Created by brainergysolutions on 10/21/16.
 */

public class ComicData implements Serializable {

    protected String title = "";
    private String issn;
    private String thumbnail;
    private String image;
    private String description;
    private List<Price> prices;
    private CreatorList authors;
    protected int price = 0;
    protected int pageCount = 0;
    protected int bounding = 1; // the maximal limit of ComicData's pieces
    protected int inKnapsack = 0; // the pieces of ComicData in solution

    public ComicData() {
    }

    public ComicData(ComicData ComicData) {
        setTitle(ComicData.title);
        setprice(ComicData.price);
        setpageCount(ComicData.pageCount);
        setBounding(ComicData.bounding);
    }

    public ComicData(int _price, int _pageCount) {
        setprice(_price);
        setpageCount(_pageCount);
    }

    public ComicData(int _price, int _pageCount, int _bounding) {
        setprice(_price);
        setpageCount(_pageCount);
        setBounding(_bounding);
    }

    public ComicData(String _title, int _price, int _pageCount, String _issn, String _thumbnail, String _image,
                     String _description, List<Price> _prices, CreatorList _authors) {
        setTitle(_title);
        setprice(_price);
        setpageCount(_pageCount);
        this.issn = _issn;
        this.thumbnail = _thumbnail;
        this.image = _image;
        this.description = _description;
        this.prices = _prices;
        this.authors = _authors;
    }

    public ComicData(String _title, int _price, int _pageCount, int _bounding) {
        setTitle(_title);
        setprice(_price);
        setpageCount(_pageCount);
        setBounding(_bounding);
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public CreatorList getAuthors() {
        return authors;
    }

    public void setAuthors(CreatorList authors) {
        this.authors = authors;
    }

    public void setTitle(String _title) {
        title = _title;
    }

    public void setprice(int _price) {
        price = Math.max(_price, 0);
    }

    public void setpageCount(int _pageCount) {
        pageCount = Math.max(_pageCount, 0);
    }

    public void setInKnapsack(int _inKnapsack) {
        inKnapsack = Math.min(getBounding(), Math.max(_inKnapsack, 0));
    }

    public void setBounding(int _bounding) {
        bounding = Math.max(_bounding, 0);
        if (bounding == 0)
            inKnapsack = 0;
    }

    public void checkMembers() {
        setprice(price);
        setpageCount(pageCount);
        setBounding(bounding);
        setInKnapsack(inKnapsack);
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getInKnapsack() {
        return inKnapsack;
    }

    public int getBounding() {
        return bounding;
    }

}