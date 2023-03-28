package engine.elements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Table implements Serializable {
    private final String folderLocation;
    private List<PageInfo> pagesInfo;
    private final String name;

    public Table(String name, String folderLocation) {
        this.name = name;
        this.folderLocation = folderLocation;
        pagesInfo = new ArrayList<>();
    }

    public String getFolderLocation() {
        return folderLocation;
    }

    public List<PageInfo> getPagesInfo() {
        return pagesInfo;
    }

    public String getName() {
        return name;
    }
    public void addPageInfo(PageInfo pageInfo) {
        pagesInfo.add(pageInfo);
    }
    public PageInfo removePageInfo(int index) {
        return pagesInfo.remove(index);
    }
}
