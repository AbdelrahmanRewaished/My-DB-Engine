package engine.elements.index;

public enum OctreeLocations {

    bottomSouthWest(0),
    bottomSouthEast(1),
    bottomNorthWest(2),
    bottomNorthEast(3),
    topSouthWest(4),
    topSouthEast(5),
    topNorthWest(6),
    topNorthEast(7);

    private final int position;
    OctreeLocations(int position) {
        this.position = position;
    }
    public int getPosition() {
        return position;
    }
}
