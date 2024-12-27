package com.example.vsbp_demo.page.Error;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AccessDeniedException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.RouteAccessDeniedError;
import com.vaadin.flow.server.HttpStatusCode;

public class CustomAccessDeniedError extends RouteAccessDeniedError {
    @Override
    public int setErrorParameter(BeforeEnterEvent event,
                                 ErrorParameter<AccessDeniedException> parameter) {

        // VerticalLayoutにスタイルを追加して中央寄せ
        var vertical = new VerticalLayout();
        vertical.setAlignItems(FlexComponent.Alignment.CENTER);  // 要素を中央に配置
        vertical.setSizeFull();  // 親コンテナのサイズをフルにする

        // 表示メッセージを含むDivコンポーネントを作成
        var headLine = new H1("このページにアクセスする権限がありません。");
        headLine.addClassName("headLineCenter");  // 必要に応じてCSSクラスを追加

        // ボタンの作成とスタイルの設定
        var toHomeButton = new Button("ホームへ戻る");
        toHomeButton.addClickListener(e -> {
            UI.getCurrent().getPage().setLocation("/");
        });
        toHomeButton.getElement().getStyle()
                .set("margin-top", "20px"); // ボタンとの間に余白を設定

        // VerticalLayoutに要素を追加
        vertical.add(headLine, toHomeButton);

        // メッセージとリンクをUIに追加
        getElement().appendChild(vertical.getElement());

        return HttpStatusCode.UNAUTHORIZED.getCode(); // HTTP 401ステータスを返す
    }

}
