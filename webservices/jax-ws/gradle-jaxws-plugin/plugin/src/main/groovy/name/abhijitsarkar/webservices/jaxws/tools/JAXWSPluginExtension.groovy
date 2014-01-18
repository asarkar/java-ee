package name.abhijitsarkar.webservices.jaxws.tools;

import org.apache.cxf.tools.wsdlto.WSDLToJava
import org.gradle.api.Project
import org.gradle.api.file.ConfigurableFileTree
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.Optional
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class JAXWSPluginExtension {
	private final Project project
	private static final Logger logger = LoggerFactory.getLogger("JAXWSPluginExtension")

	@Optional Class<?> WSDLToJavaClass = WSDLToJava.class
	@Optional String wsdlDir = new File(project.rootDir, "src/main/resources").absolutePath
	@Optional String[] wsdlFiles
	@Optional String sourceDestDir = project.buildDir.absolutePath
	@Optional String packageName
	@Optional String staleFile = new File(project.buildDir, "jaxws/stale").absolutePath
	@Optional float target = 2.1f
	@Optional boolean verbose = false
	@Optional String wsdlLocation
	@Optional List<String> wsdlUrls = new ArrayList<String>()
	@Optional String encoding = "UTF-8"

	JAXWSPluginExtension(final Project project) {
		this.project = project
	}

	Class<?> getWSDLToJavaClass() {
		WSDLToJavaClass
	}

	FileCollection getClasspath() {
		project.sourceSets.main.runtimeClasspath
	}

	String getWsdlDir() {
		wsdlDir
	}

	String[] getWsdlFiles() {
		wsdlFiles
	}

	String getSourceDestDir() {
		sourceDestDir
	}

	String getPackageName() {
		packageName
	}

	String getStaleFile() {
		staleFile
	}

	float getTarget() {
		target
	}

	boolean isVerbose() {
		verbose
	}

	String getWsdlLocation() {
		wsdlLocation
	}

	String getEncoding() {
		encoding
	}

	List<String> createArgsList() {
		List<String> args = new ArrayList<String>()

		args.add("-d")
		args.add(sourceDestDir)

		if (packageName != null) {
			args.add("-p")
			args.add(packageName)
		}

		if (verbose) {
			args.add("-verbose")
		}

		args
	}

	List<String> initWsdlUrls() {
		if (wsdlUrls != null && wsdlUrls.size() > 0) {
			wsdlUrls;
		}

		if (wsdlFiles != null && wsdlFiles.size() > 0) {
			initWsdlUrlsBasedOnWsdlFiles()
		} else {
			initWsdlUrlsByFilteringOnWsdlDir()
		}
	}

	private List<String> initWsdlUrlsBasedOnWsdlFiles() {
		wsdlFiles.each { wsdlURL ->
			logger.debug("Adding " + wsdlURL + " to wsdlUrls.")

			logger.debug("wsdlFiles: " + wsdlFiles.toString())
			logger.debug("wsdlUrls: " + wsdlUrls.toString())

			wsdlUrls.add(JAXWSPluginExtension.getURLAsString(new File(wsdlDir, wsdlURL)))
		}

		wsdlUrls
	}

	private List<String> initWsdlUrlsByFilteringOnWsdlDir() {
		assert wsdlDir != null, "One of wsdlDir or wsdlFiles must be specified."

		logger.debug("No wsdlFiles specified...wsdlDir is scanned for all .wsdl files.")

		final ConfigurableFileTree wsdlRootDir = project.fileTree(wsdlDir)

		wsdlRootDir.include("**/*.wsdl")

		wsdlRootDir.visit { wsdlURL ->
			logger.debug("Adding " + wsdlURL + " to wsdlUrls.")

			wsdlUrls.add(JAXWSPluginExtension.getURLAsString(wsdlURL.file))
		}

		wsdlUrls
	}

	private static String getURLAsString(File f) {
		f.toURI().toURL().toString()
	}
}