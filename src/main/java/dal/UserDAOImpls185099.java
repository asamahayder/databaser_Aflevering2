package dal;

import dal.dto.IUserDTO;
import dal.dto.UserDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpls185099 implements IUserDAO {


    private Connection createConnection() throws SQLException {
        return  DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185099?"
                    + "user=s185099&password=zhKW0aeedrH5Jvd9UDGJp");
    }

    @Override
    public void createUser(IUserDTO user) throws DALException {

        try(Connection connection = createConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO databaser1 (id, username) VALUES (?,?)");
            statement.setInt(1,user.getUserId());
            statement.setString(2,user.getUserName());
            statement.executeUpdate();

            statement = connection.prepareStatement("INSERT INTO databaser2 (id, role) VALUES (?,?)");

            for (int i = 0; i < user.getRoles().size(); i++) {
                statement.setInt(1,user.getUserId());
                statement.setString(2, user.getRoles().get(i));
                statement.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String generateIni(String username){
        String[]names = username.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < names.length; i++) {
            stringBuilder.append(names[i].charAt(0));
        }
        String initials = stringBuilder.toString();
        String uppercaseInitials=initials.toUpperCase();
        return uppercaseInitials;
    }

    @Override
    public IUserDTO getUser(int userId) throws DALException {
        UserDTO user = new UserDTO();
        ArrayList<String> roles = new ArrayList<>();

       try (Connection connection = createConnection()){
           Statement statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery("SELECT databaser1.id, databaser1.username, databaser2.role FROM databaser1, databaser2 WHERE databaser1.id =" + userId + " AND databaser2.id="+userId);
           System.out.println("Got resultset from database:");

           while (resultSet.next()){
               user.setUserId(resultSet.getInt("id"));
               user.setUserName(resultSet.getString("username"));
               user.setIni(generateIni(resultSet.getString("username")));
               roles.add(resultSet.getString("role"));
           }
           user.setRoles(roles);
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
            Statement statement1 = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM databaser1");
            while (resultSet.next()){
                    UserDTO user = new UserDTO();
                    user.setUserId(resultSet.getInt("id"));
                    user.setUserName(resultSet.getString("username"));
                    user.setIni(generateIni(resultSet.getString("username")));
                    ResultSet resultSet1 = statement1.executeQuery("SELECT * FROM databaser2 WHERE id=" + user.getUserId());
                    while (resultSet1.next()){
                        user.addRole(resultSet1.getString("role"));
                    }
                    users.add(user);
                }
            } catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void updateUser(IUserDTO user) throws DALException {
        String update;
        try(Connection connection = createConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM databaser2 WHERE id=?");
            statement.setInt(1,user.getUserId());
            statement.executeUpdate();

            for (int i = 0; i < user.getRoles().size(); i++) {
                statement = connection.prepareStatement("INSERT INTO databaser2 (id, role) VALUES (?,?)");
                statement.setInt(1,user.getUserId());
                statement.setString(2,user.getRoles().get(i));
                statement.executeUpdate();
            }

            statement = connection.prepareStatement("UPDATE databaser1 SET username=? WHERE id=?");
            statement.setInt(2, user.getUserId());
            statement.setString(1,user.getUserName());
            statement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(int userId) throws DALException {
        try (Connection connection = createConnection()){
            PreparedStatement statement = connection.prepareStatement("DELETE FROM databaser2 WHERE id=?");
            statement.setInt(1,userId);
            statement.executeUpdate();

            statement=connection.prepareStatement("DELETE FROM databaser1 WHERE id=?");
            statement.setInt(1,userId);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
