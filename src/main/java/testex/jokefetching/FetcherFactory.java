
package testex.jokefetching;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Rihards
 */
public class FetcherFactory implements IFetcherFactory{

     private final List<String> availableTypes = 
        Arrays.asList("EduJoke", "ChuckNorris", "Moma", "Tambal");
     
    @Override
    public List<String> getAvailableTypes() {
        return availableTypes;
    }

    @Override
    public List<IJokeFetcher> getJokeFetchers(String jokesToFetch) {
        List<IJokeFetcher> jokeList = new ArrayList<IJokeFetcher>();
        String[] tokens = jokesToFetch.split(",");
        
        for (String token : tokens) {
            switch (token) {
                case "eduprog":
                    jokeList.add(new EduJoke());
                    break;
                case "chucknorris":
                    jokeList.add(new ChuckNorris());
                    break;
                case "moma":
                    jokeList.add(new Moma());
                    break;
                case "tambal":
                    jokeList.add(new Tambal());
                    break;
                default:
                    break;
            }
        }
        return jokeList;
    }
    
}
