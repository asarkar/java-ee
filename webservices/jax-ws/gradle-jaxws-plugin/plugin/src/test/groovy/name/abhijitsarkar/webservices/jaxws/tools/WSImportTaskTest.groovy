package name.abhijitsarkar.webservices.jaxws.tools;

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

class WSImportTaskTest {
	private final Project project

	WSImportTaskTest() {
		ProjectBuilder projectBuilder = ProjectBuilder.builder()
		projectBuilder.withName("jaxws-plugin")
		projectBuilder.withProjectDir(new File("."))

		project = projectBuilder.build()

		project.task(type: WSImportTask, "wsimport")
	}

	@Test
	public void testWsimportTaskAdded() {
		assert project.tasks.findByName("wsimport") != null
	}
}