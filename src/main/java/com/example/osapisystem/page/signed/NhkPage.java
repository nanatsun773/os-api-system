package com.example.osapisystem.page.signed;

import com.example.osapisystem.MySession;
import com.example.osapisystem.page.SignPage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.wicketstuff.annotation.mount.MountPath;

import java.net.URL;
import java.io.*;

@AuthorizeInstantiation(Roles.USER)
@MountPath("nhk")
public class NhkPage extends WebPage {

    public NhkPage() throws IOException {

        final String endpoint = "https://api.nhk.or.jp/v2/pg/now/010/g1.json";

        final String key = "Cn0MIQ2u5oJaoHY6Il1Z46ss9YH8UJ6T";

        String url = endpoint + "?key=" + key;
        URL obj = new URL(url);
        InputStreamReader streamReader = new InputStreamReader(obj.openStream());
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        String content = bufferedReader.readLine();
        var objectMapper = new ObjectMapper();
        var jsonNode = objectMapper.readTree(content);

        var previousTitle = jsonNode.get("nowonair_list").get("g1").get("previous").get("title");
        var previousSubtitle = jsonNode.get("nowonair_list").get("g1").get("previous").get("subtitle");
        var presentTitle = jsonNode.get("nowonair_list").get("g1").get("present").get("title");
        var presentSubtitle = jsonNode.get("nowonair_list").get("g1").get("present").get("subtitle");
        var followingTitle = jsonNode.get("nowonair_list").get("g1").get("following").get("title");
        var followingSubtitle = jsonNode.get("nowonair_list").get("g1").get("following").get("subtitle");

        var previousTitleModel = Model.of(previousTitle.toString());
        var previousTitleLabel = new Label("previousTitle", previousTitleModel);
        add(previousTitleLabel);

        var previousSubtitleModel = Model.of(previousSubtitle.toString());
        var previousSubtitleLabel = new Label("previousSubtitle", previousSubtitleModel);
        add(previousSubtitleLabel);

        var presentTitleModel = Model.of(presentTitle.toString());
        var presentTitleLabel = new Label("presentTitle", presentTitleModel);
        add(presentTitleLabel);

        var presentSubtitleModel = Model.of(presentSubtitle.toString());
        var presentSubtitleLabel = new Label("presentSubtitle", presentSubtitleModel);
        add(presentSubtitleLabel);

        var followingTitleModel = Model.of(followingTitle.toString());
        var followingTitleLabel = new Label("followingTitle", followingTitleModel);
        add(followingTitleLabel);

        var followingSubtitleModel = Model.of(followingSubtitle.toString());
        var followingSubtitleLabel = new Label("followingSubtitle", followingSubtitleModel);
        add(followingSubtitleLabel);

        Link<Void> signoutLink = new Link<Void>("signout") {

            @Override
            public void onClick() {
                // セッションの破棄
                MySession.get().invalidate();
                // SignPageへ移動
                setResponsePage(SignPage.class);
            }
        };
        add(signoutLink);

    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem.forUrl("css/nhk_api.css"));
        response.render(CssHeaderItem.forUrl("https://cdnjs.cloudflare.com/ajax/libs/foundation/6.3.1/css/foundation.min.css"));
    }
}
