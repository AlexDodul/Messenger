package com.github.repository.user;

import com.github.micro.orm.CustomJdbcTemplate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.sql.DataSource;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTestTest {

    @Mock
    private CustomJdbcTemplate jdbcTemplate;

    private IUserRepository userRepository;

    @Before
    public void setup() {
        //this.userRepository = new UserRepository(this.jdbcTemplate);
    }

    @Test
    public void insert() {
        this.userRepository.insert(null);
    }
}