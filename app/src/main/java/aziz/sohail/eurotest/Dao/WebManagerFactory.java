package aziz.sohail.eurotest.Dao;

/**
 * Created by Sohail Aziz on 6/24/2015.
 */
public class WebManagerFactory {

    private static final WebManager webManager = new WebManagerImpl();

    public static WebManager getWebManager() {
        return webManager;
    }

    private WebManagerFactory() {

    }
}
