package aiss.bitbucketminer1.model.BitBucket.user;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserJavaTest {

    @Test
    public void testGettersAndSetters() {
        UserJava user = new UserJava();
        user.setDisplayName("Test User");
        user.setUuid("uuid");
        user.setAccountId("accountId");
        user.setNickname("nickname");

        assertEquals("Test User", user.getDisplayName());
        assertEquals("uuid", user.getUuid());
        assertEquals("accountId", user.getAccountId());
        assertEquals("nickname", user.getNickname());
    }
}
