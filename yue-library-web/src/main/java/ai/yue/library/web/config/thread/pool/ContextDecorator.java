package ai.yue.library.web.config.thread.pool;

import ai.yue.library.base.config.thread.pool.AsyncProperties;
import org.slf4j.MDC;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;

/**
 * <h2>子线程上下文装饰器</h2>
 * <p>https://stackoverflow.com/questions/23732089/how-to-enable-request-scope-in-async-task-executor</p>
 * <p>传递：RequestAttributes and MDC and SecurityContext</p>
 *
 * @author ylyue
 * @since  2020/12/26
 */
public class ContextDecorator extends AbstractContextDecorator {

    public ContextDecorator(AsyncProperties asyncProperties) {
        super(asyncProperties);
    }

    @Override
    public Runnable decorate(Runnable runnable) {
        // Servlet上下文
        ServletRequestAttributes context = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        // 日志上下文
        Map<String, String> previous = MDC.getCopyOfContextMap();
        // ServletAsyncContext-enable：异步上下文最长生命周期（最大阻塞父线程多久）
        enableServletAsyncContext(context, asyncProperties);
        return () -> {
            try {
                RequestContextHolder.setRequestAttributes(context);
                if (previous != null) {
                    MDC.setContextMap(previous);
                }
                runnable.run();
            } finally {
                RequestContextHolder.resetRequestAttributes();
                MDC.clear();
                // ServletAsyncContext-complete：完成异步请求处理并关闭响应流
                completeServletAsyncContext(context, asyncProperties);
            }
        };
    }

}
