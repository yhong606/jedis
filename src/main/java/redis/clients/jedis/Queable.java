package redis.clients.jedis;

import java.util.LinkedList;
import java.util.Queue;

public class Queable {
    private Queue<Response<?>> pipelinedResponses = new LinkedList<Response<?>>();	//使用builder构建，封装了将来会返回填充的内容

    protected void clean() {
        pipelinedResponses.clear();
    }

    protected Response<?> generateResponse(Object data) {
        Response<?> response = pipelinedResponses.poll();
        if (response != null) {
            response.set(data);
        }
        return response;
    }

    protected <T> Response<T> getResponse(Builder<T> builder) {
        Response<T> lr = new Response<T>(builder);
        pipelinedResponses.add(lr);
        return lr;
    }

}
