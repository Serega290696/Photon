package Photon.DataBase;

import java.util.ArrayList;

/**
 * Created by Serega on 05.04.2015.
 */
public class ListWorker {


    public static ArrayList<User> getList() {
        ArrayList<User> users = new ArrayList<User>();
//        ArrayList<User> usersData = new ArrayList<User>();
        int begin = 1;
        int range = 1;
        users = DBWorker.getAll();
        for(User user : users) {
            begin++;
            for(String data : user.getData()) {
                System.out.println(data);
            }
            if(begin > range)
                break;
        }
        return users;
    }
}
