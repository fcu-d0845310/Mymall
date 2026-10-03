package com.suyuhuang.Mymall.Service.ServiceImpl;

import com.suyuhuang.Mymall.DAO.UserDao;
import com.suyuhuang.Mymall.Model.User;
import com.suyuhuang.Mymall.Service.UserService;
import com.suyuhuang.Mymall.dto.UserRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.creatUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
