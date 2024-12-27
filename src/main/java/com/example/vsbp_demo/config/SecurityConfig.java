package com.example.vsbp_demo.config;

import com.example.vsbp_demo.page.SignPage;
import com.example.vsbp_demo.service.IUserService;
import com.example.vsbp_demo.service.UserService;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    private final IUserService userService;

    // UserServiceの依存性注入
    @Autowired
    public SecurityConfig(IUserService userService) {
        this.userService = userService;
    }

    // configure(HttpSecurity)のオーバーライド
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        setLoginView(http, SignPage.class);
    }

    // インメモリユーザの登録、@BeanでDIコンテナに登録していりゅ
//    @Bean
//    public UserDetailsManager userDetailsService() {
//        UserDetails user =
//                User.withUsername("user")
//                        .password("{noop}user")
//                        .roles("USER")
//                        .build();
//        UserDetails admin =
//                User.withUsername("admin")
//                        .password("{noop}admin")
//                        .roles("ADMIN")
//                        .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }

    @Bean
    AuthenticationManager authenticationManager() {
        return authentication -> {
            // ログインフォームに入力されたユーザ名を取得
            String userName = authentication.getName();

            // ログインフォームに入力されたパスワードを取得
            String userPass = (String) authentication.getCredentials();

            // データベースの認証用情報に入力された情報と合致するものはあるか照合
            if (userService.existsUser(userName, userPass)) {

                // 照合できた場合に実行される
                // 照合できたユーザの権限情報(ロール)を取得
                var authorities = userService.findUserAuthoritiesByUserName(userName);

                // userNameとuserPass、
                // authoritriesのレコードクラスから取得した権限リストを元に
                // 認証済みのユーザー情報を格納したトークンを返す
                return new UsernamePasswordAuthenticationToken(userName, userPass, authorities.getAuthorities());
            }
            throw new BadCredentialsException("Invalid credentials");
        };
    }

}