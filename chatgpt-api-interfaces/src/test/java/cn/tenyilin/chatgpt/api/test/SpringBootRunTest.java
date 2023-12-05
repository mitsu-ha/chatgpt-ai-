package cn.tenyilin.chatgpt.api.test;

import cn.tenyilin.chatgpt.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import cn.tenyilin.chatgpt.api.domain.zsxq.model.vo.Topics;
import cn.tenyilin.chatgpt.api.domain.zsxq.service.ZsxqApi;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

//取消这两个注解则无法注入API
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRunTest {

    private Logger logger = LoggerFactory.getLogger(SpringBootRunTest.class);

//    @Value("${chatgpt-api.groupId}")
    @Value("${chatgpt-api.groupId}")
    private String groupId;

    @Value("${chatgpt-api.cookie}")
    private String cookie;

    @Resource
    private ZsxqApi zsxqApi;

    @Test
    public void test_zsxqApi() throws IOException {
        UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates = zsxqApi.queryUnAnsweredQuestionsTopicID(groupId, cookie);
        logger.info("测试结果{}", JSON.toJSONString(unAnsweredQuestionsAggregates));

        List<Topics> topics = unAnsweredQuestionsAggregates.getResp_data().getTopics();
        for (Topics topic: topics){
            String topicId = topic.getTopic_id();
            String text = topic.getTalk().getText();
            logger.info("topic_id:{} text:{}", topicId, text);
        }
    }


}
