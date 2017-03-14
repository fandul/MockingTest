
package testex;

import java.util.Arrays;
import java.util.Collection;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import testex.jokefetching.FetcherFactory;

/**
 *
 * @author Rihards
 */

@RunWith(Parameterized.class)
public class TestValidToken {
    
    private String jokeTokens;
    private boolean expectedResult;
    JokeFetcher jokeFetcher;
    
    public TestValidToken(String jokeTokens, boolean expectedResult) {
        this.expectedResult = expectedResult;
        this.jokeTokens = jokeTokens;
        this.jokeFetcher = new JokeFetcher(new DateFormatter(), new FetcherFactory());
    }
    
    @Parameters
    public static Collection<Object[]> tokensToTest() {
        return Arrays.asList(new Object [] [] {
            {"cuckNoris", false},
            {"eduprog,chucknorris,chucknorris,moma,tambal", true}
        });
    }
    
    @Test
    public void testTokens () {
        assertThat(jokeFetcher.isStringValid(jokeTokens), is(expectedResult));
    }
}
