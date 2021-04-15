package com.github.micro.orm;

import com.github.micro.orm.exceptions.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomJdbcTemplate <T>{

    private final DataSource dataSource;

    public CustomJdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Collection<T> findAll(String query, CustomRowMapper<T> rowMapper, Object... params) {
        try (Connection connection = this.dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query)) {
            List<T> result = new ArrayList<>();
            setParameters(stmt, params);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(rowMapper.rowMap(rs));
            }
            return result;
        } catch (SQLException e){
            throw new CustomSQLException(String.format("SQL Exception. Message: %s", e.getMessage()));
        }
    }

    public T findBy(String query, CustomRowMapper<T> rowMapper, Object... params) {
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            setParameters(stmt, params);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rowMapper.rowMap(rs);
            } else {
                throw new ElementNotFoundException("There is no such element in database.");
            }
        } catch (SQLException e){
            throw new CustomSQLException(String.format("SQL Exception. Message: %s", e.getMessage()));
        }
    }

    public T insert(String query, CustomRowMapper<T> rowMapper, Object... params) {
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            setParameters(stmt, params);
            int row = stmt.executeUpdate();
            if(row != 0){
                ResultSet resultSet = stmt.getGeneratedKeys();
                resultSet.next();
                return rowMapper.rowMap(resultSet);
            } else {
                throw new InsertErrorException("Failed to insert into database.");
            }
        } catch (SQLException e){
            throw new CustomSQLException(String.format("SQL Exception. Message: %s", e.getMessage()));
        }
    }

    public T update(String query, CustomRowMapper<T> rowMapper, Object... params) {
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            setParameters(stmt, params);
            int row = stmt.executeUpdate();
            if(row != 0){
                ResultSet resultSet = stmt.getGeneratedKeys();
                resultSet.next();
                return rowMapper.rowMap(resultSet);
            } else {
                throw new UpdateErrorException("Failed to insert into database.");
            }
        } catch (SQLException e){
            throw new CustomSQLException(String.format("SQL Exception. Message: %s", e.getMessage()));
        }
    }

    public void delete(String query, Object... params) {
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            setParameters(stmt, params);
            stmt.executeUpdate();
        } catch (SQLException e){
            throw new CustomSQLException(String.format("SQL Exception. Message: %s", e.getMessage()));
        }
    }

    public void deleteAll(String query, Object... params) {
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            setParameters(stmt, params);
            stmt.executeUpdate();
        } catch (SQLException e){
            throw new CustomSQLException(String.format("SQL Exception. Message: %s", e.getMessage()));
        }
    }

    private void setParameters(PreparedStatement statement, Object... params) {
        for (int i = 0; i < params.length; i++) {
            try {
                statement.setObject(i + 1, params[i]);
            } catch (SQLException e){
                throw new ParameterSettingException(String.format("Failed to set parameter %d: %s", i, params[i].toString()));
            }
        }
    }

}
