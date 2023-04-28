package utilities.datatypes;

import java.io.Serializable;

public class NullValueWrapper implements Serializable {

    @Override
    public boolean equals(Object obj) {
        return false;
    }
    @Override
    public String toString() {
        return null;
    }
}
