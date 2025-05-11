package aiss.bitbucketminer1.utils;

import org.springframework.http.HttpHeaders;
import java.util.List;

public class RESTUtil {
    public static String getNextPageUrl(HttpHeaders headers) {
        List<String> linkHeaders = headers.get("Link");
        if (linkHeaders == null || linkHeaders.isEmpty()) {
            return null;
        }

        for (String linkHeader : linkHeaders) {
            String[] links = linkHeader.split(",");
            for (String link : links) {
                String[] parts = link.split(";");
                if (parts.length < 2) {
                    continue;
                }
                String url = parts[0].trim().replaceAll("[<>]", "");
                String rel = parts[1].trim();
                if (rel.equals("rel=\"next\"")) {
                    return url;
                }
            }
        }
        return null;
    }
}