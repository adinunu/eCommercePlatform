package id.org.test.data.service.menu.wrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import id.org.test.common.constant.AppConstant;
import lombok.Data;

@Data
public class LoggedUserWrapper implements Serializable {

	private static final long serialVersionUID = -7112286365899288190L;

	private String username;
	private String name;
	private String role;
	private String userType;
	private List<String> menuList = new ArrayList<String>();
	
	public void addMenu(String... menuUrls) {
		for (String mu : menuUrls) {
			menuList.add(mu);
		}
	}
	
	public static LoggedUserWrapper build(Authentication authentication) {
		
		WebAuthenticationDetails wad = null;
		Map authInfo = null;
		LoggedUserWrapper loggedUser = new LoggedUserWrapper();
		
		// if no login user, it will cast as WebAuthenticationDetails
		if(authentication.getDetails() instanceof WebAuthenticationDetails) {
			wad = (WebAuthenticationDetails) authentication.getDetails();
		} else { // if theres login user, it will cast as HashMap
			authInfo = (HashMap) authentication.getDetails();
		}
		
		if(null != authentication && null != authInfo) {
			loggedUser = (LoggedUserWrapper) authInfo.get("loggedUser");
		}
		
		return loggedUser;
	}
	
	public static LoggedUserWrapper build(HttpServletRequest request) {
		LoggedUserWrapper loggedUser = new LoggedUserWrapper();
		loggedUser.setUsername(String.valueOf(request.getAttribute(AppConstant.TokenInfo.USERNAME)));
		loggedUser.setRole(String.valueOf(request.getAttribute(AppConstant.TokenInfo.ROLE)));
		return loggedUser;
	}

}
