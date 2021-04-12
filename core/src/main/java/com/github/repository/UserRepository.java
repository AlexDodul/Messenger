package com.github.repository;

import com.github.dto.UserAuthDto;
import com.github.dto.UserRegDto;
import com.github.entity.User;
import com.github.exceptions.*;
import com.github.micro.orm.CustomJdbcTemplate;
import com.github.micro.orm.exceptions.ElementNotFoundException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.SQLException;
import java.util.Collection;

public class UserRepository implements IUserRepository{

    private final HikariConfig config;

    private final CustomJdbcTemplate<User> customJdbcTemplate;

    public UserRepository(HikariConfig config) {
        this.config = config;
        HikariDataSource dataSource = new HikariDataSource(this.config);
        customJdbcTemplate = new CustomJdbcTemplate<>(dataSource);
    }

    @Override
    public Collection<User> findAll() {
        String sql = "select * from " + UserTable.tableName;
        try {
            return customJdbcTemplate.findAll(
                    sql,
                    UserRowMapper.getRowMapper()
            );
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public User findAuth(UserAuthDto userAuthDto) {
        String sql = "select * from "
                + UserTable.tableName + "where "
                + UserTable.login + " = ? and "
                + UserTable.password + " = ?";
        try {
            return customJdbcTemplate.findBy(
                    sql,
                    UserRowMapper.getRowMapper(),
                    userAuthDto.getLogin(),
                    userAuthDto.getPassword()
            );
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } catch (ElementNotFoundException e){
            throw new UserNotFoundException(e.getMessage());
        }
    }

    @Override
    public User findReg(UserRegDto userRegDto) {
        String sql = "select * from "
                + UserTable.tableName + "where "
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
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } catch (ElementNotFoundException e){
            throw new UserNotFoundException(e.getMessage());
        }
    }

    @Override
    public void insert(UserRegDto userRegDto) {
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
            customJdbcTemplate.insert(sql,
                    userRegDto.getFirstName(),
                    userRegDto.getLastName(),
                    userRegDto.getLogin(),
                    userRegDto.getEmail(),
                    userRegDto.getPhone(),
                    userRegDto.getPassword()
            );
        } catch (SQLException e) {
            throw new InsertUserException(e.getMessage());
        }
    }

    @Override
    public void update(User user) {
        String sql = "update "
                + UserTable.tableName + " set "
                + UserTable.firstName + " = ?, "
                + UserTable.lastName + " = ?, "
                + UserTable.login + " = ?, "
                + UserTable.email + " = ?, "
                + UserTable.phone + " = ?, "
                + UserTable.password + " = ? "
                + "where "
                + UserTable.id + " = ?";
        try {
            customJdbcTemplate.insert(sql,
                    user.getFirstName(),
                    user.getLastName(),
                    user.getLogin(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getPassword(),
                    user.getId()
            );
        } catch (SQLException e) {
            throw new UserUpdateException(e.getMessage());
        }
    }

    @Override
    public void delete(User user) {
        String sql = "delete from "
                + UserTable.tableName + " where "
                + UserTable.id + " = ?";
        try {
            customJdbcTemplate.delete(sql, user.getId());
        } catch (SQLException e) {
            throw new UserDeleteException(e.getMessage());
        }
    }

}
