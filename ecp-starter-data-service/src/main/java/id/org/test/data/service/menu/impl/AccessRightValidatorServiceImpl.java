package id.org.test.data.service.menu.impl;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import id.org.test.data.service.menu.AccessRightValidatorService;
import id.org.test.data.service.menu.UserService;
import id.org.test.data.service.menu.wrapper.LoggedUserWrapper;

@Service
public class AccessRightValidatorServiceImpl implements AccessRightValidatorService {

	private static final Logger logger = LoggerFactory.getLogger(AccessRightValidatorServiceImpl.class);

	@Value("${spring.app.baseUrl}")
	private String baseUrl;

	private final UserService userService;
	private final HttpServletRequest request;

	@Autowired
	public AccessRightValidatorServiceImpl(UserService userService, HttpServletRequest request) {
		this.userService = userService;
		this.request = request;
	}



	@Override
	public String getRoleByUser(String username) {
		return userService.getRoleByUsername(username);
	}

	@Override
	public LoggedUserWrapper getByUsername(String username) {
		return userService.getByUsername(username);
	}

	private String compareMenuUrlWithAppProperties(String menuUrl) {
		String[] baseUrlList = null;
		if (baseUrl.contains(",")) {
			baseUrlList = baseUrl.split(",");
		}
		if (baseUrlList != null) {
			for (String url : baseUrlList) {
				if (url.endsWith("/"))
					url = url.substring(0, url.length() - 1);
				menuUrl = menuUrl.replace(url, "");
			}
		} else {
			if (baseUrl.endsWith("/")) {
				baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
			}
			menuUrl = menuUrl.replaceAll(baseUrl, "");
		}
		return menuUrl;
	}

	public static String getRequestContext(String uri) {
		uri = uri.replace("//", "");
		return uri.substring(uri.indexOf("/"));
	}

	public static String getRootDomain(String url) {
		logger.info("Url to check : " + url);
		if (url.split("//")[1].contains(".")) {
			String[] domainKeys = url.split("/")[2].split("\\.");
			int length = domainKeys.length;
			int dummy = domainKeys[0].equals("www") ? 1 : 0;
			if (length != 4) {
				if (length - dummy == 2)
					return domainKeys[length - 2] + "." + domainKeys[length - 1];
				else {
					// check if last key contains port
					if (domainKeys[length - 1].contains(":")) {
						logger.info("Url using port " + domainKeys[length - 1].split(":")[1]);
						return domainKeys[length - 3] + "." + domainKeys[length - 2] + "." + domainKeys[length - 1];
					} else {
						if (domainKeys[length - 1].length() == 2) {
							return domainKeys[length - 3] + "." + domainKeys[length - 2] + "." + domainKeys[length - 1];
						} else {
							return domainKeys[length - 2] + "." + domainKeys[length - 1];
						}
					}
				}
			} else {
				if (domainKeys[length - 1].contains(":")) {
					logger.info("Server using port to host:");
					if (isNumber(domainKeys[1])) {
						logger.info("Server using only IP address and port to host:");
						return url.split("//")[1].split("/")[0];
//	                    }else if(isNumber(domainKeys[length - 1 ]))
					} else { // our domain using three words [whee.co.id]
						String fullDomain = url.split("//")[1].split("/")[0];
						String[] tempList = fullDomain.split("\\.");
						String subDomainToRemove = tempList[0] + ".";
						return fullDomain.replace(subDomainToRemove, "");
					}
				} else {
					String fullDomain = url.split("//")[1].split("/")[0];
					String[] tempList = fullDomain.split("\\.");
					String subDomainToRemove = tempList[0] + ".";
					return fullDomain.replace(subDomainToRemove, "");
					// return url.split("//")[1].split("/")[0];
				}
			}
		} else {
			String[] domainKeys = url.split("/");
			int length = domainKeys.length;
			logger.info(String.valueOf(domainKeys.length));
			return domainKeys[2];
		}
	}

	private static Boolean isNumber(String strToCheck) {
		try {
			double d = Double.parseDouble(strToCheck);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

}
