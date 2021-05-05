package com.github.repository.message;

import com.github.entity.Message;
import com.github.exceptions.DatabaseException;
import com.github.exceptions.MessageInsertException;
import com.github.micro.orm.CustomJdbcTemplate;
import com.github.micro.orm.exceptions.CustomSQLException;
import com.github.micro.orm.exceptions.InsertErrorException;

import javax.sql.DataSource;
import java.util.LinkedList;
import java.util.List;

public class MessageRepository implements IMessageRepository {

    private CustomJdbcTemplate<Message> customJdbcTemplate;

    private final DataSource dataSource;

    public MessageRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        customJdbcTemplate = new CustomJdbcTemplate<>(this.dataSource);
    }

    @Override
    public void insert(String payload, String tableName) {
        String sql = "insert into "
                + tableName + " ("
                + MessageTable.payload
                + ") values (?)";
        try {
            customJdbcTemplate.insert(sql,
                    MessageRowMapper.getRowMapper(),
                    payload);
        } catch (CustomSQLException | InsertErrorException e) {
            throw new MessageInsertException(e.getMessage());
        }
    }

    @Override
    public List<Message> getMessages(int number, String tableName) {
        String sql = "select * from " + tableName +
                " order by " + MessageTable.id + " desc limit " + number;
        try {
            return new LinkedList<>(customJdbcTemplate.findAll(
                    sql,
                    MessageRowMapper.getRowMapper()
            ));
        } catch (CustomSQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
