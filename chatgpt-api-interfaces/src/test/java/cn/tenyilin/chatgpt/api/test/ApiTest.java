package cn.tenyilin.chatgpt.api.test;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLOutput;

public class ApiTest {
    @Test
    public void query_unanswered_question() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/28885518425541/topics?scope=all&count=20");

        get.addHeader("cookie", "zsxq_access_token=C53A636B-1E7B-330B-4C5C-306C36090719_40702339506BD4D4; abtest_env=product; zsxqsessionid=c727cb1c4a2133cf539436308862947f; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22818551158585882%22%2C%22first_id%22%3A%2218c09a0e53fcce-075af3b58d85178-4c657b58-2073600-18c09a0e540d21%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMThjMDlhMGU1M2ZjY2UtMDc1YWYzYjU4ZDg1MTc4LTRjNjU3YjU4LTIwNzM2MDAtMThjMDlhMGU1NDBkMjEiLCIkaWRlbnRpdHlfbG9naW5faWQiOiI4MTg1NTExNTg1ODU4ODIifQ%3D%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22818551158585882%22%7D%2C%22%24device_id%22%3A%2218c09a0e53fcce-075af3b58d85178-4c657b58-2073600-18c09a0e540d21%22%7D");
        get.addHeader("Context-type", "application/json;charset=utf8");

        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }

    }

    @Test
    public void answer() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/211455251112181/comments");

        post.addHeader("cookie", "zsxq_access_token=C53A636B-1E7B-330B-4C5C-306C36090719_40702339506BD4D4; zsxqsessionid=c727cb1c4a2133cf539436308862947f; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22818551158585882%22%2C%22first_id%22%3A%2218c09a0e53fcce-075af3b58d85178-4c657b58-2073600-18c09a0e540d21%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMThjMDlhMGU1M2ZjY2UtMDc1YWYzYjU4ZDg1MTc4LTRjNjU3YjU4LTIwNzM2MDAtMThjMDlhMGU1NDBkMjEiLCIkaWRlbnRpdHlfbG9naW5faWQiOiI4MTg1NTExNTg1ODU4ODIifQ%3D%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22818551158585882%22%7D%2C%22%24device_id%22%3A%2218c09a0e53fcce-075af3b58d85178-4c657b58-2073600-18c09a0e540d21%22%7D; abtest_env=product");
        post.addHeader("Context-type", "application/json;charset=utf8");

        //这里在网页上复制json时要把req_data一起复制进去，不要点开下一级再复制
        String paramJson =  "{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"hhh\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"mentioned_user_ids\": []\n" +
                "  }\n" +
                "}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }

    }


}