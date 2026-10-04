package com.suyuhuang.Mymall.Service.ServiceImpl;

import com.suyuhuang.Mymall.DAO.UserDao;
import com.suyuhuang.Mymall.Model.User;
import com.suyuhuang.Mymall.Service.UserService;
import com.suyuhuang.Mymall.dto.UserLoginRequest;
import com.suyuhuang.Mymall.dto.UserRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;


@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {

        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        if(user != null)
        {
            log.warn("該mail:{} 已被註冊",userRegisterRequest.getEmail());//log可以用{}表示變數
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //md5-hash密碼
        String hashedPassword  = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashedPassword);

        return userDao.creatUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());

        if(user == null)
        {
            log.warn("該email:{}尚未註冊", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //hash
        String hashedPassword  = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());

        if(user.getPassword().equals(hashedPassword))
        {
            return user;
        }
        else
        {
            log.warn("密碼錯誤");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }
}
