package com.example.vsbp_demo.page.Error;

import com.example.vsbp_demo.page.HomePage;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("NotEnoughAuthZ")
@PageTitle("権限がありません")
@AnonymousAllowed
public class NotEnoughAuthZPage extends VerticalLayout {
    public NotEnoughAuthZPage() {
        var headLine =
                new H1("パス" + VaadinSession.getCurrent().getSession().getAttribute("AccessDenied") + "を閲覧する権限がありません");
        add(headLine);

        var toHomeButton = new Button("ホームへ戻る");
        toHomeButton.addClickListener(e -> {
           VaadinSession.getCurrent().getSession().setAttribute("AccessDenied", null);
           UI.getCurrent().getPage().setLocation("/");
        });
        add(toHomeButton);
    }
}
