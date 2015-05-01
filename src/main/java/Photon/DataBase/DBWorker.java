package Photon.DataBase;

/**
 * Created by Serega on 02.04.2015.
// */
//public class DBWorker {
//
//
//
//    private static final String URL = "jdbc:mysql://localhost:3306/photon";
//    private static final String USER = "root";
//    private static final String PASS = "root";
////    private static final String INSERT_NEW = "INSERT INTO top_list(id, name, placeInTopList, score, bestTime, gameDate, isBestScore) VALUES (?, ?, ?, ?, ?, ?, ?)";
//    private static final String INSERT_NEW = "INSERT INTO top_list VALUES (?,?,?,?,?,?,?)";
//    private static final String UPDATE_PLACE_IN_TOP_LIST = "UPDATE top_list SET placeInTopList=?, isBestScore=? WHERE id=?";
//    private static final String UPDATE = "UPDATE top_list SET name=?, placeInTopList=?, score=?, bestTime=?, gameDate=?, isBestScore=? WHERE id=?";
//    private static final String GET_ALL = "SELECT * FROM top_list ORDER BY placeInTopList";
//    private static final String GET_ONE_ID = "SELECT * FROM top_list WHERE id=?";
//    private static final String GET_ONE_NAME = "SELECT * FROM top_list WHERE name=?";
//    private static final String DELETE = "DELETE FROM top_list WHERE id=?";
//
//    private static Connection connection = null;
//    private static PreparedStatement preparedStatement = null;
//    public DBWorker() {
//        try {
//            Driver driver = new FabricMySQLDriver();
//            DriverManager.registerDriver(driver);
//
//            connection = DriverManager.getConnection(URL, USER, PASS);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public static void insert(User user) {
//        insert(user.getName(), user.getPlaceInTopList(), user.getScore(), user.getBestTime(), user.getGameDate(), user.isBestScoreInt());
////        update(user);
//    }
//    public static void insert(String name, int placeInTopList, int bestScore, Time bestTime, Date gameDate, int isBestScore) {
//        try {
//            int id = User.setId();
//            preparedStatement = connection.prepareStatement(INSERT_NEW);
//
//            preparedStatement.setInt(1, id);
//            preparedStatement.setString(2, name);
//            preparedStatement.setInt(3, placeInTopList);
//            preparedStatement.setInt(4, bestScore);
//            preparedStatement.setTime(5, bestTime);
//            preparedStatement.setDate(6, gameDate);
//            preparedStatement.setInt(7, isBestScore);
//            preparedStatement.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }
//    public static void update(User user) {
//        try {
//            preparedStatement = connection.prepareStatement(UPDATE);
//            preparedStatement.setString(1, user.getName());
//            preparedStatement.setInt(2, user.getPlaceInTopList());
//            preparedStatement.setInt(3, user.getScore());
//            preparedStatement.setTime(4, user.getBestTime());
//            preparedStatement.setDate(5, user.getGameDate());
//            preparedStatement.setInt(6, user.isBestScoreInt());
//            preparedStatement.setInt(7, user.getId());
//
//            preparedStatement.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    public static void updatePlaceInTopList(int id, int newPlaceInTopList, boolean isBestScore) {
//        try {
//            preparedStatement = connection.prepareStatement(UPDATE_PLACE_IN_TOP_LIST);
//            preparedStatement.setInt(1, newPlaceInTopList);
//            if(isBestScore)
//                preparedStatement.setInt(2, 1);
//            else
//                preparedStatement.setInt(2, 0);
//            preparedStatement.setInt(3, id);
//
//            preparedStatement.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    public static User getUser(int id) {
//        User user = null;
//        try {
//            preparedStatement = connection.prepareStatement(GET_ONE_ID);
//            preparedStatement.setInt(1, id);
////            preparedStatement.execute();
//            ResultSet res = preparedStatement.executeQuery();
//            while(res.next())
//                user = new User(res.getInt(1), res.getString(2), res.getInt(3), res.getInt(4), res.getTime(5), res.getDate(6));
//
//            preparedStatement.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return user;
//    }
//    public static ArrayList<User> getUsers(String name) {
//        ArrayList<User> users = new ArrayList<User>();
//        User user = null;
//        try {
//            preparedStatement = connection.prepareStatement(GET_ONE_NAME);
//            preparedStatement.setString(1, name);
////            preparedStatement.execute();
//            ResultSet res = preparedStatement.executeQuery();
////            user = new User(res.getInt(1), res.getString(2), res.getInt(3), res.getInt(4), res.getTime(5), res.getDate(6));
//
//            while(res.next()) {
//                int id = res.getInt(1);
//                int placeInTopList = res.getInt(3);
//                int bestScore = res.getInt(4);
//                Time bestTime = res.getTime(5);
//                Date gameDate = res.getDate(6);
//                user = new User(id, name, placeInTopList, bestScore, bestTime, gameDate);
//                users.add( user );
//            }
//            preparedStatement.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return users;
//    }
//    public static ArrayList<User> getAll() {
//        ArrayList<User> users = new ArrayList<User>();
//        try {
//            preparedStatement = connection.prepareStatement(GET_ALL);
//            ResultSet res = preparedStatement.executeQuery();
//
//            User curUser;
//
//            while(res.next()) {
//                int id = res.getInt(1);
//                String name = res.getString(2);
//                int placeInTopList = res.getInt(3);
//                int bestScore = res.getInt(4);
//                Time bestTime = res.getTime(5);
//                Date gameDate = res.getDate(6);
//                curUser = new User(id, name, placeInTopList, bestScore, bestTime, gameDate);
//                users.add( curUser );
//            }
//
//            preparedStatement.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return users;
//    }
//
//
//    public static void delete() {
//
//    }
//
//
//
//}
