package name.abhijitsarkar.webservices.jaxws.tools;

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

class WSImportTaskTest {
	private final Project project
	//	private static final String userHome = System.getProperty("user.home")
	//	private static final String GRADLE_INSTALLATION = userHome + File.separator +
	//	"Applications" + File.separator + "gradle-default"
	//	private static final String GRADLE_USER_HOME = userHome + File.separator + "Repositories" +
	//	File.separator + "gradle"

	WSImportTaskTest() {
		ProjectBuilder projectBuilder = ProjectBuilder.builder()
		projectBuilder.withName("jaxws-plugin")
		projectBuilder.withProjectDir(new File("."))

		project = projectBuilder.build()

		//		project.sourceCompatibility = 1.7
		//		project.group = "name.abhijitsarkar.webservices.jaxws.tools"
		//		project.version = "1.0-SNAPSHOT"
		//
		//		project.apply plugin: "groovy"
		//
		//		project.repositories {
		//			mavenCentral()
		//			mavenLocal()
		//		}
		//
		//		def cxfVersion = "2.7.8"
		//
		//		project.dependencies {
		//			delegate.compile gradleApi()
		//			delegate.compile group: "org.apache.cxf", name: "cxf-tools-wsdlto-core", version: "$cxfVersion"
		//		}

		project.task(type: WSImportTask, "wsimport")
	}

	@Test
	public void testWsimportTaskAdded() {
		assert project.tasks.findByName("wsimport") != null
	}

	//	@Test
	//	public void testConfigure() {
	//		Task wsimport = project.tasks.findByName("wsimport")
	//
	//		assert wsimport.WSDLToJavaClass == WSDLToJava.class
	//		assert !wsimport.classpath.isEmpty()
	//		assert wsimport.args.isEmpty()
	//
	//		testInitWsdlUrls(wsimport);
	//	}

	//	@Test
	//	public void testWsimport() {
	//		ProjectConnection connection = newProjectConnection();
	//
	//		try {
	//			connection.newBuild().forTasks("WSImportTask").run();
	//		} finally {
	//			connection.close();
	//		}
	//	}

	//	private ProjectConnection newProjectConnection() {
	//		GradleConnector.newConnector()
	//				.forProjectDirectory(new File("."))
	//				.useInstallation(new File(GRADLE_INSTALLATION))
	//				.useGradleUserHomeDir(new File(GRADLE_USER_HOME))
	//				.connect();
	//	}

	//	private void testInitWsdlUrls(Task wsimport) {
	//		String wsdlFile = new File(project.rootDir, "src/test/resources").toURI().toURL().toString() +
	//				"AWSECommerceService.wsdl"
	//
	//		List<String> wsdlUrls = wsimport.wsdlUrls
	//
	//		assert wsdlUrls != null
	//		assert wsdlUrls.size() == 1
	//		assert wsdlUrls[0].toString() == wsdlFile
	//	}
}