package com.example.vsbp_demo.service;


import com.example.vsbp_demo.data.AuthUser;
import com.example.vsbp_demo.data.Authorities;

import java.util.List;

public interface IUserService {

    public void registerUser(String userName, String userPass);

    public void deleteUser(String userName);

    /**
     * ユーザ名とパスワードをデータベースに照合する
     *
     * @param userName ユーザー名
     * @param userPass パスワード
     * @return 照合成功であれば<code>true</code>, 照合失敗は<code>false</code>
     */
    public boolean existsUser(String userName, String userPass);

    /**
     * ユーザ名からユーザの存在を確認する
     *
     * @param userName
     *
     * return void
     */
    public boolean findByName(String userName);
    /**
     * サインインしたユーザのロールを取得する
     *
     * @param userName
     *
     * @return Authorities
     */
    public Authorities findUserAuthoritiesByUserName(String userName);

    /**
     * ユーザの権限レコードを作成
     *
     * @param userName
     * @param userRoles
     *
     * return n 更新行数
     */
    public void registerAuthorities(String userName, String userRoles);

    /**
     * ユーザ名とパスワードの一覧を、AuthUser型のリストで検索する
     *
     * @return AuthUser型のListインスタンス
     */
    public List<AuthUser> findAuthUsers();

}