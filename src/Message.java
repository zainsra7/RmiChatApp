public class Message  implements java.io.Serializable{

    String msg;
    String client;
    Message(String m,String c){msg=m; client=c;}

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}
