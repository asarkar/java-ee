package com.github.mkalin.jwsur2.ch6.tempconvert;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class TempConvert {
	@WebMethod
	public float c2f(float t) {
		return 32.0f + (t * 9.0f / 5.0f);
	}

	@WebMethod
	public float f2c(float t) {
		return (5.0f / 9.0f) * (t - 32.0f);
	}
}
