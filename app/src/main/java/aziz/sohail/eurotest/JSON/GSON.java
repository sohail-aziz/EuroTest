package aziz.sohail.eurotest.JSON;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Sohail Aziz on 6/23/2015.
 */
public class GSON {

    private static final Gson gson = new Gson();

    @NotNull
    public static Gson getGson() {
        return gson;
    }


    private GSON() {
    }
}
