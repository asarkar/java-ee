package edu.certification.abhijitsarkar.ocewsd.ejb3;

import javax.ejb.Remote;

@Remote
public interface SecureBeanRemote {
	public String secureMethod();

	public String unsecureMethod();
}
