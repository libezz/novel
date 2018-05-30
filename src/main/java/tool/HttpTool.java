package tool;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class HttpTool {
	
	private static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	public static boolean isMobileDevice() {
		String userAgent = getRequest().getHeader("user-agent");
		String[] deviceArray = new String[]{"android","mac os","windows phone"};
		if(userAgent == null) {
			return false;
		}
		userAgent = userAgent.toLowerCase();
		for(int i=0; i<deviceArray.length; i++) {
			if(userAgent.indexOf(deviceArray[i]) > 0) {
				return true;
			}
		}
		return false;
	}
}
