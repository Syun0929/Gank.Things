package syun.gankthings.bean;

public class BaseEntry<T> {
    public boolean error;
    public T results;

    public boolean isSuccess(){
        return error == error;
    }
}
