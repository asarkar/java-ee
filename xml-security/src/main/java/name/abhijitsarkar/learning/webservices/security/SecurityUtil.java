package name.abhijitsarkar.learning.webservices.security;

import java.io.File;
import java.io.FileInputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Collections;

import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class SecurityUtil {

	public static void print(File file) throws Exception {
		Transformer transformer = TransformerFactory.newInstance()
				.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.transform(new StreamSource(file), new StreamResult(
				System.out));
	}
}
