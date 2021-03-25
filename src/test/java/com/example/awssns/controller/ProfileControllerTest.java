package com.example.awssns.controller;

import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ProfileControllerTest {
    @Test
    public void real_profile_되() {
        //given
        String 		expectedProfile = "real";
        MockEnvironment env 		= new MockEnvironment();

        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("oauth");
        env.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(env);

        //when
        String profile = controller.profile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    public void real_profile_이건되() {
        //given
        String 		expectedProfile = "oauth";
        MockEnvironment env 		= new MockEnvironment();

        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(env);

        //when
        String profile = controller.profile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    public void active_profile_이_default거() {
        //given
        String 			expectedProfile = "default";
        MockEnvironment 	env 		= new MockEnvironment();
        ProfileController 	controller 	= new ProfileController(env);

        //when
        String profile = controller.profile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }
}
