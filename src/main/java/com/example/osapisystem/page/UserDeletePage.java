package com.example.osapisystem.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.wicketstuff.annotation.mount.MountPath;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import com.example.osapisystem.service.IUserService;

@MountPath("UserDelete")
public class UserDeletePage extends WebPage {

    //IUserService を IoC/DI する
    @SpringBean
    private IUserService userService;

    public UserDeletePage() {
        var userNameModel = Model.of("");

        var toSignLink = new BookmarkablePageLink<>("toSign", SignPage.class);
        add(toSignLink);

        var userInfoForm = new Form<>("userInfo") {
            @Override
            protected void onSubmit() {
                var userName = userNameModel.getObject();
                var msg = "送信データ：" + userName;
                System.out.println(msg);

                // IoC/DI した userService のメソッドを呼び出す
                userService.deleteUser(userName);

                setResponsePage(new UserDeleteCompPage(userNameModel));
            }
        };
        add(userInfoForm);

        var userNameField = new TextField<>("userName", userNameModel);
        userInfoForm.add(userNameField);
    }
}
