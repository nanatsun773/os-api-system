package com.example.osapisystem;

import com.giffing.wicket.spring.boot.starter.app.WicketBootSecuredWebApplication;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OsApiSystemApplication extends WicketBootSecuredWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(OsApiSystemApplication.class, args);
	}

	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		return MySession.class;
	}

}

