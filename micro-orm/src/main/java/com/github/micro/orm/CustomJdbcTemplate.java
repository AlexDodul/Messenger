package com.github.micro.orm;

import javax.sql.DataSource;
import java.sql.*;

public class CustomJdbcTemplate {

    private final DataSource dataSource;

    public CustomJdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> T findBy(String query, CustomRowMapper<T> rowMapper, Object... params){
        T result = null;
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){
            for(int i = 0; i < params.length; i++){
                statement.setObject(i + 1, params[i]);
            }
        } catch (SQLException e){
            System.out.printf("Message %s \n", e.getMessage());
        }
        return result;
    }

    public <T> void insert(String query, CustomRowMapper<T> rowMapper, Object... params){
        try(Connection connection = this.dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(query)){
            for(int i = 0; i < params.length; i++){
                statement.setObject(i + 1, params[i]);
            }
        } catch (SQLException e){
            System.out.printf("Message %s \n", e.getMessage());
        }
    }

    public <T> void update(String query, CustomRowMapper<T> rowMapper, Object... params){
        try (Connection connection = this.dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            for(int i = 0; i < params.length; i++){
                statement.setObject(i + 1, params[i]);
            }
            int row = statement.executeUpdate();
            if(row != 0){
                ResultSet resultSet = statement.getGeneratedKeys();
                resultSet.next();
            }
        } catch (SQLException e){
            System.out.printf("Message %s \n", e.getMessage());
        }
    }

    public <T> void deleteBy(String query, CustomRowMapper<T> rowMapper, Object... params){
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){
            for(int i = 0; i < params.length; i++){
                statement.setObject(i + 1, params[i]);
            }
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.printf("Message %s \n", e.getMessage());
        }
    }


}
