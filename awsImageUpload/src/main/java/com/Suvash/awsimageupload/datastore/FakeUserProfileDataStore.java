package com.Suvash.awsimageupload.datastore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.Suvash.awsimageupload.profile.UserProfile;


@Repository
public class FakeUserProfileDataStore {
	
	private static final List<UserProfile> USER_PROFILES = new ArrayList<>();
	
	static {	
		USER_PROFILES.add(new UserProfile(UUID.fromString("d39d6745-3747-4fb3-874a-009a111b62c1"), "Janet Jones", null));
		USER_PROFILES.add(new UserProfile(UUID.fromString("c968f045-3fc5-4e45-b2b3-0617b088d332"), "Antonio Junior", null));
	}

	public List<UserProfile> getUserProfiles(){
		return USER_PROFILES;
	}
}
