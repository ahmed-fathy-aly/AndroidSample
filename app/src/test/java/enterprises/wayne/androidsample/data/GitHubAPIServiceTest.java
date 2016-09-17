package enterprises.wayne.androidsample.data;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import enterprises.wayne.androidsample.entity.Repo;

import static org.junit.Assert.*;

/**
 * Created by ahmed on 9/17/2016.
 */
public class GitHubAPIServiceTest
{
    private GitHubAPIService gitHubAPIService;

    @Before
    public void init()
    {
        gitHubAPIService = new GitHubAPIService();
    }

    /**
     * checks that the api call returns a list of object with non-null fields
     * @throws Exception
     */
    @Test
    public void testGetRepos() throws Exception
    {
        String userName = "ahmed-fathy-aly";
        List<Repo> repoList = gitHubAPIService.getRepos(userName);

        assertNotNull(repoList);
        assertTrue(repoList.size() > 0);
        for (Repo repo : repoList)
        {
            assertNotNull(repo.getName());
            assertNotNull(repo.getId());
        }
    }
}