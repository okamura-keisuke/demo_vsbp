package com.example.vsbp_demo.data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public record Authorities(String userName, String userRoles) {

    // VaadinWebSecurity(Spring-Security)に対応する形でuserRolesを返すメソッド
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(userRoles) // userRolesがnullの場合、Optional.empty()を返す
                .map(roles -> roles.trim().split(",")) // userRolesが存在する場合、前後の空白を取り除き、カンマで分割
                .stream() // Optional<String[]>をストリームに変換（非nullの場合のみ動作）
                .flatMap(Arrays::stream) // String[] を Stream<String> に変換
                .map(SimpleGrantedAuthority::new) // 各ロール文字列を SimpleGrantedAuthority オブジェクトに変換
                .collect(Collectors.toList()); // SimpleGrantedAuthorityのリストを作成して返す
    }
}
