package com.f1soft.profileservice.repository;

import com.f1soft.profileservice.entities.Profile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;

import static com.f1soft.profileservice.utils.ProfileRequestUtils.getProfileInfo;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

/**
 * @author smriti on 7/9/19
 */
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class ProfileRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ProfileRepository profileRepository;

    @Test
    public void saveProfile() {

        Profile expected = new Profile(null, "Cogent123", "This is f1soft profile", 'Y',
                1L, 1L);

        Profile actual = testEntityManager.persist(expected);

        assertNotNull(actual);

        assertThat(expected).isEqualTo(actual);
    }

    public String queryToFindProfileByName() {
        return "SELECT * FROM profile p WHERE p.name='F1soft'";
    }

    @Test
    public void testFindByName() {

        Object result = testEntityManager.getEntityManager().createNativeQuery
                (queryToFindProfileByName()).getSingleResult();

        Profile profile = (Profile) testEntityManager.getEntityManager().createNativeQuery
                (queryToFindProfileByName(), Profile.class).getSingleResult();

        assertNotNull(result);

        assertThat(profile.getName()).isEqualTo(getProfileInfo().getName());
    }

    @Test
    public void fetchProfileByName_Should_Return_0() {
        BigInteger result = profileRepository.findProfileByName("F1soft");
        assertThat(result).isEqualTo(ZERO);
    }

    @Test
    public void fetchProfileByName_Should_Return_1() {
        BigInteger result = profileRepository.findProfileByName("F1soft Profile");
        assertThat(result).isEqualTo(ONE);
    }

    @Test
    public void fetchProfileByIdAndName_Should_Return_0() {
        BigInteger result = profileRepository.findProfileByIdAndName(1L, "F1soft");
        assertThat(result).isEqualTo(ZERO);
    }

    @Test
    public void fetchProfileByIdAndName_Should_Return_1() {
        BigInteger result = profileRepository.findProfileByIdAndName(2L, "F1soft Profile");
        assertThat(result).isEqualTo(ONE);
    }


}

