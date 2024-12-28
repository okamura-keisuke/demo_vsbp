package com.example.vsbp_demo.page;

import com.example.vsbp_demo.data.AuthUser;
import com.example.vsbp_demo.service.IUserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.Optional;

@AnonymousAllowed
@Route("PullDown")
public class PullDownPage extends VerticalLayout {

    private final IUserService userService;

    public PullDownPage(IUserService userService) {
        this.userService = userService;

        var top = new H1("プルダウンメニューによる選択");
        var title = new H2("このシステムに登録されているユーザ一の一覧");
        var selectedModel = userService.findAuthUsers();

        ComboBox<AuthUser> select = new ComboBox<>();
        select.setPlaceholder("選んでください");
        select.setItems(selectedModel);

        // 何も選択していない状態に戻す手法
        select.setClearButtonVisible(true);

        //ドロップダウンに表示されるラベルの指定
        select.setItemLabelGenerator(AuthUser::userName);

        // ドロップダウンメニューの初期値設定
        Optional.ofNullable(selectedModel)
                .ifPresent(user ->
                        select.setValue(selectedModel.getFirst()));

        select.setRequiredIndicatorVisible(true);
        select.setI18n(new ComboBox.ComboBoxI18n()
                .setRequiredErrorMessage("選択してください"));

        var sendButton = new Button("送信");
        sendButton.addClickListener(clickEvent -> {
            if (select.getValue() == null) {
                return;
            }
            System.out.println(select.getValue());
        });

        var selectHorizontalLayout = new HorizontalLayout(select, sendButton);

        add(top, title, selectHorizontalLayout);
    }
}