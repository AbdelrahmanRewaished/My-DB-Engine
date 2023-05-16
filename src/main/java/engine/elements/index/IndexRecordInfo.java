package engine.elements.index;

import java.io.Serializable;
import java.util.Objects;

public class IndexRecordInfo implements Serializable {
    private Comparable clusteringKeyValue;
    private int pageNumber;

    public IndexRecordInfo(Comparable clusteringKeyValue, int pageNumber) {
        this.clusteringKeyValue = clusteringKeyValue;
        this.pageNumber = pageNumber;
    }

    public Comparable getClusteringKeyValue() {
        return clusteringKeyValue;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IndexRecordInfo that)) return false;
        return getPageNumber() == that.getPageNumber() && getClusteringKeyValue().equals(that.getClusteringKeyValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClusteringKeyValue(), getPageNumber());
    }

    @Override
    public String toString() {
        return "IndexRecordInfo{" +
                "clusteringKeyValue=" + clusteringKeyValue +
                ", pageNumber=" + pageNumber +
                '}';
    }
}
