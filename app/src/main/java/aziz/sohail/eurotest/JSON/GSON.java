package aziz.sohail.eurotest.JSON;

import com.google.gson.Gson;

/**
 * Created by Sohail Aziz on 6/23/2015.
 */
public class GSON {

    private static final Gson gson = new Gson();

    public static Gson getGson() {
        return gson;
    }


    private GSON() {

    }
}
