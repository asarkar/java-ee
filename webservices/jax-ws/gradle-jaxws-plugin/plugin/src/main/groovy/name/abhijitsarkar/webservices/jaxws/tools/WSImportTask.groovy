package name.abhijitsarkar.webservices.jaxws.tools;

import org.gradle.api.DefaultTask
import org.gradle.api.Task
import org.gradle.api.file.ConfigurableFileTree
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.TaskAction

class WSImportTask extends DefaultTask {
	Class<?> WSDLToJavaClass
	FileCollection classpath
	List<String> args = new ArrayList<>()
	String wsdlDir
	String[] wsdlFiles
	List<String> wsdlUrls = new ArrayList<>()

	WSImportTask() {
		description "Generates Java artifacts from WSDL."
	}

//	@Override
//	Task configure(Closure c) {
//		c.delegate = this;
//		c.call();
//
//		initWsdlUrls();
//	}

	void initWsdlUrls() {
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

			wsdlUrls.add(WSImportTask.getURLAsString(new File(wsdlDir, it)))
		}
	}

	private void initWsdlUrlsByFilteringOnWsdlDir() {
		assert wsdlDir != null, "One of wsdlDir or wsdlFiles must be specified."

		project.logger.debug("No wsdlFiles specified...wsdlDir is scanned for all .wsdl files.")

		final ConfigurableFileTree wsdlRootDir = project.fileTree(wsdlDir)

		wsdlRootDir.include("**/*.wsdl")

		wsdlRootDir.visit {
			project.logger.debug("Adding " + it + " to wsdlUrls.")

			wsdlUrls.add(WSImportTask.getURLAsString(it.file))
		}
	}

	private static String getURLAsString(File f) {
		f.toURI().toURL().toString()
	}

	@TaskAction
	public void wsimport() {
//		printDebugInfo();

		wsdlUrls.each { String wsdlUrl ->
			project.javaexec {
				setMain(WSDLToJavaClass.name)
				setClasspath(classpath)

				List<String> argsPlusWsdlUrl = args.plus(wsdlUrl)

				project.logger.info("argsPlusWsdlUrl: " + argsPlusWsdlUrl)
				//				project.logger.debug("classpath: " + classpath.getAsPath())

				setArgs(argsPlusWsdlUrl)
			}
		}
	}

	private printDebugInfo() {
		project.logger.debug("WSDLToJavaClass: " + WSDLToJavaClass.name)
//		project.logger.debug("classpath: \n")
//		classpath.each {
//			project.logger.debug(it + " ")
//		}
		project.logger.debug("args: " + args)
		project.logger.debug("wsdlDir: " + wsdlDir)
		project.logger.debug("wsdlFiles: " + wsdlFiles)
		project.logger.debug("wsdlUrls: " + wsdlUrls)
	}
}