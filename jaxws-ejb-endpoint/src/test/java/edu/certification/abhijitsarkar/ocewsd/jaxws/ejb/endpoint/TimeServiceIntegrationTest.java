package edu.certification.abhijitsarkar.ocewsd.jaxws.ejb.endpoint;

//@RunWith(Arquillian.class)
//@Ignore
public class TimeServiceIntegrationTest {
	// @ArquillianResource
	// private URL baseUrl;
	//
	// private TimeServiceSEI proxy;
	//
	// public static final String WEBAPP_SRC = "src/main/webapp";
	//
	// QName serviceName = new QName(
	// "http://abhijitsarkar.certification.edu/ocewsd/jaxws/ejb/webservice",
	// "TimeService");
	//
	// @Deployment(testable = false)
	// public static Archive<?> deployment() {
	// final MavenDependencyResolver resolver = DependencyResolvers
	// .use(MavenDependencyResolver.class);
	// resolver.loadEffectivePom("pom.xml");
	//
	// WebArchive archive = ShrinkWrap
	// .create(WebArchive.class, "jaxws-ejb-webservice-1.0.war")
	// .addAsWebInfResource(
	// new File(WEBAPP_SRC, "WEB-INF/jboss-webservices.xml"))
	// .addAsWebInfResource(
	// new File(WEBAPP_SRC, "WEB-INF/jboss-ejb3.xml"))
	// .addAsResource(TimeService.class.getPackage(),
	// "handler-chain.xml")
	// .addAsLibraries(
	// resolver.artifact(
	// "edu.certification.abhijitsarkar.ocewsd:jaxws-utility")
	// .resolveAsFiles())
	// .addAsLibraries(
	// resolver.artifact("com.sun.xml.ws:jaxws-rt")
	// .resolveAsFiles())
	// .addAsLibraries(
	// resolver.artifact("com.sun.xml.parsers:jaxp-ri")
	// .resolveAsFiles())
	// .addPackages(true,
	// HttpBasicAuthenticationHandler.class.getPackage())
	// .addPackages(true, TimeServiceSEI.class.getPackage());
	// archive.toString(true);
	// return archive;
	// }
	//
	// @Before
	// public void setUp() throws MalformedURLException {
	// URL wsdlURL = new URL(baseUrl, "/jaxws-ejb-webservice/TimeService?wsdl");
	//
	// Service service = Service.create(wsdlURL, serviceName);
	// proxy = service.getPort(TimeServiceSEI.class);
	// }
	//
	// // java.lang.NoClassDefFoundError:
	// // com/sun/xml/messaging/saaj/soap/SOAPDocumentImpl which I've solved by
	// // placing libraries in server endorsed lib
	// public void testGetTimeWithoutUsernameAndPassord() throws Exception {
	// BindingProvider bp = (BindingProvider) proxy;
	// bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "user");
	// bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,
	// "abhijitsarkar");
	// setSoapAction("no-auth", bp);
	// Assert.assertEquals("Wrong time zone", TIME_ZONE, proxy
	// .getCurrentTime().getTimeZone().getID());
	// }
	//
	// private void setSoapAction(String soapAction, BindingProvider bp) {
	// bp.getRequestContext().put(BindingProvider.SOAPACTION_USE_PROPERTY,
	// true);
	// bp.getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY,
	// soapAction);
	// }
	//
	// private final static String TIME_ZONE = "America/New_York";
	//
}
