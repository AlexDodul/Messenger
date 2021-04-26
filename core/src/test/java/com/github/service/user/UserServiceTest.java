package com.github.service.user;

import com.github.dto.UserRegDto;
import com.github.entity.User;
import com.github.exceptions.UserInsertException;
import com.github.exceptions.UserUpdateException;
import com.github.exceptions.WrongLoginException;
import com.github.repository.user.IUserRepository;
import com.github.repository.user.UserRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserServiceTest {

    private static final HikariConfig config = new HikariConfig("src/test/resources/hikari.properties");

    private static final IUserRepository userRepository = new UserRepository(new HikariDataSource(config));

    private static final IUserService userService = new UserService(userRepository);

    @Before
    public void setUp(){
        userService.deleteAll();
        for(UserRegDto userRegDto : UserServiceTestMock.userRegCollection){
            userService.insert(userRegDto);
        }
    }

    @After
    public void tearDown(){
        userService.deleteAll();
    }

    @Test
    public void findAll() {
        Collection<UserRegDto> exp = new ArrayList<>(UserServiceTestMock.userRegCollection);
        Collection<User> result = userService.findAll();
        Collection<UserRegDto> act = new ArrayList<>();
        for(User user : result){
            act.add(new UserRegDto(user));
        }
        Assert.assertEquals(exp, act);
    }

    @Test
    public void findAllEmpty(){
        userService.deleteAll();
        Collection<UserRegDto> exp = new ArrayList<>();
        Collection<User> result = userService.findAll();
        Collection<UserRegDto> act = new ArrayList<>();
        for(User user : result){
            act.add(new UserRegDto(user));
        }
        Assert.assertEquals(exp, act);
    }

    @Test
    public void findAuthFirst() {
        UserRegDto exp = UserServiceTestMock.firstUserReg;
        UserRegDto act = new UserRegDto(userService.findAuth(UserServiceTestMock.firstUserAuth));
        Assert.assertEquals(exp, act);
    }

    @Test
    public void findAuthSecond() {
        UserRegDto exp = UserServiceTestMock.secondUserReg;
        UserRegDto act = new UserRegDto(userService.findAuth(UserServiceTestMock.secondUserAuth));
        Assert.assertEquals(exp, act);
    }

    @Test(expected = WrongLoginException.class)
    public void findAuthNotExisting() {
        userService.findAuth(UserServiceTestMock.notExistingAuth);
    }

    @Test
    public void findRegFirst() {
        UserRegDto exp = UserServiceTestMock.firstUserReg;
        UserRegDto act = new UserRegDto(userService.findReg(UserServiceTestMock.firstUserReg));
        Assert.assertEquals(exp, act);
    }

    @Test
    public void findRegSecond() {
        UserRegDto exp = UserServiceTestMock.secondUserReg;
        UserRegDto act = new UserRegDto(userService.findReg(UserServiceTestMock.secondUserReg));
        Assert.assertEquals(exp, act);
    }

    @Test(expected = UserInsertException.class)
    public void insertSame(){
        UserRegDto dtoToInsert = UserServiceTestMock.firstUserReg;
        userService.insert(dtoToInsert);
    }

    @Test(expected = UserInsertException.class)
    public void insertNull(){
        UserRegDto dtoToInsert = UserServiceTestMock.nullUserReg;
        userService.insert(dtoToInsert);
    }

    @Test
    public void update() {
        List<User> initial = new ArrayList<>(userService.findAll());
        List<User> usersToUpdate = new ArrayList<>(initial.size());
        List<UserRegDto> updated = new ArrayList<>(UserServiceTestMock.updatedUserRegCollection);
        Collection<User> exp = new ArrayList<>();
        for (int i = 0; i < initial.size(); i++) {
            User user = new User(
                    initial.get(i).getId(),
                    updated.get(i).getFirstName(),
                    updated.get(i).getLastName(),
                    updated.get(i).getLogin(),
                    updated.get(i).getPassword(),
                    updated.get(i).getEmail(),
                    updated.get(i).getPhone()
            );
            usersToUpdate.add(user);
            exp.add(user);
        }
        for (User user : usersToUpdate) {
            userService.update(user);
        }
        Collection<User> act = userService.findAll();
        Assert.assertEquals(exp, act);
    }

    @Test(expected = UserUpdateException.class)
    public void updateNotExisting(){
        userService.update(UserServiceTestMock.notExistingUser);
    }

    @Test
    public void deleteFirst() {
        List<User> users = new ArrayList<>(userService.findAll());
        userService.delete(users.get(0));
        Collection<User> exp = new ArrayList<>();
        for(int i = 0; i < users.size(); i++){
            if(i != 0) {
                exp.add(users.get(i));
            }
        }
        Collection<User> act = userService.findAll();
        Assert.assertArrayEquals(exp.toArray(), act.toArray());
    }

    @Test
    public void deleteSecond() {
        List<User> users = new ArrayList<>(userService.findAll());
        userService.delete(users.get(1));
        Collection<User> exp = new ArrayList<>();
        for(int i = 0; i < users.size(); i++){
            if(i != 1) {
                exp.add(users.get(i));
            }
        }
        Collection<User> act = userService.findAll();
        Assert.assertEquals(exp, act);
    }


}
