package Photon.DataBase;
import java.sql.Time;
import java.sql.Date;
import java.util.ArrayList;
//import java.util.Date;

/**
 * Created by Serega on 02.04.2015.
 */
public class User {

    private int id = 0;
    private String name;
    private int placeInTopList = 0;
    private int score;
    private Time bestTime;
    private Date gameDate;
    private boolean isBestScore = true;

    public User(String name, int score, Time time, Date date) {
        this.id = setId();
        this.name = name;
        this.score = score;
        this.placeInTopList = setPlaceInTopList(this.score);
        this.bestTime = time;
        this.gameDate = date;
//        this(0, name, setPlaceInTopList(this.score), score, time, date, isBestScore);
    }
    public User(int id, String name, int placeInTopList, int bestScore, Time bestTime, Date gameDate) {
        this.id = id;
        this.name = name;
        this.placeInTopList = placeInTopList;
        this.score = bestScore;
        this.bestTime = bestTime;
        this.gameDate = gameDate;
    }

    public boolean changeName(String newName) {
        name = newName;
        if(name == newName)
            return true;
        return false;
    }
    public void newScore(int score, Time time) {
        if(isBestScore(score)) {
            this.score = score;
            bestTime = time;
            gameDate = new Date(new java.util.Date().getTime());
            placeInTopList = setPlaceInTopList(score);
        }
    }
    public boolean isBestScore(int score) {
        return score > this.score;
    }

    public int setPlaceInTopList(int score) {
        int newPlace = 1;

        ArrayList<User> usersList = DBWorker.getAll();
        if(usersList.isEmpty())
            return 1;
        for (int i = 0; score <= usersList.get(i).getScore(); i++) {
            if(usersList.get(i).getName().equals(this.name))
                this.isBestScore = false;
            newPlace = i + 2;
            if(i >= usersList.size()-1) {
//                newPlace--;
                break;
            }
        }
//        newPlace++;
        placeInTopList = newPlace;
        DBWorker.updatePlaceInTopList(this.id, this.placeInTopList, this.isBestScoreBool());
//        DBWorker.update(this);
        for (int i = newPlace-1; i <= usersList.size() - 1; i++) {
            User curUser = usersList.get(i);
            if(this.getName().equals(curUser.getName()))
                curUser.setBestScoreBool(false);
            curUser.setPlaceInTopListByNumber(curUser.getPlaceInTopList() + 1);
//            DataBaseWorker.updatePlaceInTopList(curUser.getId(), curUser.getPlaceInTopList(), curUser.isBestScoreBool());
            DBWorker.update(curUser);
        }
        return newPlace;
    }
    public void setPlaceInTopListByNumber(int placeInTopList) {
        this.placeInTopList = placeInTopList;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getPlaceInTopList() {
        return placeInTopList;
    }
    public int getScore() {
        return score;
    }
    public Time getBestTime() {
        return bestTime;
    }
    public Date getGameDate() {
        return gameDate;
    }
    public int isBestScoreInt() {
        if(isBestScore)
            return 1;
        return 0;
    }
    public boolean isBestScoreBool() {
        return isBestScore;
    }

    public void setBestScoreBool(boolean isBestScore) {
        this.isBestScore = isBestScore;
    }
    @Override
    public String toString() {
        String str = "["+id+"."+name+"] ";
        System.out.println(str);
        return str;
    }
    public static int setId() {
        int tmpID = 1;
        for(int i = 1; DBWorker.getUser(i) != null; i++) {
            tmpID = i+1;
        }
//        tmpID++;
        return tmpID;
    }
}
