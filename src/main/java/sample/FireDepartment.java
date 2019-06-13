package sample;

public class FireDepartment extends Organization  {

    private static FireDepartment fireDepartment;

    private FireDepartment(){
        super();
    }

    public static FireDepartment getInstance(){
        if (fireDepartment == null) {
            fireDepartment = new FireDepartment();
        }
        return fireDepartment;
    }
}
