
package name.abhijitsarkar.webservices.jaxws.calculator.contractfirst.client.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for addDefaultBareRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addDefaultBareRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="param1" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="param2" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addDefaultBareRequest", propOrder = {
    "param1",
    "param2"
})
public class AddDefaultBareRequest {

    protected float param1;
    protected float param2;

    /**
     * Gets the value of the param1 property.
     * 
     */
    public float getParam1() {
        return param1;
    }

    /**
     * Sets the value of the param1 property.
     * 
     */
    public void setParam1(float value) {
        this.param1 = value;
    }

    /**
     * Gets the value of the param2 property.
     * 
     */
    public float getParam2() {
        return param2;
    }

    /**
     * Sets the value of the param2 property.
     * 
     */
    public void setParam2(float value) {
        this.param2 = value;
    }

}
