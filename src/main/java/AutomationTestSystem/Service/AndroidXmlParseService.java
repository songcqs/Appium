package AutomationTestSystem.Service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import AutomationTestSystem.Base.StepAction;
import AutomationTestSystem.Base.TestCase;
import AutomationTestSystem.Base.TestStep;
import AutomationTestSystem.Base.TestUnit;
import AutomationTestSystem.Service.AppiumService;
import AutomationTestSystem.Util.ConfigUtil;
import AutomationTestSystem.Util.Constants;
import io.appium.java_client.android.AndroidDriver;

/**
 * <br>配置Appium</br>
 * <br>解析xml到TestUnit</br>
 *
 * @version 1.0
 * @since   1.0
 */
public class AndroidXmlParseService {

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(AndroidXmlParseService.class);

	static public AndroidDriver<WebElement> driver;
	static AppiumService appiumServer; 
	
	/**
	 * <br>Appium服务配置</br>
	 *
	 * @throws Exception
	 */
	public static void AppiumConfigure(String ApkName,String ApkPackageName,String PlatformName,String PlatformVersion,String DeviceID) throws Exception {
		Runtime.getRuntime().exec("adb -s "+DeviceID+" uninstall "+ApkPackageName+""); 
		//指定APK安装路径:
		File apk = new File(ConfigUtil.getProperty("apk.path", Constants.CONFIG_COMMON), ApkName);

		//设置自动化相关参数:
		DesiredCapabilities capabilities = new DesiredCapabilities();

		//设置Appium测试引擎:
		capabilities.setCapability("device", "uiautomator2");

		//指定测试设备系统及系统版本:
		capabilities.setCapability("platformName", PlatformName);
		capabilities.setCapability("platformVersion", PlatformVersion);
		
		//当前连接的手机,默认识别一台
//		capabilities.setCapability("deviceName", "Android Emulator");
	
		//指定测试设备名称及设备ID:
        capabilities.setCapability("deviceName", DeviceID);
        capabilities.setCapability("udid", DeviceID);
        
		//小米5S(黑色-USB有线连接)
//        capabilities.setCapability("deviceName", "29739ff4");
//        capabilities.setCapability("udid", "29739ff4");
        
        //小米5S(金色-WIFI无线连接)
//        capabilities.setCapability("deviceName", ConfigUtil.getProperty("MI_5S_golden.WIFI", Constants.CONFIG_COMMON));
//        capabilities.setCapability("udid", ConfigUtil.getProperty("MI_5S_golden.WIFI", Constants.CONFIG_COMMON));
        
		//初始化APP缓存，false(初始化)/true(不初始化)
		capabilities.setCapability("noReset", true);

		//重新安装APP，true(重新安装)/false(不重新安装)
		capabilities.setCapability("fullReset", false);
		
		//启动时是否覆盖session，true(覆盖)/false(不覆盖)
		capabilities.setCapability("sessionOverride", false);

		//开启中文输入，安装Unicode输入法，true(安装)/false(不安装)
		capabilities.setCapability("unicodeKeyboard", true);

		//还原系统默认输入法，true(还原)/false(不还原)
		capabilities.setCapability("resetKeyboard", true);

		//设置Appium超时时间:
		capabilities.setCapability("newCommandTimeout", 60000);

		//APK重新签名，false(重签)/true(不重签)
		capabilities.setCapability("noSign", true);

		//已安装后启动APP
		capabilities.setCapability("app", apk.getAbsolutePath());
		
		//进入Webview
//		capabilities.setCapability("autoWebview", true);

		//初始化AndroidDriver
		driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
	    
		//命令启动Appium Service
		//node C:\Users\King-liu\AppData\Local\Programs\Appium\resources\app\node_modules\appium\build\lib\main.js --address 127.0.0.1 --port 4723
		
		//设置全局隐性等待时间
		driver.manage().timeouts().implicitlyWait(80000, TimeUnit.MILLISECONDS);
	}

