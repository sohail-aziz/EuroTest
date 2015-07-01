package aziz.sohail.eurotest.Dao;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Sohail Aziz on 6/24/2015.
 */
public class WebManagerFactory {

    private static final WebManager webManager = new WebManagerImpl();

    @NotNull
    public static WebManager getWebManager() {
        return webManager;
    }

    private WebManagerFactory() {

    }
}
