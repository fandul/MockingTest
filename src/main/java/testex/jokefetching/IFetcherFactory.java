package testex.jokefetching;

import java.util.List;

/**
 *
 * @author Rihards
 */
public interface IFetcherFactory {

    List<String> getAvailableTypes();

    List<IJokeFetcher> getJokeFetchers(String jokesToFetch);

}
