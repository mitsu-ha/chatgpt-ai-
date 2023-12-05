package cn.tenyilin.chatgpt.api.domain.zsxq.service;

import cn.tenyilin.chatgpt.api.domain.zsxq.IZsxqApi;
import cn.tenyilin.chatgpt.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import cn.tenyilin.chatgpt.api.domain.zsxq.model.req.AnswerReq;
import cn.tenyilin.chatgpt.api.domain.zsxq.model.req.ReqData;
import cn.tenyilin.chatgpt.api.domain.zsxq.model.res.AnswerRes;
import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import net.sf.json.JSONObject;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ZsxqApi implements IZsxqApi {

    private Logger logger = LoggerFactory.getLogger(ZsxqApi.class);
//    把这所谓的UnAnsweredQuestionsAggregates写成一个res不就好了吗，特意做一个聚合对象干嘛
    @Override
    public UnAnsweredQuestionsAggregates queryUnAnsweredQuestionsTopicID(String groupID, String cookie) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/" + groupID + "/topics?scope=all&count=20");

        get.addHeader("cookie", cookie);
        get.addHeader("Context-type", "application/json;charset=utf8");


        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("拉取问题：groupId：{}  jsonStr：{}", groupID, jsonStr);
            return JSON.parseObject(jsonStr, UnAnsweredQuestionsAggregates.class);
        } else {
            throw new RuntimeException("queryUnAnsweredQuestionsTopicID err code is " + response.getStatusLine().getStatusCode());
        }
    }

    @Override
    public boolean answer(String cookie, String topicId, String text) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/" + topicId + "/comments");

        post.addHeader("cookie", cookie);
        post.addHeader("Context-type", "application/json;charset=utf8");
        post.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
        //这里在网页上复制json时要把req_data一起复制进去，不要点开下一级再复制

        AnswerReq answerReq = new AnswerReq(new ReqData(text));
        String paramJson = JSONObject.fromObject(answerReq).toString();
        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("回答问题结果。 topicId：{} jsonStr：{}", topicId, jsonStr);
            AnswerRes answerRes = JSON.parseObject(jsonStr, AnswerRes.class);
            return answerRes.isSucceeded();
        } else {
            throw new RuntimeException("answer Err Code is " + response.getStatusLine().getStatusCode());        }
    }
}
