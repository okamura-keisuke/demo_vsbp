package com.example.vsbp_demo.page;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.security.RolesAllowed;

@Route("UserDeleteComp")
@PageTitle("UserDeleteComp")
@RolesAllowed("ADMIN")
public class UserDeleteCompPage extends VerticalLayout {

    public UserDeleteCompPage() {

        var headLine = new H1("削除が完了されました");

        // 現在のセッションにおけるユーザ名の取得
        var userName = (String) VaadinSession.getCurrent().getAttribute("deletedUserName");

        // nullチェックを追加
        if (userName == null) {
            headLine = new H1("ユーザ削除ができていません");
            userName = "不明なユーザー"; // デフォルト値を設定
            Notification.show("セッションにユーザー名が設定されていません。デフォルト値を使用します。");
        }

        VaadinSession.getCurrent().setAttribute("deletedUserName", null);

        add(headLine);

        // ユーザー名ラベルの作成
        NativeLabel userNameLabel = new NativeLabel("ユーザー名: " + userName);
        add(userNameLabel);

        // 比較用　ラベル使用時の表示とテキストフィールドでの表示の違い
        var nameField = new TextField("ユーザ名");
        nameField.setValue(userName);
        nameField.setReadOnly(true);
        add(nameField);

        // ホームページへ遷移するボタンの作成
        var toHomeButton = new Button("ホームに戻る", e -> {
            UI.getCurrent().getPage().setLocation("/");
        });
        add(toHomeButton);
    }
}