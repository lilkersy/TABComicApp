package ifeanyi.com.tab.tabcomicapp.sorter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ifeanyi.com.tab.tabcomicapp.ComicData;
import ifeanyi.com.tab.tabcomicapp.marvelapi.dao.CreatorList;
import ifeanyi.com.tab.tabcomicapp.marvelapi.dao.Price;

/**
 * Created by brainergysolutions on 10/21/16.
 */

public class ComicSorter {
    protected List<ComicData> ComicDataList  = new ArrayList<>();
    protected int maxPrice        = 0;
    protected int solutionPrice   = 0;
    protected int profit           = 0;
    protected boolean calculated   = false;

    public ComicSorter() {}

    public ComicSorter(int _maxPrice) {
        setMaxPrice(_maxPrice);
    }

    public ComicSorter(List<ComicData> _ComicDataList) {
        setComicDataList(_ComicDataList);
    }

    public ComicSorter(List<ComicData> _ComicDataList, int _maxPrice) {
        setComicDataList(_ComicDataList);
        setMaxPrice(_maxPrice);
    }

    
    public List<ComicData> calcSolution() {
        int n = ComicDataList.size();

        setInitialStateForCalculation();
        if (n > 0  &&  maxPrice > 0) {
            List< List<Integer> > c = new ArrayList<>();
            List<Integer> curr = new ArrayList<>();

            c.add(curr);
            for (int j = 0; j <= maxPrice; j++)
                curr.add(0);
            for (int i = 1; i <= n; i++) {
                List<Integer> prev = curr;
                c.add(curr = new ArrayList<>());
                for (int j = 0; j <= maxPrice; j++) {
                    if (j > 0) {
                        int wH = ComicDataList.get(i-1).getPrice();
                        curr.add(
                                (wH > j)
                                        ?
                                        prev.get(j)
                                        :
                                        Math.max(
                                                prev.get(j),
                                                ComicDataList.get(i-1).getPageCount() + prev.get(j-wH)
                                        )
                        );
                    } else {
                        curr.add(0);
                    }
                } // for (j...)
            } // for (i...)
            profit = curr.get(maxPrice);

            for (int i = n, j = maxPrice; i > 0  &&  j >= 0; i--) {
                int tempI   = c.get(i).get(j);
                int tempI_1 = c.get(i-1).get(j);
                if (
                        (i == 0  &&  tempI > 0)
                                ||
                                (i > 0  &&  tempI != tempI_1)
                        )
                {
                    ComicData iH = ComicDataList.get(i-1);
                    int  wH = iH.getPrice();
                    iH.setInContainer(1);
                    j -= wH;
                    solutionPrice += wH;
                }
            } // for()
            calculated = true;
        } // if()
        return ComicDataList;
    }

    // add an ComicData to the ComicData list
    public void add(String name, int price, int pageCount,
                    String issn, String thumbnail, String image,
                    String description, List<Price> prices, CreatorList authors) {
        if (name.equals(""))
            name = "" + (ComicDataList.size() + 1);
        ComicDataList.add(new ComicData(name, price, pageCount, issn, thumbnail, image, description, prices, authors));
        setInitialStateForCalculation();
    }

    public void add(ComicData album){
        if (album.getTitle().equals(""))
            album.setTitle("No Title");
        add(album.getTitle(), album.getPrice(), album.getPageCount(), album.getIssn(),
                album.getThumbnail(), album.getImage(), album.getDescription(), album.getPrices(), album.getAuthors());
    }

    // add an ComicData to the ComicData list
    public void add(int price, int pageCount,
                    String issn, String thumbnail, String image,
                    String description, List<Price> prices, CreatorList authors) {
        add("", price, pageCount, issn, thumbnail, image, description, prices, authors); // the name will be "ComicDataList.size() + 1"!
    }

    // remove an ComicData from the ComicData list
    public void remove(String name) {
        for (Iterator<ComicData> it = ComicDataList.iterator(); it.hasNext(); ) {
            if (name.equals(it.next().getTitle())) {
                it.remove();
            }
        }
        setInitialStateForCalculation();
    }

    // remove all ComicDatas from the ComicData list
    public void removeAllComicDatas() {
        ComicDataList.clear();
        setInitialStateForCalculation();
    }

    public int getProfit() {
        if (!calculated)
            calcSolution();
        return profit;
    }

    public int getSolutionPrice() {return solutionPrice;}
    public boolean isCalculated() {return calculated;}
    public int getMaxPrice() {return maxPrice;}

    public void setMaxPrice(int _maxPrice) {
        maxPrice = Math.max(_maxPrice, 0);
    }

    public void setComicDataList(List<ComicData> _ComicDataList) {
        if (_ComicDataList != null) {
            ComicDataList = _ComicDataList;
            for (ComicData ComicData : _ComicDataList) {
                ComicData.checkMembers();
            }
        }
    }

    // set the member with name "inContainer" by all ComicDatas:
    private void setInContainerByAll(int inContainer) {
        for (ComicData ComicData : ComicDataList)
            if (inContainer > 0)
                ComicData.setInContainer(1);
            else
                ComicData.setInContainer(0);
    }

    // set the data members of class in the state of starting the calculation:
    protected void setInitialStateForCalculation() {
        setInContainerByAll(0);
        calculated     = false;
        profit         = 0;
        solutionPrice = 0;
    }

}
