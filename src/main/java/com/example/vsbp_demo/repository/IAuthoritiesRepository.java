package com.example.vsbp_demo.repository;

import com.example.vsbp_demo.data.Authorities;

public interface IAuthoritiesRepository {

    /**
     * ユーザ名から権限情報を取得する
     *
     * @param userName return Authorities
     */
    public Authorities selectAuthorities(String userName);

    /**
     * ユーザ作成時にロールと紐づけたレコード挿入する
     *
     * @param userName
     * @param userRoles
     *
     * @return データベースの更新行数
     */
    public int insertAuthorities(String userName, String userRoles);
}