	public static void AppiumTest(String ApkName,String PlatformName,String PlatformVersion,String DeviceID) throws Exception {  
		File apk = new File(ConfigUtil.getProperty("apk.path", Constants.CONFIG_COMMON), ApkName);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("device", "uiautomator2");
		capabilities.setCapability("platformName", PlatformName);
		capabilities.setCapability("platformVersion", PlatformVersion);
		capabilities.setCapability("deviceName", DeviceID);
        capabilities.setCapability("udid", DeviceID);
		capabilities.setCapability("noReset", true);
		capabilities.setCapability("fullReset", false);
		capabilities.setCapability("app", apk.getAbsolutePath());
		capabilities.setCapability("sessionOverride", false);
		capabilities.setCapability("unicodeKeyboard", true);
		capabilities.setCapability("resetKeyboard", true);
		capabilities.setCapability("noSign", true);
//		capabilities.setCapability("autoWebview", true);
		capabilities.setCapability("newCommandTimeout", 60000);
		driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(80000, TimeUnit.MILLISECONDS);
	}
	
	public static void AppiumProduction(String ApkName,String PlatformName,String PlatformVersion,String DeviceID) throws Exception { 
		File apk = new File(ConfigUtil.getProperty("apk.path", Constants.CONFIG_COMMON), ApkName);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("device", "uiautomator2");
		capabilities.setCapability("platformName", PlatformName);
		capabilities.setCapability("platformVersion", PlatformVersion);
		capabilities.setCapability("deviceName", DeviceID);
        capabilities.setCapability("udid", DeviceID);
		capabilities.setCapability("noReset", true);
		capabilities.setCapability("fullReset", false);
		capabilities.setCapability("app", apk.getAbsolutePath());
		capabilities.setCapability("sessionOverride", false);
		capabilities.setCapability("unicodeKeyboard", true);
		capabilities.setCapability("resetKeyboard", true);
		capabilities.setCapability("noSign", true);
		capabilities.setCapability("autoWebview", "true");
		capabilities.setCapability("newCommandTimeout", 60000);
		driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(80000, TimeUnit.MILLISECONDS);
	}
	
