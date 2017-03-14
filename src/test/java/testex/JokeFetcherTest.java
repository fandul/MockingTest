package testex;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import testex.jokefetching.ChuckNorris;
import testex.jokefetching.EduJoke;
import testex.jokefetching.FetcherFactory;
import testex.jokefetching.IFetcherFactory;
import testex.jokefetching.IJokeFetcher;
import testex.jokefetching.Moma;
import testex.jokefetching.Tambal;

/**
 *
 * @author Rihards
 */
@RunWith(MockitoJUnitRunner.class)
public class JokeFetcherTest {

    Joke testEdu = new Joke("This is an educational joke", "EducationalJokes.org");
    Joke testChuck = new Joke("Cuck Norris joke...", "ChuckNorrisJokes.org");
    Joke testMoma = new Joke("Yo mama is so fat...", "JoMamaJokes.org");
    Joke testTambal = new Joke("tambala...", "TambalaJokes.org");
    
    JokeFetcher jokeFetcher;

    @Mock
    IFetcherFactory factory;
    @Mock
    IDateFormatter dfMock;
    @Mock
    ChuckNorris chuckNorris;
    @Mock
    Moma moma;
    @Mock
    EduJoke eduJoke;
    @Mock
    Tambal tambal;

    @Before
    public void setUp() {
        List<IJokeFetcher> fetchers = Arrays.asList(eduJoke, chuckNorris, moma, tambal);
        when(factory.getJokeFetchers("eduprog,chucknorris,moma,tambal")).thenReturn(fetchers);
        List<String> types = Arrays.asList("EduJoke", "ChuckNorris", "Moma", "Tambal");
        when(factory.getAvailableTypes()).thenReturn(types);

        given(eduJoke.getJoke()).willReturn(testEdu);
        given(chuckNorris.getJoke()).willReturn(testChuck);
        given(moma.getJoke()).willReturn(testMoma);
        given(tambal.getJoke()).willReturn(testTambal);
        jokeFetcher = new JokeFetcher(dfMock, factory);
    }

    @Test
    public void testGetAvailableTypes() {
        List<String> expResult = Arrays.asList("eduprog", "chucknorris", "moma", "tambal");
        List<String> result = jokeFetcher.getAvailableTypes();
        assertThat(expResult, is(result));
        assertThat(result, hasItems("eduprog", "chucknorris", "moma", "tambal"));
        assertThat(result, hasSize(equalTo(4)));
    }

    @Test
    public void testGetJokes() throws Exception {

        given(dfMock.getFormattedDate(eq("Europe/Copenhagen"), anyObject())).willReturn("17 feb. 2017 10:56 AM");
        assertThat(jokeFetcher.getJokes("eduprog,chucknorris,moma,tambal", "Europe/Copenhagen")
                .getTimeZoneString(), is("17 feb. 2017 10:56 AM"));
    }

    @Test
    public void testEduJoke() throws Exception {
        String expectedJoke = "This is an educational joke";
        String expectedReference = "EducationalJokes.org";
        Jokes jokes = jokeFetcher.getJokes("eduprog,chucknorris,moma,tambal", "Europe/Copenhagen");
        assertThat(jokes.getJokes().get(0).getJoke(), is(expectedJoke));
        assertThat(jokes.getJokes().get(0).getReference(), is(expectedReference));
        verify(eduJoke, times(1)).getJoke();
    }
    
    @Test
    public void testChuckNorrisJoke() throws Exception {
        String expectedJoke = "Cuck Norris joke...";
        String expectedReference = "ChuckNorrisJokes.org";
        Jokes jokes = jokeFetcher.getJokes("eduprog,chucknorris,moma,tambal", "Europe/Copenhagen");
        assertThat(jokes.getJokes().get(1).getJoke(), is(expectedJoke));
        assertThat(jokes.getJokes().get(1).getReference(), is(expectedReference));
        verify(chuckNorris, times(1)).getJoke();
    }
    
    @Test
    public void testYoMamaJoke() throws Exception {
        String expectedJoke = "Yo mama is so fat...";
        String expectedReference = "JoMamaJokes.org";
        Jokes jokes = jokeFetcher.getJokes("eduprog,chucknorris,moma,tambal", "Europe/Copenhagen");
        assertThat(jokes.getJokes().get(2).getJoke(), is(expectedJoke));
        assertThat(jokes.getJokes().get(2).getReference(), is(expectedReference));
        verify(moma, times(1)).getJoke();
    }
    
     @Test
    public void testTambalaJoke() throws Exception {
        String expectedJoke = "tambala...";
        String expectedReference = "TambalaJokes.org";
        Jokes jokes = jokeFetcher.getJokes("eduprog,chucknorris,moma,tambal", "Europe/Copenhagen");
        assertThat(jokes.getJokes().get(3).getJoke(), is(expectedJoke));
        assertThat(jokes.getJokes().get(3).getReference(), is(expectedReference));
        verify(tambal, times(1)).getJoke();
    }

    @Test
    public void testFetchers() {
        factory = new FetcherFactory();
        List<IJokeFetcher> result = factory.getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal");
        assertThat(result, hasItems());
        result.forEach((fetch) -> {
            assertThat(fetch, instanceOf(IJokeFetcher.class));
        });
    }
}
