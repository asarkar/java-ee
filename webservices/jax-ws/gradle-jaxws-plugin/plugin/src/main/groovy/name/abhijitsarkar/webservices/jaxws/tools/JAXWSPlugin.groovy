package name.abhijitsarkar.webservices.jaxws.tools;

import org.apache.cxf.tools.wsdlto.WSDLToJava
import org.gradle.api.Plugin
import org.gradle.api.Project

class JAXWSPlugin implements Plugin<Project> {
	@Override
	public void apply(Project project) {
		project.extensions.create("jaxWsPlugin", JAXWSPluginExtension, project)

		project.task("wsimport", type: WSImportTask) << {
			delegate.WSDLToJavaClass = WSDLToJava.class
			delegate.classpath = project.sourceSets.main.runtimeClasspath
			delegate.args = createArgsList(project.jaxWsPlugin)

			delegate.wsdlDir = project.jaxWsPlugin.wsdlDir
			delegate.wsdlFiles = project.jaxWsPlugin.wsdlFiles
			delegate.wsdlUrls = project.jaxWsPlugin.wsdlUrls
			
			delegate.initWsdlUrls()

//			println "apply - project.jaxWsPlugin.class: " + project.jaxWsPlugin.class.name
			println "apply - delegate.class: " + delegate.class.name
			println "apply - args: " + delegate.args
			println "apply - wsdlDir: " + delegate.wsdlDir
			println "apply - wsdlFiles: " + delegate.wsdlFiles
			println "apply - wsdlUrls: " + delegate.wsdlUrls
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
