package com.example.vsbp_demo.page.signed;

import com.example.vsbp_demo.data.AuthUser;
import com.example.vsbp_demo.service.IUserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("SignedPage")
@Route("Signed")
@PermitAll
public class SignedPage extends VerticalLayout {

    private final IUserService userService;

    @Autowired
    public SignedPage(IUserService userService) {
        this.userService = userService;

        var headLine = new H1("認証後");
        var userName = VaadinService.getCurrentRequest().getUserPrincipal().getName();

        var nameText = new Paragraph("あなたは" + userName + "さんです");
        add(headLine, nameText);

        var signOutButton = new Button("サインアウト");
        signOutButton.addClickListener(clickEvent -> {
            VaadinSession.getCurrent().getSession().invalidate();
        });
        add(signOutButton);

        // Service からデータベースのユーザ一覧をもらう。
        var authUsersModel = userService.findAuthUsers();

        Grid<AuthUser> grid = new Grid<>(AuthUser.class);
        grid.setItems(authUsersModel);
        add(grid);
    }
}