package name.abhijitsarkar.webservices.jaxws.tools.client;

import org.gradle.tooling.GradleConnector
import org.gradle.tooling.ProjectConnection
import org.junit.Test

class JAXWSPluginClientTest {
	private static final String userHome = System.getProperty("user.home")
	private static final String GRADLE_INSTALLATION = userHome + File.separator +
	"Applications" + File.separator + "gradle-default"
	private static final String GRADLE_USER_HOME = userHome + File.separator + "Repositories" +
	File.separator + "gradle"

	@Test
	public void testWsimport() {
		ProjectConnection connection = newProjectConnection()

		try {
			connection.newBuild()
					.withArguments("--debug", "--stacktrace")
					.setStandardOutput(System.out)
					.setStandardError(System.out)
					.forTasks("wsimport")
					.run()
		} finally {
			connection.close()
		}
	}

	private ProjectConnection newProjectConnection() {
		println "new File('.') " + new File(".").absolutePath
		GradleConnector.newConnector()
				.forProjectDirectory(new File("."))
				.useInstallation(new File(GRADLE_INSTALLATION))
				.useGradleUserHomeDir(new File(GRADLE_USER_HOME))
				.connect()
	}
}