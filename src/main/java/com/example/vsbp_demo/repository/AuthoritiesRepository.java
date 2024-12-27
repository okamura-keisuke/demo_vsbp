package com.example.vsbp_demo.repository;

import com.example.vsbp_demo.data.Authorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuthoritiesRepository implements IAuthoritiesRepository {

    private final JdbcTemplate jdbc;

    @Autowired
    public AuthoritiesRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Authorities selectAuthorities(String userName) {
        var sql = "select user_name, user_roles from authorities where user_name = ?";
        Authorities authorities = jdbc.queryForObject(sql, DataClassRowMapper.newInstance(Authorities.class), userName);
        return authorities;
    }

    @Override
    public int insertAuthorities(String userName, String userRoles) {
        var sql = "insert into authorities values (?, ?)";
        var n = jdbc.update(sql, userName, userRoles);
        return n;
    }
}