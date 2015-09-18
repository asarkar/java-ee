package com.nuance.dcs.userpref.repository;

import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.nuance.dcs.userpref.UserPreference;
import com.nuance.dcs.userpref.UserPreferenceApp;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringApplicationConfiguration(classes = UserPreferenceApp.class)
public class UserPreferenceRepositoryTest {
    private String username = "x01bundle@gmail.com";
    private String partnerId = "4DE68703-5FE3-4477-8D18-0A63B452D534";
    private String preferenceName = "LOCATION";
    private int serviceId = 9;
    private String data = "Portland";

    @Autowired
    private UserPreferenceRepository repo;

    @Test
    public void testThatInvokesWithoutError() {
	List<UserPreference> userPreferences = repo.get(username, "", serviceId,
		preferenceName, partnerId);

	assertFalse(CollectionUtils.isEmpty(userPreferences));
	userPreferences.forEach(x -> System.out.println("***" + x + "***"));
    }
}
