package name.abhijitsarkar.webservices.jaxws.tools;

import org.gradle.api.DefaultTask
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.TaskAction

class WSImportTask extends DefaultTask {
	Class<?> WSDLToJavaClass
	FileCollection classpath
	List<String> args

	WSImportTask() {
		description "Generates Java artifacts from WSDL."
	}

	@TaskAction
	public void wsimport() {
		project.jaxWsPlugin.wsdlUrls.each { String wsdlUrl ->
			project.javaexec {
				setMain(WSDLToJavaClass.name)
				setClasspath(this.classpath)

				List<String> argsWithWsdlUrl = this.args.plus(wsdlUrl)
				project.logger.info("args: " + args)

				setArgs(argsWithWsdlUrl)
			}
		}
	}
}