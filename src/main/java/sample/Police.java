package sample;

public class Police extends Organization {

    private static Police police;

    private Police(){
        super();
    }

    public static Police getInstance(){
        if (police == null) {
            police = new Police();
        }
        return police;
    }
}