	public static void AppiumCustom(String ApkName,String PlatformName,String PlatformVersion,String DeviceID,String Port) throws Exception {  
		File apk = new File(ConfigUtil.getProperty("apk.path", Constants.CONFIG_COMMON), ApkName);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("device", "uiautomator2");
		capabilities.setCapability("platformName", PlatformName);
		capabilities.setCapability("platformVersion", PlatformVersion);
		capabilities.setCapability("deviceName", DeviceID);
        capabilities.setCapability("udid", DeviceID);
		capabilities.setCapability("noReset", true);
		capabilities.setCapability("fullReset", false);
		capabilities.setCapability("app", apk.getAbsolutePath());
		capabilities.setCapability("sessionOverride", false);
		capabilities.setCapability("unicodeKeyboard", true);
		capabilities.setCapability("resetKeyboard", true);
		capabilities.setCapability("noSign", true);
//		capabilities.setCapability("autoWebview", true);
		capabilities.setCapability("newCommandTimeout", 60000);
		driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:"+Port+"/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(80000, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * <br>根据用例的名称，截取图片，进行保存</br>
	 *
	 * @param ScreenshotName
	 */
	@SuppressWarnings("unused")
	public static void screenShot(String ScreenshotName) {

		int t = 1;
		String AppointDir = System.getProperty("user.dir")+"\\TestOutput\\ExtentReport\\BugScreenshot\\";
		String picture = AppointDir + ScreenshotName + ".png";

		File file = new File(AppointDir);
		File[] fs = file.listFiles();
		File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		// 指定图片数量，过多删除
		try {
			if (fs.length >= 300) {
				for (File f : fs) {
					if (f.getName().contains("png"))
						f.delete();
				}
			}

			FileUtils.copyFile(screenShot, new File(picture));
			++t;
		} catch (IOException e) {
			System.out.println("截图失败:" + ScreenshotName);
			e.printStackTrace();
		} finally {
			System.out.println("『发现问题』开始执行: " + "<截图操作,保存目录为[" + picture + "]>");
		}
	}

	/**
	 * <br>根据用例的名称，录制视频，上传至指定目录</br>
	 * 
	 * @param CaseName
	 * @param CaseName
	 */
	public static void StartRecord(String CaseName) throws Exception {
	    Runtime rt = Runtime.getRuntime();
	    try {
	  		 rt.exec("cmd.exe /C adb shell screenrecord /sdcard/"+ CaseName +".mp4");
	  	} 
	    catch (IOException e) {
	    	 e.printStackTrace();
	  	}
	}
	  
	public void EndRecord(String CaseName,String SpecifiedDirectory) throws Exception {
	    Runtime rt = Runtime.getRuntime();
	    try {
	  		 rt.exec("cmd.exe /C adb kill-server");
	  		 rt.exec("cmd.exe /C adb start-server");
	  		 Thread.sleep(5000);
	  		 rt.exec("cmd.exe /C adb pull /sdcard/"+ CaseName +".mp4");
	  		 Thread.sleep(5000);
	  		 rt.exec("cmd.exe /C move "+ CaseName +".mp4 " + SpecifiedDirectory);
	  	} 
	    catch (IOException e) {
	    	 e.printStackTrace();
	  		 System.out.println("file not found"); 
	    }
	}
	
	/**
	 * <br>解析xml测试场景配置文件之前，对appium进行配置</br>
	 *
	 * @param xmlPath
	 * @return
	 */
	public static TestUnit parse(String ApkName,String ApkPackageName,String PlatformName,String PlatformVersion,String DeviceID,String XmlPath) {
		try {
			System.out.println("当前为重新安装APK，初始化Android设备,耐心等待App启动ing..."); 
			AppiumConfigure(ApkName,ApkPackageName,PlatformName,PlatformVersion,DeviceID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parse(new File("src/test/java/"+XmlPath));
	}
	
	public static TestUnit ParseTest(String ApkName,String PlatformName,String PlatformVersion,String DeviceID,String XmlPath) {
		try {
			System.out.println("当前为测试环境，开始初始化Android设备,耐心等待App启动ing..."); 
			AppiumTest(ApkName,PlatformName,PlatformVersion,DeviceID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parse(new File("src/test/java/"+XmlPath));
	}
	
	public static TestUnit ParseProduction(String ApkName,String PlatformName,String PlatformVersion,String DeviceID,String XmlPath) {
		try {
			System.out.println("当前为生产环境，开始初始化Android设备,耐心等待App启动ing..."); 
			AppiumProduction(ApkName,PlatformName,PlatformVersion,DeviceID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parse(new File("src/test/java/"+XmlPath));
	}
	
	public static TestUnit ParseCustom(int index,String ApkName,String PlatformName,String PlatformVersion,String DeviceID,String Prot,String XmlPath) {
		try {
			if(index==0){
				//0表示启动前会杀掉所有Appium服务，做为第一个Appium服务启动
				appiumServer =new AppiumService();
				appiumServer.StartAppium(Prot);
			}else if(index==1){
				//1表示不会杀掉Appium服务，接着启动第二个Appium服务，适用多台设备并发测试
				appiumServer =new AppiumService();
				appiumServer.appiumStart(Prot);
			}
			System.out.println("当前为预发布环境，开始初始化Android设备,耐心等待App启动ing..."); 
			AppiumCustom(ApkName,PlatformName,PlatformVersion,DeviceID,Prot);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parse(new File("src/test/java/"+XmlPath));
	}
	
	/**
	 * <br>解析xml测试场景配置文件，转换为一个TestUnit实例，也就是一个TestCase的集合</br>
	 *
	 * @param xmlFile
	 * @return
	 */
	public static TestUnit parse(File xmlFile) {

		TestUnit testUnit = null;

		if ( xmlFile == null || !xmlFile.exists()  )
			return testUnit;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		
		try {
			builder = factory.newDocumentBuilder();
			
			Document doc = builder.parse(xmlFile);
			Element root = doc.getDocumentElement();
			NodeList cases = root.getElementsByTagName("case");
			//存放case的map
			LinkedHashMap<String, TestCase> caseMap = new LinkedHashMap<String, TestCase>();
			Element child;
			TestCase testCase;
			
			//逐个解析xml中的case元素
			for (int i = 0; i < cases.getLength(); i++) {
				child = (Element) cases.item(i);
				testCase = parseTestCase(child);
				
				if (testCase == null)
					continue;
				
				if (caseMap.containsKey(testCase.getId()))
					throw new RuntimeException("存在多个" + testCase.getId() + "，请检查id配置");
				else
					caseMap.put(testCase.getId(), testCase);
			}
			
			testUnit = new TestUnit();
			testUnit.setCaseMap(caseMap);		
		} catch (Exception e) {
			e.printStackTrace();
		}

		return testUnit;
	}

	/**
	 * <br>将case元素解析为TestCase实例，也就是一个TestStep的集合</br>
	 *
	 * @param element 一个case元素
	 * @return
	 */
	private static TestCase parseTestCase(Element element) {
		if (element == null)
			return null;
		
		NamedNodeMap attrs = element.getAttributes();
		//根据case的属性实例化TestCase，并注入相应字段值
		TestCase testCase = initByAttributes(attrs, new TestCase());
		
		NodeList stepNodes = element.getElementsByTagName("step");
		int len = stepNodes.getLength();
		List<TestStep> stepList = new ArrayList<TestStep>(len);
		
		Element stepNode;
		//逐个解析case元素的step子元素
		for (int i = 0; i < len; i++) {
			stepNode = (Element) stepNodes.item(i);
			stepList.add(parseTestStep(stepNode));
		}
		testCase.setSteps(stepList);
		
		return testCase;
	}

	/**
	 * <br>解析step元素为TestStep实例</br>
	 *
	 * @param element
	 * @return
	 */
	private static TestStep parseTestStep(Element element) {
		if (element == null)
			return null;
		
		TestStep testStep = initByAttributes(element.getAttributes(), new TestStep());
		testStep.setAndroidDriver(driver);
		
		return testStep;
	}

	/**
	 * <br>根据xml文件中的元素属性为对象的对应字段注入值</br>
	 *
	 * @param attrs
	 * @param t 需要实例化并注入字段值的对象
	 * @return
	 */
	private static <T> T initByAttributes(NamedNodeMap attrs, T t) {
		if (attrs == null || attrs.getLength() == 0)
			return t;
		
		int len = attrs.getLength();
		Node attr;
		String name, value;
		
		//通过反射逐个注入字段值
		for (int i = 0; i < len; i++) {
			attr = attrs.item(i);
			if (attr == null) continue;
			
			name = attr.getNodeName();
			value = attr.getNodeValue();
			//通过反射为对象的对应字段注入值
			initByReflect(name, value, t);
		}
		return t;
	}

	/**
	 * <br>通过反射为对象的对应字段注入值</br>
	 *
	 * @param name
	 * @param value
	 * @param t
	 * @return
	 */
	private static <T> T initByReflect(String name, String value, T t) {
		if (t == null)
			throw new RuntimeException("null object");
		if (name == null || "".equals(name))
			throw new RuntimeException("empty name");
		
		Class<?> clazz = t.getClass();
		Method setter, getter;

		try {
			String methodStr = name.substring(0, 1).toUpperCase() + name.substring(1);
			
			//如果名称是cancel，则调用isCancel()方法，主要是为了语义上的直观
			getter = clazz.getMethod(("cancel".equals(name) ? "is" : "get") + methodStr, new Class<?>[] {});
			setter = clazz.getMethod("set" + methodStr, getter.getReturnType());
			
			if ("action".equals(name))
				//根据StepAction类中的map来获取名称对应的StepAction（枚举）实例
				setter.invoke(t, StepAction.action(value));
			else if ("cancel".equals(name))
				setter.invoke(t, "true".equals(value) ? true : false);
			else if("details".equals(name))
				setter.invoke(t,parseDetail(value));
			else
				setter.invoke(t, value);
		} catch (Exception e) {
//			System.out.println("对象反射初始化失败");
//			e.printStackTrace();
		}
		return t;
	}
	
	/**
	 * <br>解析行为的细节描述为map</br>
	 *
	 * @author    102051
	 * @date      2017年7月28日 上午11:01:14
	 * @param detail
	 * @return
	 */
	public static Map<String,String> parseDetail(String detail){
		HashMap<String,String> map = new HashMap<>();
		String[] strarr = detail.split(";");
		
		for(String str : strarr){
			map.put(str.split(":")[0], str.split(":")[1]);
		}
		return map;
	}
	
	/**
	 * <br>执行完毕,退出App程序</br>
	 */
	public static void QuitApp() {
		try {
			driver.quit();
			System.out.println("『测试结束』开始执行: <结束进程,退出App>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
