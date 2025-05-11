package aiss.bitbucketminer1.model.BitBucket.common;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LinkTest {

    @Test
    public void testGettersAndSetters() {
        Link link = new Link();
        link.setHref("http://example.com");
        assertEquals("http://example.com", link.getHref());
    }
}
