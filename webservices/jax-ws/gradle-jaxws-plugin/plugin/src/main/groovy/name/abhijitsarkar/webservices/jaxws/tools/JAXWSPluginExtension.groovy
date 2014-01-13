package name.abhijitsarkar.webservices.jaxws.tools;

import org.gradle.api.Project
import org.gradle.api.file.ConfigurableFileTree
import org.gradle.api.tasks.Optional

class JAXWSPluginExtension {
	final Project project

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

	public String getWsdlDir() {
		return wsdlDir
	}

	public String[] getWsdlFiles() {
		return wsdlFiles
	}

	public String getSourceDestDir() {
		return sourceDestDir
	}

	public String getPackageName() {
		return packageName
	}

	public String getStaleFile() {
		return staleFile
	}

	public float getTarget() {
		return target
	}

	public boolean isVerbose() {
		return verbose
	}

	public String getWsdlLocation() {
		return wsdlLocation
	}

	public List<String> getWsdlUrls() {
		return wsdlUrls
	}

	public String getEncoding() {
		return encoding
	}

	void initParams() {
		initWsdlUrls()
	}

	private void initWsdlUrls() {
		if (wsdlUrls != null && wsdlUrls.size() > 0) {
			return;
		}

		if (wsdlFiles != null && wsdlFiles.size() != 0) {
			initWsdlUrlsBasedOnWsdlFiles()
		} else {
			initWsdlUrlsByFilteringOnWsdlDir()
		}
	}

	private void initWsdlUrlsBasedOnWsdlFiles() {
		wsdlFiles.each {
			project.logger.debug("Adding " + it + " to wsdlUrls.")

			wsdlUrls.add(JAXWSPluginExtension.getURLAsString(new File(wsdlDir, it)))
		}
	}

	private void initWsdlUrlsByFilteringOnWsdlDir() {
		assert wsdlDir != null, "One of wsdlDir or wsdlFiles must be specified."

		project.logger.debug("No wsdlFiles specified...wsdlDir is scanned for all .wsdl files.")
		
		final ConfigurableFileTree wsdlRootDir = project.fileTree(wsdlDir)

		wsdlRootDir.include("**/*.wsdl")

		wsdlRootDir.visit {
			project.logger.debug("Adding " + it + " to wsdlUrls.")

			wsdlUrls.add(getURLAsString(it.file))
		}
	}

	private static String getURLAsString(File f) {
		f.toURI().toURL().toString()
	}
}