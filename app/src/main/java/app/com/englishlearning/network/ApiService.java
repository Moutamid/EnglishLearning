package app.com.englishlearning.network;

import java.util.List;


import app.com.englishlearning.model.ImgUrlModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

// this is class where we are calling our api functions here
public interface ApiService {

    //here we are defining signup function of the api here we only define our function it will be called from the signup page
    @GET("api/")
    Call<ImgUrlModel> Register(@Query("key") String key, @Query("q") String name, @Query("image_type") String image_type, @Query("pretty")Boolean pretty);
    //just like about function we are just defining login function of api here and soo on

}
