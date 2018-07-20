package syun.gankthings.fuli;

import java.util.List;

import syun.gankthings.bean.Meizi;

public class FuliEvent {
    public static class LoadMeiziDataEvent{
        private List<Meizi> list;

        public LoadMeiziDataEvent(List<Meizi> list){
            this.list = list;
        }
        public List<Meizi> getList() {
            return list;
        }
    }

    public static class LoadMoreMeiziDataEvent{
        private List<Meizi> list;

        public LoadMoreMeiziDataEvent(List<Meizi> list){
            this.list = list;
        }
        public List<Meizi> getList() {
            return list;
        }
    }

    public static class LoadDataErrorEvent{
        private String msg;

        public LoadDataErrorEvent(String msg){
            this.msg = msg;
        }
        public String getMsg() {
            return msg;
        }
    }
}
