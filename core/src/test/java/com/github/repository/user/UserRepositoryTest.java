package com.github.repository.user;

import com.github.dto.UserRegDto;
import com.github.entity.User;
import com.github.exceptions.UserInsertException;
import com.github.exceptions.UserUpdateException;
import com.github.exceptions.WrongLoginException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserRepositoryTest {

    private static final HikariConfig config = new HikariConfig("src/test/resources/hikari.properties");

    private static final IUserRepository userRepository = new UserRepository(
            new HikariDataSource(config));

    @Before
    public void setUp(){
        userRepository.deleteAll("public.user");
        for(UserRegDto userRegDto : UserRepositoryTestMock.userRegCollection){
            userRepository.insert(userRegDto, "public.user");
        }
    }

    @After
    public void tearDown(){
        userRepository.deleteAll("public.user");
    }

    @Test
    public void findAll() {
        Collection<UserRegDto> exp = new ArrayList<>(UserRepositoryTestMock.userRegCollection);
        Collection<User> result = userRepository.findAll("public.user");
        Collection<UserRegDto> act = new ArrayList<>();
        for(User user : result){
            act.add(new UserRegDto(user));
        }
        Assert.assertEquals(exp, act);
    }

    @Test
    public void findAllEmpty(){
        userRepository.deleteAll("public.user");
        Collection<UserRegDto> exp = new ArrayList<>();
        Collection<User> result = userRepository.findAll("public.user");
        Collection<UserRegDto> act = new ArrayList<>();
        for(User user : result){
            act.add(new UserRegDto(user));
        }
        Assert.assertEquals(exp, act);
    }

    @Test
    public void findAuthFirst() {
        UserRegDto exp = UserRepositoryTestMock.firstUserReg;
        UserRegDto act = new UserRegDto(userRepository.findAuth(UserRepositoryTestMock.firstUserAuth, "public.user"));
        Assert.assertEquals(exp, act);
    }

    @Test
    public void findAuthSecond() {
        UserRegDto exp = UserRepositoryTestMock.secondUserReg;
        UserRegDto act = new UserRegDto(userRepository.findAuth(UserRepositoryTestMock.secondUserAuth, "public.user"));
        Assert.assertEquals(exp, act);
    }

    @Test(expected = WrongLoginException.class)
    public void findAuthNotExisting() {
        userRepository.findAuth(UserRepositoryTestMock.notExistingAuth, "public.user");
    }

    @Test
    public void findRegFirst() {
        UserRegDto exp = UserRepositoryTestMock.firstUserReg;
        UserRegDto act = new UserRegDto(userRepository.findReg(UserRepositoryTestMock.firstUserReg, "public.user"));
        Assert.assertEquals(exp, act);
    }

    @Test
    public void findRegSecond() {
        UserRegDto exp = UserRepositoryTestMock.secondUserReg;
        UserRegDto act = new UserRegDto(userRepository.findReg(UserRepositoryTestMock.secondUserReg, "public.user"));
        Assert.assertEquals(exp, act);
    }

    @Test(expected = UserInsertException.class)
    public void insertSame(){
        UserRegDto dtoToInsert = UserRepositoryTestMock.firstUserReg;
        userRepository.insert(dtoToInsert, "public.user");
    }

    @Test(expected = UserInsertException.class)
    public void insertNull(){
        UserRegDto dtoToInsert = UserRepositoryTestMock.nullUserReg;
        userRepository.insert(dtoToInsert, "public.user");
    }

    @Test
    public void update() {
        List<User> initial = new ArrayList<>(userRepository.findAll("public.user"));
        List<User> usersToUpdate = new ArrayList<>(initial.size());
        List<UserRegDto> updated = new ArrayList<>(UserRepositoryTestMock.updatedUserRegCollection);
        Collection<User> exp = new ArrayList<>();
        for (int i = 0; i < initial.size(); i++) {
            User user = new User(
                    initial.get(i).getId(),
                    updated.get(i).getFirstName(),
                    updated.get(i).getLastName(),
                    updated.get(i).getNickname(),
                    updated.get(i).getLogin(),
                    updated.get(i).getPassword(),
                    updated.get(i).getEmail(),
                    updated.get(i).getPhone()
            );
            usersToUpdate.add(user);
            exp.add(user);
        }
        for (User user : usersToUpdate) {
            userRepository.update(user, "public.user");
        }
        Collection<User> act = userRepository.findAll("public.user");
        Assert.assertEquals(exp, act);
    }

    @Test(expected = UserUpdateException.class)
    public void updateNotExisting(){
        userRepository.update(UserRepositoryTestMock.notExistingUser, "public.user");
    }

    @Test
    public void deleteFirst() {
        List<User> users = new ArrayList<>(userRepository.findAll("public.user"));
        userRepository.delete(users.get(0), "public.user");
        Collection<User> exp = new ArrayList<>();
        for(int i = 0; i < users.size(); i++){
            if(i != 0) {
                exp.add(users.get(i));
            }
        }
        Collection<User> act = userRepository.findAll("public.user");
        Assert.assertArrayEquals(exp.toArray(), act.toArray());
    }

    @Test
    public void deleteSecond() {
        List<User> users = new ArrayList<>(userRepository.findAll("public.user"));
        userRepository.delete(users.get(1), "public.user");
        Collection<User> exp = new ArrayList<>();
        for(int i = 0; i < users.size(); i++){
            if(i != 1) {
                exp.add(users.get(i));
            }
        }
        Collection<User> act = userRepository.findAll("public.user");
        Assert.assertEquals(exp, act);
    }

}