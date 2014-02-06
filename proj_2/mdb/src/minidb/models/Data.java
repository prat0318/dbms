package minidb.models;

public class Data<T> {
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getType() {
        return data.getClass().getName();
    }

    private T data;
//    private DataType dataType;



}
