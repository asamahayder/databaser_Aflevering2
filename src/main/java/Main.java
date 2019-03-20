import dal.IUserDAO;
import dal.UserDAOImpls185099;
import dal.dto.IUserDTO;
import dal.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDAOImpls185099 userDAOImpls185099 = new UserDAOImpls185099();
        //create user test
        /*IUserDTO user = new UserDTO();
        String username = "hejsa asana";
        ArrayList roles = new ArrayList();
        roles.add("Gavegivende mand");
        roles.add("hiiiii");
        roles.add("dsgdfg");


        user.setRoles(roles);
        user.setUserId(5);
        user.setUserName(username);

        try {
            userDAOImpls185099.createUser(user);
        } catch (IUserDAO.DALException e) {
            e.printStackTrace();
        }*/

        //getUser test
        /*
        try {
            IUserDTO user = userDAOImpls185099.getUser(1);
            System.out.println(user.toString());
        } catch (IUserDAO.DALException e) {
            e.printStackTrace();
        }*/

        //updateUser test

        /*IUserDTO user = null;
        try {
            user = userDAOImpls185099.getUser(1);
        } catch (IUserDAO.DALException e) {
            e.printStackTrace();
        }
        String username = "Ny Bruger";
        ArrayList roles = new ArrayList();
        roles.add("Ny");
        roles.add("Noob");

        user.setRoles(roles);
        user.setUserName(username);
        user.setUserId(1);
        try {
            userDAOImpls185099.updateUser(user);
        } catch (IUserDAO.DALException e) {
            e.printStackTrace();
        }*/

        //get userList test
        /*try {
            List<IUserDTO> users = userDAOImpls185099.getUserList();
            for (int i = 0; i < users.size(); i++) {
                System.out.println(users.get(i).toString());
            }
        } catch (IUserDAO.DALException e) {
            e.printStackTrace();
        }*/

        //delete TEst
        try {
            userDAOImpls185099.deleteUser(1);
        } catch (IUserDAO.DALException e) {
            e.printStackTrace();
        }
    }
}
