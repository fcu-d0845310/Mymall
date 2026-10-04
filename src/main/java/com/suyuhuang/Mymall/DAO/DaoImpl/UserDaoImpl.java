package com.suyuhuang.Mymall.DAO.DaoImpl;

import com.suyuhuang.Mymall.DAO.UserDao;
import com.suyuhuang.Mymall.Model.User;
import com.suyuhuang.Mymall.dto.UserRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    private final JdbcClient jdbcClient;

    @Override
    public Integer creatUser(UserRegisterRequest userRegisterRequest) {
        String sql = "INSERT INTO user(email, password, created_date, last_modified_date) " +
                "VALUES (:email, :password, :createdDate, :lastModifiedDate)";
        Date now = new Date();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(sql)
                .param("email",userRegisterRequest.getEmail())
                .param("password",userRegisterRequest.getPassword())
                .param("createdDate",now)
                .param("lastModifiedDate",now)
                .update(keyHolder,"user_id");
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public User getUserById(Integer userId) {
        String sql = "SELECT user_id, email, password, created_date, last_modified_date " +
                "FROM user WHERE user_id = :userId";

        return jdbcClient.sql(sql)
                .param("userId", userId)
                .query(User.class)
                .optional()
                .orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT user_id, email, password, created_date, last_modified_date " +
                "FROM user WHERE email =:email";

        return jdbcClient.sql(sql)
                .param("email", email)
                .query(User.class)
                .optional()
                .orElse(null);
    }
}
