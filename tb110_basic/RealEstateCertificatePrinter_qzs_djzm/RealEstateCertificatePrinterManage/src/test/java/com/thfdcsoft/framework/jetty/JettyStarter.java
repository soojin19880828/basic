package com.thfdcsoft.framework.jetty;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.eclipse.jetty.server.Server;

public class JettyStarter {
	
	public static final int PORT = 28090;
	public static final int SSL_PORT = 28443;
	public static final String CONTEXT = "/";
	static String hostName = null;
	static {
		InetAddress inet;
		try {
			inet = InetAddress.getLocalHost();
			hostName = inet.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		if (hostName == null) {
			hostName = "localhost";
		}
	}
	public static final String URL = "http://" + hostName + ":" + PORT
			+ CONTEXT;
	public static final String SSL_URL = "https://" + hostName + ":" + SSL_PORT
			+ CONTEXT;
	public static final String[] TLD_JAR_NAMES = new String[] { "spring-webmvc" };

	public static void main(String[] args) {

		// true:使用eclipse jdt
		// false:jdk1.6以上使用jstl
		System.setProperty("org.apache.jasper.compiler.disablejsr199", "false");

		// 启动Jetty
		Server server = JettyFactory.createServer(PORT, SSL_PORT, CONTEXT);
		JettyFactory.setTldJarNames(server, TLD_JAR_NAMES);

		try {
			server.start();

			System.out.println("Server running at " + URL);
			System.out.println("And SSL Server running at " + SSL_URL);
			System.out.println("Hit Enter to reload the application");

			// 等待用户输入回车重载应用.
			while (true) {
				char c = (char) System.in.read();
				if (c == '\n') {
					JettyFactory.reloadContext(server);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
