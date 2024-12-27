package com.example.vsbp_demo.page;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("OnlySuper")
@Route("super")
@RolesAllowed("SUPER")
public class OnlySuperPage extends VerticalLayout {
    public OnlySuperPage(AuthenticationContext context) {

        var headLine = new H1("全てのユーザに表示される");
        add(headLine);
    }
}
