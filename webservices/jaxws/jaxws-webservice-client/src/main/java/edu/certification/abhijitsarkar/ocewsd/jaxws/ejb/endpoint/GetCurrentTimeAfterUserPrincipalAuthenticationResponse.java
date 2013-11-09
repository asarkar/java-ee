
package edu.certification.abhijitsarkar.ocewsd.jaxws.ejb.endpoint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getCurrentTimeAfterUserPrincipalAuthenticationResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getCurrentTimeAfterUserPrincipalAuthenticationResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://endpoint.ejb.jaxws.ocewsd.abhijitsarkar.certification.edu/}time" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCurrentTimeAfterUserPrincipalAuthenticationResponse", propOrder = {
    "_return"
})
public class GetCurrentTimeAfterUserPrincipalAuthenticationResponse {

    @XmlElement(name = "return")
    protected Time _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link Time }
     *     
     */
    public Time getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link Time }
     *     
     */
    public void setReturn(Time value) {
        this._return = value;
    }

}
