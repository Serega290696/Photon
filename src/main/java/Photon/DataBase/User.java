package Photon.DataBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
//import java.util.Date;

/**
 * Created by Serega on 02.04.2015.
 */
@Entity
@Table(name = "user")
public class User {

    @Id
    private int id = 0;
    @Column(name = "name")
    private String name;
    @Column(name = "placeInTopList")
    private int placeInTopList = 0;
    @Column(name = "score")
    private int score;
    @Column(name = "bestTime")
    private Time bestTime;
    @Column(name = "gameDate")
    private Date gameDate;
    @Column(name = "isBestScore")
    private boolean isBestScore = true;

    public static String defaultName = "Player";

    public User(String name, int score, Time time, Date date) {
        this.id = setId();
        this.name = name;
        this.score = score;
        this.placeInTopList = setPlaceInTopList(this.score);
        this.bestTime = time;
        this.gameDate = date;
        setName();
//        this(0, name, setPlaceInTopList(this.score), score, time, date, isBestScore);
    }
    public User(int id, String name, int placeInTopList, int bestScore, Time bestTime, Date gameDate) {
        this.id = id;
        this.name = name;
        this.placeInTopList = placeInTopList;
        this.score = bestScore;
        this.bestTime = bestTime;
        this.gameDate = gameDate;
        setName();
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

        ArrayList<User> usersList = ListWorker.DBWorker.getAll();
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
        ListWorker.DBWorker.updatePlaceInTopList(this.id, this.placeInTopList, this.isBestScoreBool());
//        DBWorker.update(this);
        for (int i = newPlace-1; i <= usersList.size() - 1; i++) {
            User curUser = usersList.get(i);
            if(this.getName().equals(curUser.getName()))
                curUser.setBestScoreBool(false);
            curUser.setPlaceInTopListByNumber(curUser.getPlaceInTopList() + 1);
//            DataBaseWorker.updatePlaceInTopList(curUser.getId(), curUser.getPlaceInTopList(), curUser.isBestScoreBool());
            ListWorker.DBWorker.update(curUser);
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
    public ArrayList<String> getData() {
        String prefics = "\t* ";
        ArrayList<String> dataList = new ArrayList<String>();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        dataList.add(prefics + "Id: " + Integer.toString(id));
        dataList.add(prefics + "Name: " + name);
        dataList.add(prefics + "Place: " + Integer.toString(placeInTopList));
        dataList.add(prefics + "Score: " + Integer.toString(score));
        dataList.add(prefics + "Time: " + bestTime.toString());
        dataList.add(prefics + "Date: " + gameDate.toString());
        if(isBestScore)
            dataList.add(prefics + "THE RECORD!");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//
//        dataList.add(Integer.toString(id));
//        dataList.add(name);
//        dataList.add(Integer.toString(placeInTopList));
//        dataList.add(Integer.toString(score));
//        dataList.add(bestTime.toString());
//        dataList.add(gameDate.toString());
//        dataList.add(Boolean.toString(isBestScore));

        return dataList;
    }
    public static int setId() {
        int tmpID = 1;
        for(int i = 1; ListWorker.DBWorker.getUser(i) != null; i++) {
            tmpID = i+1;
        }
//        tmpID++;
        return tmpID;
    }
    public void setName() {
        if(name.equals(defaultName)) {
            name += id;
        }
    }
}
