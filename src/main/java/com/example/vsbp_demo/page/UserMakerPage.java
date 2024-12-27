package com.example.vsbp_demo.page;

import com.example.vsbp_demo.service.IUserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@PageTitle("UserMakerPage")
@Route("UserMaker")
@RolesAllowed({"USER", "ADMIN"})
public class UserMakerPage extends VerticalLayout {

    private final IUserService userService;

    @Autowired
    public UserMakerPage(IUserService userService) {

        this.userService = userService;
        // ページに表示するページの役割の説明文
        var headLine = new H1("UserMakerPage");
        add(headLine);

        // 入力フォームレイアウトの作成
        var userInfoForm = new FormLayout();
        userInfoForm.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        userInfoForm.setWidth("45%");

        // ユーザー名フィールド
        var userNameField = new TextField("ここにユーザー名を入力して");
        userNameField.setRequired(true); // setRequired(true)で入力必須とする

        // パスワードフィールド
        var userPassField = new PasswordField("ここにパスワードを入力して");
        userPassField.setRequired(true);

        // ロールフィールド
        TextField userRoleField = new TextField("ここにユーザのロールを入力して");
        userPassField.setRequired(true);

        var binder = new Binder<>();
        binder.forField(userNameField)
                .withValidator(name -> name.length() >= 8 && name.length() <= 32, "ユーザー名は8~32文字で入力してください")
                .bind(user -> "", (user, name) -> {
                });

        binder.forField(userPassField)
                .withValidator(pass -> pass.length() >= 8 && pass.length() <= 32, "パスワードは8~32文字で入力してください")
                .bind(user -> "", (user, pass) -> {
                });

        // userRole のバリデータ
        binder.forField(userRoleField)
                .withValidator(role -> role.matches("^ROLE_.*"), "ロール名は 'ROLE_' で始める必要があります")
                .bind(user -> "", (user, role) -> {
                });

        // フォーム送信ボタン
        Button submitButton = new Button("新規ユーザ登録");
        submitButton.addClickListener(clickEvent -> {
            if (!binder.validate().isOk()) return; // 変更箇所２
            var userName = userNameField.getValue();
            var userPass = userPassField.getValue();
            var userRole = userRoleField.getValue();
            var msg = "入力データ：" + userName + "," + userPass + "," + userRole;
            System.out.println(msg);

            // IoC/DI した userService のメソッドを呼び出す
//            userService.registerUser(userName, userPass);
//            userService.registerAuthorities(userName, userRole);

            userRole = getPrefixedRoles(userRole);
            try {
                userService.registerUser(userName, userPass);
                userService.registerAuthorities(userName, userRole);
            } catch (Exception e) {
                Notification.show("そのユーザ名は既に登録されています");
                return;
            }

            Notification.show("以下の入力とともにボタンが押されました" + "\n" + msg);
            VaadinSession.getCurrent().setAttribute("makedUserName", userName);
            UI.getCurrent().getPage().setLocation("UserMakerComp");
        });

        var toHomeButton = new Button("ホームへ戻る");
        toHomeButton.addClickListener(clickEvent -> {
           UI.getCurrent().getPage().setLocation("/");
        });

        userInfoForm.add(userNameField, userPassField, userRoleField, submitButton);

        add(userInfoForm, toHomeButton);
    }

    private String getPrefixedRoles(String userRoles) {
        return Optional.ofNullable(userRoles)
                .map(roles -> roles.trim().split(",")) // カンマで分割
                .stream()
                .flatMap(Arrays::stream)              // Stream<String> に変換
                .map(role -> "ROLE_" + role.trim())   // 各要素に "ROLE_" を付加
                .collect(Collectors.joining(","));   // カンマで結合
    }
}
