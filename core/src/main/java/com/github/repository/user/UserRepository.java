package com.github.repository.user;

import com.github.dto.UserAuthDto;
import com.github.dto.UserRegDto;
import com.github.entity.User;
import com.github.exceptions.*;
import com.github.micro.orm.CustomJdbcTemplate;
import com.github.micro.orm.exceptions.CustomSQLException;
import com.github.micro.orm.exceptions.ElementNotFoundException;
import com.github.micro.orm.exceptions.InsertErrorException;
import com.github.micro.orm.exceptions.UpdateErrorException;

import javax.sql.DataSource;
import java.util.Collection;

public class UserRepository implements IUserRepository{

    private CustomJdbcTemplate<User> customJdbcTemplate;

    private final DataSource dataSource;

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        customJdbcTemplate = new CustomJdbcTemplate<>(this.dataSource);
    }

    @Override
    public Collection<User> findAll() {
        String sql = "select * from " + UserTable.tableName;
        try {
            return customJdbcTemplate.findAll(
                    sql,
                    UserRowMapper.getRowMapper()
            );
        } catch (CustomSQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public User findAuth(UserAuthDto userAuthDto) {
        String sql = "select * from "
                + UserTable.tableName + " where "
                + UserTable.login + " = ?";
        try {
            User result = customJdbcTemplate.findBy(
                    sql,
                    UserRowMapper.getRowMapper(),
                    userAuthDto.getLogin()
            );
            if(userAuthDto.getPassword().equals(result.getPassword())){
                return result;
            } else {
                throw new WrongPasswordException("Wrong password for user " + userAuthDto.getLogin());
            }
        } catch (CustomSQLException e) {
            throw new DatabaseException(e.getMessage());
        } catch (ElementNotFoundException e){
            throw new WrongLoginException("Wrong login: " + userAuthDto.getLogin());
        }
    }

    @Override
    public User findReg(UserRegDto userRegDto) {
        String sql = "select * from "
                + UserTable.tableName + " where "
                + UserTable.login + " = ? and "
                + UserTable.email + " = ? and "
                + UserTable.phone + " = ?";
        try {
            return customJdbcTemplate.findBy(
                    sql,
                    UserRowMapper.getRowMapper(),
                    userRegDto.getLogin(),
                    userRegDto.getEmail(),
                    userRegDto.getPhone()
            );
        } catch (CustomSQLException e) {
            throw new DatabaseException(e.getMessage());
        } catch (ElementNotFoundException e){
            throw new WrongLoginException(e.getMessage());
        }
    }

    @Override
    public User insert(UserRegDto userRegDto) {
        String sql = "insert into "
                + UserTable.tableName + " ("
                + UserTable.firstName + ", "
                + UserTable.lastName + ", "
                + UserTable.login + ", "
                + UserTable.email + ", "
                + UserTable.phone + ", "
                + UserTable.password
                + ") values (?, ?, ?, ?, ?, ?)";
        try {
            return customJdbcTemplate.insert(sql,
                    UserRowMapper.getRowMapper(),
                    userRegDto.getFirstName(),
                    userRegDto.getLastName(),
                    userRegDto.getLogin(),
                    userRegDto.getEmail(),
                    userRegDto.getPhone(),
                    userRegDto.getPassword()
            );
        } catch (CustomSQLException | InsertErrorException e) {
            throw new UserInsertException(e.getMessage());
        }
    }

    @Override
    public User update(User user) {
        String sql = "update "
                + UserTable.tableName + " set "
                + UserTable.firstName + " = ?, "
                + UserTable.lastName + " = ?, "
                + UserTable.login + " = ?, "
                + UserTable.email + " = ?, "
                + UserTable.phone + " = ?, "
                + UserTable.password + " = ?"
                + " where "
                + UserTable.id + " = ?";
        try {
            return customJdbcTemplate.update(sql,
                    UserRowMapper.getRowMapper(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getLogin(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getPassword(),
                    user.getId()
            );
        } catch (CustomSQLException | UpdateErrorException e) {
            throw new UserUpdateException(e.getMessage());
        }
    }

    @Override
    public void delete(User user) {
        String sql = "delete from "
                + UserTable.tableName + " where "
                + UserTable.id + " = ?";
        try {
            customJdbcTemplate.delete(sql,
                    user.getId());
        } catch (CustomSQLException e) {
            throw new UserDeleteException(e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        String sql = "truncate " + UserTable.tableName;
        try {
            customJdbcTemplate.deleteAll(sql);
        } catch (CustomSQLException e) {
            throw new UserDeleteException(e.getMessage());
        }
    }

}
