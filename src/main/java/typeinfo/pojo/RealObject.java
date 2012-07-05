package typeinfo.pojo;

import typeinfo.itf.Interface;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-7-4
 * Time: 上午11:05
 * RealObject
 */
public class RealObject implements Interface {
    @Override
    public void doSomething() {
        System.out.println("Real Object do somethin...");
    }

    @Override
    public void somethingElse(String bonobo) {
        System.out.println("Real Object do something else...");
    }
}
