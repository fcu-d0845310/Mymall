package com.suyuhuang.Mymall.Service;

import com.suyuhuang.Mymall.Model.User;
import com.suyuhuang.Mymall.dto.UserLoginRequest;
import com.suyuhuang.Mymall.dto.UserRegisterRequest;

public interface UserService {
    Integer register(UserRegisterRequest userRegisterRequest);
    User getUserById(Integer userId);
    User login(UserLoginRequest userLoginRequest);
}
