package com.thfdcsoft.framework.jetty;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class JettyFactory {

	/** newSystemResource */
	private static final String SYSTEM_RESOURCE = "jetty/jetty.xml";

	/** setKeyStorePath */
	private static final String KEY_STORE_PATH = "src/test/resources/jetty/thfdcsoft.keystore";

	/** setKeyStorePassword */
	private static final String KEY_STORE_PASSWORD = "123456";

	/** setKeyManagerPassword */
	private static final String KEY_MANAGER_PASSWORD = "123456";

	/** WebAppContext */
	private static final String WEB_APP_CONTEXT = "src/main/webapp";

	/** setDefaultsDescriptor */
	private static final String DEFAULTS_DESCRIPTOR = "jetty/webdefault-windows.xml";

	private static final Logger logger = LoggerFactory.getLogger(JettyFactory.class);

	public static Server createServer(int port, int sslPort, String ctxPath) {
		Server server = new Server();
		/** 读取Jetty配置文件 */
		try {
			Resource jettyConfig = Resource.newSystemResource(SYSTEM_RESOURCE);
			XmlConfiguration configuration = new XmlConfiguration(jettyConfig.getInputStream());
			server = (Server) configuration.configure();
			server.setStopAtShutdown(true);
		} catch (Exception e) {
			logger.error("启动失败：Jetty配置文件：" + SYSTEM_RESOURCE + "异常！");
		}
		/** 设置普通Http连接 */
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(port);
		connector.setReuseAddress(false);
		server.addConnector(connector);
		/** 设置加密Https连接 */
		SslContextFactory sslCtxFactory = new SslContextFactory();
		sslCtxFactory.setKeyStorePath(KEY_STORE_PATH);
		sslCtxFactory.setKeyStorePassword(KEY_STORE_PASSWORD);
		sslCtxFactory.setKeyManagerPassword(KEY_MANAGER_PASSWORD);
		ServerConnector sslConnector = new ServerConnector(server, sslCtxFactory);
		sslConnector.setPort(sslPort);
		sslConnector.setReuseAddress(false);
		server.addConnector(sslConnector);
		
		/** 设置web应用目录 */
		WebAppContext webContext = new WebAppContext(WEB_APP_CONTEXT, ctxPath);
		webContext.setDefaultsDescriptor(DEFAULTS_DESCRIPTOR);
		server.setHandler(webContext);

		return server;
	}
	
	/**
	 * 设置除jstl-*.jar外其他含tld文件的jar包的名称. jar名称不需要版本号，如sitemesh, shiro-web
	 */
	public static void setTldJarNames(Server server, String... jarNames) {
		WebAppContext context = (WebAppContext) server.getHandler();
		List<String> jarNameExprssions = Lists.newArrayList(
				".*/jstl-[^/]*\\.jar$", ".*/.*taglibs[^/]*\\.jar$");
		for (String jarName : jarNames) {
			jarNameExprssions.add(".*/" + jarName + "-[^/]*\\.jar$");
		}
		context.setAttribute(
				"org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
				StringUtils.join(jarNameExprssions, '|'));
	}

	/**
	 * 快速重新启动application，重载target/classes与target/test-classes.
	 */
	public static void reloadContext(Server server) throws Exception {
		WebAppContext context = (WebAppContext) server.getHandler();

		System.out.println("Application reloading");
		context.stop();

		WebAppClassLoader classLoader = new WebAppClassLoader(context);
		classLoader.addClassPath("target/classes");
		classLoader.addClassPath("target/test-classes");
		context.setClassLoader(classLoader);

		context.start();

		System.out.println("Application reloaded");
	}
}
