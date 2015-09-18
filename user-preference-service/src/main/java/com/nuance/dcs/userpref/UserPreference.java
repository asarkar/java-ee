package com.nuance.dcs.userpref;

import static com.nuance.dcs.userpref.repository.UserPreferenceAttributes.PARTNER_ID;
import static com.nuance.dcs.userpref.repository.UserPreferenceAttributes.PASSWORD;
import static com.nuance.dcs.userpref.repository.UserPreferenceAttributes.PREFERENCE_NAME;
import static com.nuance.dcs.userpref.repository.UserPreferenceAttributes.RESULTSET;
import static com.nuance.dcs.userpref.repository.UserPreferenceAttributes.SERVICE_ID;
import static com.nuance.dcs.userpref.repository.UserPreferenceAttributes.USERNAME;

import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.StoredProcedureParameter;

@Entity

@SqlResultSetMapping(name = "UserPreferences", entities = {
	@EntityResult(entityClass = UserPreference.class, fields = {
		@FieldResult(name = "data", column = RESULTSET) }) })

@NamedStoredProcedureQuery(name = "UserPreference.get", procedureName = "TE_GET_USER_PREFERENCES_SP", 
	resultSetMappings = "UserPreferences", resultClasses = UserPreference.class, parameters = {
	@StoredProcedureParameter(mode = ParameterMode.IN, name = USERNAME, type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.IN, name = PASSWORD, type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.IN, name = SERVICE_ID, type = Integer.class),
	@StoredProcedureParameter(mode = ParameterMode.IN, name = PREFERENCE_NAME, type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.IN, name = PARTNER_ID, type = String.class),
	@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = RESULTSET, type = void.class) })
public class UserPreference {
//    @Id
//    @GeneratedValue
    private Long id;

    @Id
//    @GeneratedValue
    private String data;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getData() {
	return data;
    }

    public void setData(String data) {
	this.data = data;
    }

    @Override
    public String toString() {
	return "UserPreference [id=" + id + ", data=" + data + "]";
    }
}
