package dal;

import dal.dto.IUserDTO;
import dal.dto.UserDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpls185099 implements IUserDAO {


    private Connection createConnection() throws SQLException {
        return  DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185099?"
                    + "user=chbu&password=zhKW0aeedrH5Jvd9UDGJp");
    }

    @Override
    public void createUser(IUserDTO user) throws DALException {
        //TODO Implement this - Should insert a user into the db using data from UserDTO object.
        StringBuilder rolesStringBuilder = new StringBuilder();
        List<String> roleListFromUser = user.getRoles();
        for (int i = 0; i < roleListFromUser.size() ; i++) {
            rolesStringBuilder.append(roleListFromUser.get(i));
            if (i != roleListFromUser.size() - 1) {
                rolesStringBuilder.append(", ");
            }
        }
        String roles = rolesStringBuilder.toString();
        try(Connection connection = createConnection()) {
            Statement statement = connection.createStatement();
            String create = "INSERT INTO databaser1 (userID, username, ini, roles) VALUES (" + user.getUserId() + ",'" + user.getUserName() + "','" + user.getIni() + "','" + roles + "')";
            statement.executeUpdate(create);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public IUserDTO getUser(int userId) throws DALException {
        UserDTO user = new UserDTO();
        ArrayList<String> roles = new ArrayList<>();

       try (Connection connection = createConnection()){
           Statement statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery("SELECT * FROM databaser1 WHERE userID =" + userId);
           System.out.println("Got resultset from database:");

           while (resultSet.next()){
               System.out.println(resultSet.getString(1) + ": " + resultSet.getString(2)+ " " + resultSet.getString(3) + " " + resultSet.getString(4));
               user.setUserId(resultSet.getInt(1));
               user.setUserName(resultSet.getString(2));
               user.setIni(resultSet.getString(3));
               String roleString = (resultSet.getString(4));
               String[] roleArray = roleString.split(", ");
               for (int i = 0; i <roleArray.length ; i++) {
                   roles.add(roleArray[i]);
               }
               user.setRoles(roles);
           }
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
        return user;

    }



    @Override
    public List<IUserDTO> getUserList() throws DALException {
        ArrayList<IUserDTO> users = new ArrayList<>();
        try (Connection connection = createConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM databaser1");
            while (resultSet.next()){
                UserDTO user = new UserDTO();
                user.setUserId(resultSet.getInt(1));
                user.setUserName(resultSet.getString(2));
                user.setIni(resultSet.getString(3));
                String roleString = resultSet.getString(4);
                String[] roleArray = roleString.split(", ");
                ArrayList<String> roles = new ArrayList<>();
                for (int i = 0; i <roleArray.length ; i++) {
                    roles.add(roleArray[i]);
                }
                user.setRoles(roles);
                users.add(user);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void updateUser(IUserDTO user) throws DALException {
        StringBuilder rolesStringBuilder = new StringBuilder();
        List<String> roleListFromUser = user.getRoles();
        for (int i = 0; i < roleListFromUser.size() ; i++) {
            rolesStringBuilder.append(roleListFromUser.get(i));
            if (i != roleListFromUser.size() - 1) {
                rolesStringBuilder.append(", ");
            }
        }
        String roles = rolesStringBuilder.toString();

        try(Connection connection = createConnection()) {
            Statement statement = connection.createStatement();
            String update = "UPDATE databaser1 SET userID ="+user.getUserId()+", username='"+user.getUserName()+"', ini ='"+user.getIni()+"', roles='"+roles+"'  WHERE userID = "+user.getUserId();
            statement.executeUpdate(update);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(int userId) throws DALException {
        try (Connection connection = createConnection()){
            Statement statement = connection.createStatement();
            String delete = "DELETE FROM databaser1 WHERE userID ="+userId;
            statement.executeUpdate(delete);
        }catch (SQLException e){
            e.printStackTrace();
        }

        //TODO Implement this - Should delete a user with the given userid from the db.
    }
}
