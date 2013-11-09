
package com.github.mkalin.jwsur2.ch6.tempconvert.client.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for f2c complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="f2c">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "f2c", propOrder = {
    "arg0"
})
public class F2C {

    protected float arg0;

    /**
     * Gets the value of the arg0 property.
     * 
     */
    public float getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     */
    public void setArg0(float value) {
        this.arg0 = value;
    }

}
