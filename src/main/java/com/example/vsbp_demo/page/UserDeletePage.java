package com.example.vsbp_demo.page;

import com.example.vsbp_demo.service.IUserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@Route("UserDelete")
@PageTitle("UserDelete")
@RolesAllowed("ADMIN")
public class UserDeletePage extends VerticalLayout {

    private final IUserService userService;

    @Autowired
    public UserDeletePage(IUserService userService) {

        this.userService = userService;

        // ユーザー名入力フィールド
        TextField userNameField = new TextField("ユーザー名");

        // フォームレイアウト
        var formLayout = new FormLayout();
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        formLayout.setWidth("45%");

        // 削除ボタン
        var deleteButton = new Button("ユーザー削除");
        deleteButton.addClickListener(clickEvent -> {
            String userName = userNameField.getValue();
            System.out.println("送信データ：" + userName);
            if (userService.findByName(userName)) {
                userService.deleteUser(userName);
            } else {
                Notification.show("そのユーザは存在しません");
                return;
            }
            Notification.show("ユーザー " + userName + " が削除されました。");
            VaadinSession.getCurrent().setAttribute("deletedUserName", userName);
            UI.getCurrent().getPage().setLocation("UserDeleteComp");
        });

        // ホームページへのリンクボタン
        var toHomeButton = new Button("ホームに戻る");
        toHomeButton.addClickListener(clickEvent -> {
            UI.getCurrent().getPage().setLocation("/");
        });

        // formにまとめる
        formLayout.add(userNameField, deleteButton, toHomeButton);

        // レイアウトに追加
        add(formLayout);
    }
}
