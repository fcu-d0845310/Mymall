package com.suyuhuang.Mymall.DAO;

import com.suyuhuang.Mymall.Model.User;
import com.suyuhuang.Mymall.dto.UserRegisterRequest;

public interface UserDao {
    Integer creatUser(UserRegisterRequest userRegisterRequest);
    User getUserById(Integer userId);
    User getUserByEmail(String email);
}
