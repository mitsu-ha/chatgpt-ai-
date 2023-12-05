package cn.tenyilin.chatgpt.api.domain.zsxq;

import cn.tenyilin.chatgpt.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public interface IZsxqApi {


    UnAnsweredQuestionsAggregates queryUnAnsweredQuestionsTopicID(String groupID, String cookie) throws IOException;

    boolean answer(String cookie, String topicId, String text) throws IOException;
}
