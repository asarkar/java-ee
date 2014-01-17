package name.abhijitsarkar.webservices.jaxws.tools;

import org.gradle.api.Plugin
import org.gradle.api.Project

class JAXWSPlugin implements Plugin<Project> {
	@Override
	public void apply(Project project) {
		project.extensions.create("jaxWsPlugin", JAXWSPluginExtension, project)

		project.task("wsimport", type: WSImportTask) {
			conventionMapping.WSDLToJavaClass = { project.jaxWsPlugin.getWSDLToJavaClass() }
			conventionMapping.classpath = { project.jaxWsPlugin.getClasspath() }
			conventionMapping.args = { project.jaxWsPlugin.createArgsList() }

			conventionMapping.wsdlDir = { project.jaxWsPlugin.getWsdlDir() }
			conventionMapping.wsdlFiles = { project.jaxWsPlugin.getWsdlFiles() }
			conventionMapping.wsdlUrls = {
				project.jaxWsPlugin.initWsdlUrls()
			}
		}
	}
}
