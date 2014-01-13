package name.abhijitsarkar.webservices.jaxws.tools;

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

class JAXWSPluginTest {
	private final Project project

	JAXWSPluginTest() {
		ProjectBuilder projectBuilder = ProjectBuilder.builder()
		projectBuilder.withName("jaxws-plugin")
		projectBuilder.withProjectDir(new File("."))

		project = projectBuilder.build()

		project.apply plugin: "groovy"
		project.apply plugin: name.abhijitsarkar.webservices.jaxws.tools.JAXWSPlugin
	}

	@Test
	public void testWsimportTaskAdded() {
		assert project.tasks.findByName("wsimport")
	}

	@Test
	public void testConfigureParams() {
		project.jaxWsPlugin {
			wsdlDir = new File(project.rootDir, "src/test/resources").absolutePath
			wsdlFiles = [
				"AWSECommerceService.wsdl"
			]

			initParams()
		}

		testInitWsdlUrls(project.jaxWsPlugin)
	}

	private void testInitWsdlUrls(JAXWSPluginExtension extn) {
		String wsdlFile = new File(project.rootDir, "src/test/resources").toURI().toURL().toString() +
				"AWSECommerceService.wsdl"

		List<String> wsdlUrls = extn.getWsdlUrls()

		assert wsdlUrls != null
		assert wsdlUrls.size() == 1
		assert wsdlUrls[0].toString() == wsdlFile
	}
}