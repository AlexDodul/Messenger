package com.github.micro.orm;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface CustomRowExtractor<T> {

    T extract(ResultSet resultSet) throws SQLException;

}
