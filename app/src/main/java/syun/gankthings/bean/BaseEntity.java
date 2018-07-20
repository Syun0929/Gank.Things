package syun.gankthings.bean;

public class BaseEntity<T> {
    public boolean error;
    public T results;

    public boolean isSuccess(){
        return error == error;
    }
}
