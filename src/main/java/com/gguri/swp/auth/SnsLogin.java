package com.gguri.swp.auth;

import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gguri.swp.domain.UserVO;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

public class SnsLogin {
	private OAuth20Service oauthService;
	private SnsValue sns;
	
	public SnsLogin(SnsValue sns) {
		this.oauthService = new ServiceBuilder(sns.getClientId())
				.apiSecret(sns.getClientSecret())
				.callback(sns.getRedirectUrl())
				.scope("profile")
				.build(sns.getApi20Instance());
		this.sns = sns;
	}
	public String getNaverAuthURL() {
		return this.oauthService.getAuthorizationUrl();
	}
	public UserVO getUserProfile(String code) throws Exception {
		OAuth2AccessToken accessToken = oauthService.getAccessToken(code);
		OAuthRequest request = new OAuthRequest(Verb.GET, this.sns.getProfileUrl());
		oauthService.signRequest(accessToken, request);
		Response response = oauthService.execute(request);
		return parseJson(response.getBody());
	}
	private UserVO parseJson(String body) throws Exception {
		UserVO user = new UserVO();
				
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(body);
		
		if(this.sns.getIsGoogle()){
			user.setGoogleId(rootNode.get("id").asText());
			user.setNickname(rootNode.get("displayName").asText());
			JsonNode nameNode = rootNode.path("name");
			user.setUname(nameNode.get("familyName").asText() + nameNode.get("givenName").asText());
			
			Iterator<JsonNode> emails = rootNode.path("emails").elements();
			
			while(emails.hasNext()) {
				JsonNode emailNode = emails.next();
				String type = emailNode.get("type").asText();
				if(StringUtils.equals(type, "account")) {
					user.setEmail(emailNode.get("value").asText());
					break;
				}
			}

		}else if(this.sns.getIsNaver()) {
			JsonNode resNode = rootNode.get("response");
			user.setNaverId(resNode.get("id").asText());
			user.setNickname(resNode.get("nickname").asText());
			user.setEmail(resNode.get("email").asText());
		}
		return user;
	}
}
