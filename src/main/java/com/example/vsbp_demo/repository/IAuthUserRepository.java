package com.example.vsbp_demo.repository;

import com.example.vsbp_demo.data.AuthUser;

import java.util.List;

public interface IAuthUserRepository {

    /**
     * ユーザー名とパスワードをAuthUserテーブルに記録する
     *
     * @param userName ユーザー名
     * @param userPass パスワード
     * @return データベースの更新行数
     */
    public int insert(String userName, String userPass);

    /**
     * 入力し送信したユーザ名の情報をデータベースから削除する
     *
     * @param userName
     * @return データベースの削除行数
     */
    public int delete(String userName);

    /**
     * ユーザ名とパスワードが一致するレコードがAuthUserテーブルにあるか検索する
     *
     * @param userName ユーザー名
     * @param userPass パスワード
     * @return レコードの有無, 存在すれば<code>true</code>, それ以外は <code>false</code>
     */
    public boolean exists(String userName, String userPass);

    /**
     * ユーザ名でユーザのレコードの存在を確認
     *
     * @param userName
     *
     * return void
     */
    public boolean existsByUserName(String userName);

    /**
     * AuthUserテーブルのすべての情報を検索する
     *
     * @return レコードの内容を {@link AuthUser} の {@link List} で返す
     */
    public List<AuthUser> find();

}