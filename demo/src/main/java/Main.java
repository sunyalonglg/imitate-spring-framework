import com.iwork.core.dao.connectionpool.pool.ConnectionPool;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection currentConnection = ConnectionPool.getPool().getCurrentConnection();
        currentConnection.setAutoCommit(true);
        PreparedStatement preparedStatement = currentConnection.prepareStatement("update t_hehe set year = ? where tid = ? ");
        preparedStatement.setString(1,"6666");
        preparedStatement.setString(2,"1");
        int i = preparedStatement.executeUpdate();
        System.out.println(i);

    }

    public static <T> T newCglibInstance(T obj) {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(obj.getClass());
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                return null;
            }
        });
        return (T)enhancer.create();
    }

}
