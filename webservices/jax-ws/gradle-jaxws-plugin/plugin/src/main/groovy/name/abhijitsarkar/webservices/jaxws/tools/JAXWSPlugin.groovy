package name.abhijitsarkar.webservices.jaxws.tools;

import org.apache.cxf.tools.wsdlto.WSDLToJava
import org.gradle.api.Plugin
import org.gradle.api.Project

class JAXWSPlugin implements Plugin<Project> {
	@Override
	public void apply(Project project) {
		project.extensions.create("jaxWsPlugin", JAXWSPluginExtension, project)

		project.task("wsimport", type: WSImportTask) {
			WSDLToJavaClass = WSDLToJava.class
			classpath = project.sourceSets.main.runtimeClasspath
			args = createArgsList(project.jaxWsPlugin)

			doFirst {
				project.jaxWsPlugin.initParams()
			}
		}
	}

	private List<String> createArgsList(JAXWSPluginExtension params) {
		List<String> args = new ArrayList<String>()

		args.add("-d")
		args.add(params.getSourceDestDir())

		if (params.getPackageName() != null) {
			args.add("-p")
			args.add(params.getPackageName())
		}

		if (params.isVerbose()) {
			args.add("-verbose")
		}

		args
	}
}
