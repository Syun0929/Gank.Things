package syun.gankthings.api;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Part;
import syun.gankthings.bean.BaseEntry;
import syun.gankthings.bean.Meizi;

public interface GankApi {

    @GET("data/福利/10/{page}")
    Observable<BaseEntry<List<Meizi>>> getMeizi(@Part("page")int page);
}
