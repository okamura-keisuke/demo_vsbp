package com.example.vsbp_demo.service;

import com.example.vsbp_demo.data.AuthUser;
import com.example.vsbp_demo.data.Authorities;
import com.example.vsbp_demo.repository.IAuthUserRepository;
import com.example.vsbp_demo.repository.IAuthoritiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    private final IAuthUserRepository authUserRepos;
    private final IAuthoritiesRepository authoritiesRepos;

    @Autowired
    public UserService(IAuthUserRepository authUserRepos, IAuthoritiesRepository authoritiesRepos) {
        this.authUserRepos = authUserRepos;
        this.authoritiesRepos = authoritiesRepos;
    }

    @Override
    public void registerUser(String userName, String userPass) {
        int n = authUserRepos.insert(userName, userPass);
        System.out.println("記録行数：" + n);
    }

    @Override
    public void deleteUser(String userName) {
        int n = authUserRepos.delete(userName);
        System.out.println("user記録行数：" + n);
    }

    @Override
    public boolean existsUser(String userName, String userPass) {
        var result = authUserRepos.exists(userName, userPass);
        System.out.println(userName + ", " + userPass + " のユーザ照合結果：" + result);
        return result;
    }

    @Override
    public boolean findByName(String userName) {
        var result = authUserRepos.existsByUserName(userName);
        System.out.println(userName + " のユーザ照合結果：" + result);
        return result;
    }

    ;

    @Override
    public Authorities findUserAuthoritiesByUserName(String userName) {
        var authorities = authoritiesRepos.selectAuthorities(userName);
        return authorities;
    }

    @Override
    public void registerAuthorities(String userName, String userRoles) {
        int n = authoritiesRepos.insertAuthorities(userName, userRoles);
        System.out.println("記録行数：" + n + " 行");
    }

    @Override
    public List<AuthUser> findAuthUsers() {
        var users = authUserRepos.find();
        System.out.println("データ件数：" + users.size());
        return users;
    }
}