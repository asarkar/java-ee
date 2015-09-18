package com.nuance.dcs.userpref;

import static com.google.common.base.Functions.forMap;
import static java.util.Arrays.asList;

import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

public enum UserPreferenceQueryStatus {
    SUCCESS(0), INTERNAL_ERROR(1), USER_NOT_FOUND(7), PARTNER_NOT_FUND(
	    213), USER_PREF_NOT_FOUND(208), INVALID_PARAM(4);

    private static final Function<Integer, UserPreferenceQueryStatus> CODE_MAPPER;

    static {
	UserPreferenceQueryStatusToCode userPrefQueryStatusToCode = new UserPreferenceQueryStatusToCode();

	Map<Integer, UserPreferenceQueryStatus> codeMap = FluentIterable
		.from(asList(UserPreferenceQueryStatus.values()))
		.uniqueIndex(userPrefQueryStatusToCode);
	CODE_MAPPER = forMap(codeMap, INTERNAL_ERROR);
    }

    private int code;

    private UserPreferenceQueryStatus(int status) {
	this.code = status;
    }

    public int getCode() {
	return code;
    }

    public static UserPreferenceQueryStatus findByStatusCode(int code) {
	return CODE_MAPPER.apply(code);
    }

    private static final class UserPreferenceQueryStatusToCode
	    implements Function<UserPreferenceQueryStatus, Integer> {
	@Override
	public Integer apply(UserPreferenceQueryStatus input) {
	    return input.getCode();
	}
    }
}
