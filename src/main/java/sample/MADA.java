package sample;

public class MADA extends Organization  {

    private static MADA mada;

    private MADA(){
        super();
    }

    public static MADA getInstance(){
        if (mada == null) {
            mada = new MADA();
        }
        return mada;
    }
}
