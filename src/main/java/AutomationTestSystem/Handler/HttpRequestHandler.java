package AutomationTestSystem.Handler;

import java.util.Map;

import AutomationTestSystem.Base.TestStep;
import AutomationTestSystem.Util.HttpGetRequestUtil;
import AutomationTestSystem.Util.HttpPostRequestUtil;

/**
 * http请求动作处理类
 */
public class HttpRequestHandler {
	/**
	 * 指定API接口URL,发送GET请求
	 * @param step
	 * @throws Exception 
	 */
	public void sendGet(TestStep step) throws Exception{
		System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
		String ApiUrl = step.getUrl();
        HttpGetRequestUtil.GetJsonResult(ApiUrl);
	}
	
	/**
	 * 指定API接口URL,发送POST请求
	 * <step action="send-post" url="http://api.admin.dev.luckeylink.com/user-community/delete" body="user_id=2566&community_unit_room_id=3414&user_community_id=4558" desc="调用移除用户接口，实现移除用户功能"/>
	 * @param step
	 */
	public void sendPost(TestStep step) throws Exception{
		System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
//		String ApiUrl = ConfigUtil.getProperty("test.api."+step.getUrl()+"", Constants.CONFIG_COMMON);
//		String Param = "{\""+step.getBody()+"\": \""+SeleniumUtil.parseStringHasEls(step.getValue())+"\"}";
//        HttpRequestUtil.SendPost(ApiUrl,Param);
        String ApiUrl = step.getUrl();
		String Param = step.getBody();
        HttpPostRequestUtil.GetJsonResultX(ApiUrl, Param);
	}
	
	/**
	 *指定API接口URL,获取Cookie
	 * @param step
	 * @throws Exception 
	 */
	public Map<String, String>  getCookie(TestStep step) throws Exception{
		System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
//		String ApiUrl = ConfigUtil.getProperty("test.api."+step.getUrl()+"", Constants.CONFIG_COMMON);
		String ApiUrl = step.getUrl();
		String Param = "{\"userId\": \"666666\",\"password\": \"612426\",\"type\": \"string\",\"version\": \"string\",\"identification\": \"string\"}";
        Map<String, String> CookieVal =HttpPostRequestUtil.GetCookie(ApiUrl,Param);
		return CookieVal;
	}
	
	/**
	 * 指定API接口URL,获取Token
	 * @param step
	 * @throws Exception 
	 */
	public String getToken(TestStep step) throws Exception{
		System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
		String ApiUrl = step.getUrl();
        String Token = HttpGetRequestUtil.GetJsonDataToken(ApiUrl);
        return Token;
	}
	
	/**
	 * 指定API接口URL,获取Value
	 * @param step
	 * @throws Exception 
	 */
	public int getIntValue(TestStep step) throws Exception{
		System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
		String ApiUrl = step.getUrl();
        int Value = HttpGetRequestUtil.GetJsonDataIntValue(ApiUrl,step.getValue());
        return Value;
	}
}