package name.abhijitsarkar.javaee.common;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.Random;

/**
 * @author Abhijit Sarkar
 */
@CacheConfig(cacheResolver = "cacheResolver")
public class RandomNumberGenerator {
    private final Random randomNumGenerator = new Random();

    @Cacheable
    public Integer random(int bound) {
        return randomNumGenerator.nextInt(bound);
    }
}
