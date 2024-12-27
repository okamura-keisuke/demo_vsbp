package com.example.vsbp_demo.page;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Home")
@Route("")
@AnonymousAllowed
public class HomePage extends VerticalLayout{

    public HomePage(){
        var layoutName = new H1("Hello ");
        var language = new H1("Vaadin");

        var com = new H3("こんにちは");

        //配置決め
        //横配置
        var horizontalLayout = new HorizontalLayout(layoutName, language);
        //縦配置
        var verticalLayout = new VerticalLayout(horizontalLayout, com);

        //以前までの内容は省略

        var toUserMaker = new Anchor("UserMaker","ユーザを新規追加");
        add(toUserMaker);

        var toUserDeletePage = new Anchor("UserDelete", "ユーザを削除");
        add(toUserDeletePage);

        add(verticalLayout);

        var signOutButton = new Button("サインアウト");
        signOutButton.addClickListener(clickEvent -> {
            UI.getCurrent().getPage().setLocation("Sign");
            VaadinSession.getCurrent().getSession().invalidate();
        });
        add(signOutButton);
    }
}
