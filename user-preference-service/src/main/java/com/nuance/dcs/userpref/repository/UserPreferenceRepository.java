package com.nuance.dcs.userpref.repository;

import static com.nuance.dcs.userpref.repository.UserPreferenceAttributes.PARTNER_ID;
import static com.nuance.dcs.userpref.repository.UserPreferenceAttributes.PASSWORD;
import static com.nuance.dcs.userpref.repository.UserPreferenceAttributes.PREFERENCE_NAME;
import static com.nuance.dcs.userpref.repository.UserPreferenceAttributes.SERVICE_ID;
import static com.nuance.dcs.userpref.repository.UserPreferenceAttributes.USERNAME;

import java.util.List;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.nuance.dcs.userpref.UserPreference;

public interface UserPreferenceRepository
	extends CrudRepository<UserPreference, Long> {
    @Procedure("UserPreference.get")
    List<UserPreference> get(@Param(USERNAME) String username,
	    @Param(PASSWORD) String password, @Param(SERVICE_ID) int serviceId,
	    @Param(PREFERENCE_NAME) String preferenceName,
	    @Param(PARTNER_ID) String partnerId);
}
