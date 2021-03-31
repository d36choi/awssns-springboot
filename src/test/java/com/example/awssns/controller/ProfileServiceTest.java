package com.example.awssns.controller;

import com.example.awssns.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

public class ProfileServiceTest {
    @Test
    public void real_profile_real() {
        //given
        String 		expectedProfile = "real";
        MockEnvironment env 		= new MockEnvironment();

        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("oauth");
        env.addActiveProfile("real-db");
        ProfileService profileService = new ProfileService(env);

        //when
        String profile = profileService.getActiveProfile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    public void real_profile_oauth() {
        //given
        String 		expectedProfile = "oauth";
        MockEnvironment env 		= new MockEnvironment();
        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("real-db");
        ProfileService profileService = new ProfileService(env);

        //when
        String profile = profileService.getActiveProfile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    public void active_profile_default() {
        //given
        String 			expectedProfile = "default";
        MockEnvironment 	env 		= new MockEnvironment();
        ProfileService profileService = new ProfileService(env);

        //when
        String profile = profileService.getActiveProfile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }
}
