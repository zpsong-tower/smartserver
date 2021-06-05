package com.tower.smartservice.provider;

import com.tower.smartservice.bean.db.UserEntity;
import com.tower.smartservice.bean.response.base.ResponseBuilder;
import com.tower.smartservice.factory.UserFactory;
import com.tower.smartservice.utils.TextUtil;
import org.glassfish.jersey.server.ContainerRequest;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;

/**
 * 筛选器
 * 用于所有请求接口的过滤和拦截
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/6/5 21:30
 */
@Provider
public class AuthRequestFilter implements ContainerRequestFilter {
	// 不需要拦截的接口
	private static final String[] NO_FILTER = {
			"account/login", // 登录
			"account/register" // 注册
	};

	// 拦截标识 Token
	private static final String TOKEN = "token";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// 对指定接口不做拦截
		String path = ((ContainerRequest) requestContext).getPath(false);
		for (String s : NO_FILTER) {
			if (path.startsWith(s)) {
				return;
			}
		}

		String token = requestContext.getHeaders().getFirst(TOKEN);
		if (!TextUtil.isEmpty(token)) {
			UserEntity self = UserFactory.findByToken(token);
			if (self != null) {
				requestContext.setSecurityContext(new SecurityContext() {
					@Override
					public Principal getUserPrincipal() {
						return self;
					}

					@Override
					public boolean isUserInRole(String role) {
						return true;
					}

					@Override
					public boolean isSecure() {
						return false;
					}

					@Override
					public String getAuthenticationScheme() {
						return null;
					}
				});

				// 写入上下文后就返回
				return;
			}
		}

		// 构建一个Token无效的返回
		Response response = Response.status(Response.Status.OK)
				.entity(ResponseBuilder.authTokenInvalid())
				.build();

		// 拦截 停止请求的继续下发
		requestContext.abortWith(response);
	}
}
