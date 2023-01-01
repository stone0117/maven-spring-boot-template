package com.stone.web.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.net.InetAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
* Created by stone on 2022/03/25
*
* @author stone
*
*/
/**
* spring-boot 启动完毕 能接受请求之后触发 完美
*/
@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

@Autowired
private Environment    environment;
@Autowired
private ServletContext servletContext;
private String         hostAddress;
private String         port;
private String         contextPath;
private String         ipPort;

/**
* This event is executed as late as conceivably possible to indicate that
* the application is ready to service requests.
* 可能会尽早执行此事件，以指示
* 该应用程序已准备就绪，可以处理请求。
*/
@Override
public void onApplicationEvent(final ApplicationReadyEvent event) {

// CustomReflect.getAllMethodResultWithRecursion(event, "event");

ConfigurableApplicationContext applicationContext = event.getApplicationContext();

if (applicationContext instanceof AnnotationConfigServletWebServerApplicationContext) {
// CustomReflect.getAllMethodResultWithRecursion(servletContext, "servletContext");
// event.getApplicationContext().getWebServer().getTomcat()
// {
//   Object webServer = SNReflect.call(applicationContext, "getWebServer");
//   Object tomcat    = SNReflect.call(webServer, "getTomcat");
//   Object connector = SNReflect.call(tomcat, "getConnector");
//   Object service   = SNReflect.call(connector, "getService");
//   Object server    = SNReflect.call(service, "getServer");
//   Object localPort = SNReflect.call(connector, "getLocalPort");
//   Object port      = SNReflect.call(connector, "getPort");
//   Object address   = SNReflect.call(server, "getAddress");
//   dlog(address, "address");
//   dlog(localPort, "localPort");
//   dlog(port, "port");
//   // CustomReflect.getAllMethodResultWithRecursion(tomcat, "tomcat");
// }
// tomcat.getConnector().getService().getServer().getAddress()  = ↓
// localhost
// tomcat.getConnector().getLocalPort()
// 8081
// tomcat.getConnector().getPort()  = ↓
// 8081
try {
hostAddress = InetAddress.getLocalHost().getHostAddress();
} catch (Exception e) {
hostAddress = "localhost";
}
try {
Object webServer = applicationContext.getClass().getMethod("getWebServer").invoke(applicationContext);
port = webServer.getClass().getMethod("getPort").invoke(webServer).toString();
} catch (Exception e) {
port = "8080";
}

contextPath = servletContext.getContextPath();

ipPort = String.format("%s:%s", hostAddress, port);
//noinspection AlibabaThreadPoolCreation
ExecutorService executor = Executors.newFixedThreadPool(2, r -> {
Thread thread = new Thread(r);
thread.setDaemon(false);
return thread;
});

CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
try {
String cmd = String.format("open -na \"Google Chrome\" --args --incognito http://%s:%s%s", hostAddress, port, contextPath);
Process process = Runtime.getRuntime().exec(new String[]{
"/bin/bash",
"-c",
cmd
}, null, null);
process.waitFor();
return cmd;
} catch (Exception e) {
e.printStackTrace();
return "";
}
}, executor);
completableFuture.whenComplete((cmd, throwable) -> {
System.out.println("\u001b[0;7;48m " + cmd + " \u001b[0m");
System.out.println("\u001b[0;7;48m " + String.format("open -na \"Google Chrome\" --args --incognito http://%s:%s%s", "localhost", port, contextPath) + " \u001b[0m");
System.out.println("\u001b[0;7;48m " + String.format("open -na \"Google Chrome\" --args --incognito http://%s:%s%s", "127.0.0.1", port, contextPath) + " \u001b[0m");
});
executor.shutdown();

String[] activeProfiles = environment.getActiveProfiles();
System.out.println("\u001b[0;7;48m activeProfiles = " + String.join(",", activeProfiles) + " \u001b[0m");

}
}
}
