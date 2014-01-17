package name.abhijitsarkar.webservices.jaxws.tools;

import org.gradle.api.DefaultTask
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.TaskAction
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class WSImportTask extends DefaultTask {
	private static final Logger logger = LoggerFactory.getLogger("WSImportTask")

	Class<?> WSDLToJavaClass
	FileCollection classpath
	List<String> args
	String wsdlDir
	String[] wsdlFiles
	List<String> wsdlUrls

	public WSImportTask() {
		description "Generates Java artifacts from WSDL."
	}

	public Class<?> getWSDLToJavaClass() {
		return WSDLToJavaClass
	}

	public setWSDLToJavaClass(Class<?> WSDLToJavaClass) {
		this.WSDLToJavaClass = WSDLToJavaClass
	}

	public FileCollection getClasspath() {
		return classpath
	}

	public setClasspath(FileCollection classpath) {
		this.classpath = classpath
	}

	public List<String> getArgs() {
		return args
	}

	public void setArgs(List<String> args) {
		this.args = args
	}

	public String getWsdlDir() {
		return wsdlDir
	}

	public void setWsdlDir(String wsdlDir) {
		this.wsdlDir = wsdlDir
	}

	public String[] getWsdlFiles() {
		return wsdlFiles
	}

	public void setWsdlFiles(String[] wsdlFiles) {
		this.wsdlFiles = wsdlFiles
	}

	public List<String> getWsdlUrls() {
		return wsdlUrls
	}

	public void setWsdlUrls(List<String> wsdlUrls) {
		this.wsdlUrls = wsdlUrls
	}

	@TaskAction
	public void wsimport() {
		getWsdlUrls().each { String wsdlUrl ->
			project.javaexec {
				delegate.main = this.getWSDLToJavaClass().name
				delegate.classpath = this.getClasspath()

				List<String> argsPlusWsdlUrl = this.getArgs().plus(wsdlUrl)

				logger.info("argsPlusWsdlUrl: " + argsPlusWsdlUrl)

				delegate.args = argsPlusWsdlUrl
			}
		}
	}
}