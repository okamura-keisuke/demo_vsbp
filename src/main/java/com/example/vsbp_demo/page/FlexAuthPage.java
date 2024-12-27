package com.example.vsbp_demo.page;

import com.example.vsbp_demo.service.IUserService;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

@Route("flex")
@PermitAll
public class FlexAuthPage extends VerticalLayout {

    private final IUserService userService;

    @Autowired
    public FlexAuthPage(IUserService userService) {
        this.userService = userService;

        add(new H2("Authenticated user can see this"));

        var userName = VaadinService.getCurrentRequest().getUserPrincipal().getName();
        var roles = userService.findUserAuthoritiesByUserName(userName);
        var rolesOfString = roles.getAuthorities().toString();

        if (rolesOfString.contains("ROLE_ADMIN")) {
            add(new H2("Administrator"));
        }
        if (rolesOfString.contains("ROLE_USER")) {
            add(new H2("User"));
        }
    }
}