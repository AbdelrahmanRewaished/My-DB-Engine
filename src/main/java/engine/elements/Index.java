package engine.elements;

import me.ryandw11.octree.Octree;
import me.ryandw11.octree.OutOfBoundsException;

import java.io.Serializable;

public class Index<T> extends Octree<T> implements Serializable{
    public Index() {
    }

    public Index(int x, int y, int z, T object) {
        super(x, y, z, object);
    }

    public Index(int x1, int y1, int z1, int x2, int y2, int z2) throws OutOfBoundsException {
        super(x1, y1, z1, x2, y2, z2);
    }


}
