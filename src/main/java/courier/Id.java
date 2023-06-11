package courier;

public class Id {

    private String id;

    public Id(int id) {
        this.id = Integer.toString(id);
    }

    public Id() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
