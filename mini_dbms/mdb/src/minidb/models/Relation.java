package minidb.models;

public class Relation {

    String details;

    public Relation(String details) {
        this.details = details;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    private String relationName;

    @Override
    public String toString() {
        return relationName.trim()+"=>"+details;
    }

    public String data() {
        return details;
    }
}
