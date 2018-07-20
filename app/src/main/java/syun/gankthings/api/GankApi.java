package syun.gankthings.api;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import syun.gankthings.bean.BaseEntity;
import syun.gankthings.bean.Meizi;

public interface GankApi {

    @GET("data/福利/10/{page}")
    Observable<BaseEntity<List<Meizi>>> getMeizi(@Path("page") int page);
}
