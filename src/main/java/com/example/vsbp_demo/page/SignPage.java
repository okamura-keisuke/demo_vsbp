package com.example.vsbp_demo.page;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.security.VaadinWebSecurity;

@Route("Sign")
@PageTitle("Sign")
@AnonymousAllowed
public class SignPage extends VerticalLayout implements BeforeEnterObserver {

    // ログインフォームをフィールド変数として宣言
    private LoginForm login = new LoginForm();

    public SignPage() {

        // このビューにCSS用のクラス名を追加。
        addClassName("SignPage");

        // ページ全体を埋めるレイアウト設定。
        setSizeFull();

        // ざっくり言うとログインフォームを中央に配置するレイアウト設定。
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        // ログインフォームの送信先を/Signに設定
        // 要はログイン(サインイン)を行うためのページを指定している。
        // 今回だとSignPage自分自身の@Routeである"Sign"
        login.setAction("Sign");

        // ページのH1ヘッドラインとフォームをレイアウトに追加。
        add(new H1("Vsbp Application"), login);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // 認証済みユーザーは直接 "Signed" にリダイレクト
        if (VaadinSession.getCurrent().getAttribute("user") != null) {
            beforeEnterEvent.forwardTo("Signed"); // 認証済みユーザーは "Signed" に遷移
        }

        // ログインエラーがある場合にエラーフラグを設定
        if (beforeEnterEvent.getLocation() // エラー時URLの取得
                .getQueryParameters() // クエリパラメータの取得　?以降
                .getParameters() // クエリパラメータと下のキーcontainsKeyでマッピング
                .containsKey("error")) { // errorを含む場合、true、含まない場合、false
            login.setError(true); // errorであった場合にエラーをセット
        }
    }
}
