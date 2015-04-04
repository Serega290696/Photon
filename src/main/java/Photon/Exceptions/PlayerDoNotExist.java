package Photon.Exceptions;

/**
 * Created by Serega on 14.03.2015.
 */
public class PlayerDoNotExist extends Exception {
    // классический конструктор с сообщением о характере ошибки
    public PlayerDoNotExist(String msg) {super(msg);}
    // пустой конструктор
    public PlayerDoNotExist() {}
}
