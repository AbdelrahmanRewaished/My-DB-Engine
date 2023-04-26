package engine.exceptions.creation_exceptions;

import engine.exceptions.DBAppException;

public class ClusteringKeyIsNotReferencedInTable extends DBAppException {
    public ClusteringKeyIsNotReferencedInTable(String clusteringKey, String table) {
        super(String.format("The clustering key '%s' does not exist in the '%s'", clusteringKey, table));
    }
}
