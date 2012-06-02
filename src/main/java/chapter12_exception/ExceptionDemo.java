package chapter12_exception;

public class ExceptionDemo {

    /**
     * 使用initCause包装异常后抛出，可以实现异常链
     * @throws Exception
     */
    public void exceptionChain() throws Exception{
        Exception e = new Exception("my exception");
        e.initCause(new RuntimeException("Runtime exception"));
        throw e;
    }
}
