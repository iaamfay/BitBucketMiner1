package aiss.bitbucketminer1.model.GitMiner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testGettersAndSetters() {
        User user = new User();
        user.setId("1");
        user.setUsername("testuser");
        user.setName("Test User");
        user.setAvatarUrl("http://avatar.url");
        user.setWebUrl("http://web.url");

        assertEquals("1", user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("Test User", user.getName());
        assertEquals("http://avatar.url", user.getAvatarUrl());
        assertEquals("http://web.url", user.getWebUrl());
    }

    @Test
    public void testToStringNotNull() {
        User user = new User();
        user.setId("1");
        user.setUsername("testuser");
        String str = user.toString();
        assertNotNull(str);
        assertTrue(str.contains("id"));
        assertTrue(str.contains("username"));
    }
}
