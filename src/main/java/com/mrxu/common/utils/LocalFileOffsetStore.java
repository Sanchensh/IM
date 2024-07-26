///**
// * Licensed to the Apache Software Foundation (ASF) under one or more
// * contributor license agreements.  See the NOTICE file distributed with
// * this work for additional information regarding copyright ownership.
// * The ASF licenses this file to You under the Apache License, Version 2.0
// * (the "License"); you may not use this file except in compliance with
// * the License.  You may obtain a copy of the License at
// * <p>
// * http://www.apache.org/licenses/LICENSE-2.0
// * <p>
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.mrxu.common.utils;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
//import org.apache.rocketmq.client.consumer.store.OffsetSerializeWrapper;
//import org.apache.rocketmq.client.exception.MQClientException;
//import org.apache.rocketmq.common.MixAll;
//import org.apache.rocketmq.common.help.FAQUrl;
//import org.apache.rocketmq.common.message.MessageQueue;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.atomic.AtomicLong;
//
//
///**
// * Local storage implementation
// */
//@Slf4j
//public class LocalFileOffsetStore {
//
//    public final static String LocalOffsetStoreDir = System.getProperty(
//            "rocketmq.client.localOffsetStoreDir",
//            System.getProperty("user.home") + File.separator + ".rocketmq_offsets");
//    private final String groupName;
//    private final String storePath;
//    private final ZmsMetadata metadata;
//    private ConcurrentHashMap<MessageQueue, AtomicLong> offsetTable =
//            new ConcurrentHashMap<MessageQueue, AtomicLong>();
//
//    public LocalFileOffsetStore(String groupName) {
//        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
//        metadata = RouterManager.getZkInstance().readConsumerGroupMetadata(groupName);
//        if (metadata.isGatedLaunch()) {
//            consumer.setClientIP("consumer-client-id-" + metadata.getGatedCluster().getClusterName() + "-" + ZmsEnv.ZMS_IP);
//        } else {
//            consumer.setClientIP("consumer-client-id-" + metadata.getClusterMetadata().getClusterName() + "-" + ZmsEnv.ZMS_IP);
//        }
//        String clientId = consumer.buildMQClientId();
//        this.groupName = groupName;
//        this.storePath = LocalOffsetStoreDir + File.separator + //
//                clientId + File.separator + //
//                this.groupName + File.separator + //
//                "offsets.json";
//    }
//
//    public void load() throws MQClientException {
//        OffsetSerializeWrapper offsetSerializeWrapper = this.readLocalOffset();
//        if (offsetSerializeWrapper != null && offsetSerializeWrapper.getOffsetTable() != null) {
//            offsetTable.putAll(offsetSerializeWrapper.getOffsetTable());
//
//            for (MessageQueue mq : offsetSerializeWrapper.getOffsetTable().keySet()) {
//                AtomicLong offset = offsetSerializeWrapper.getOffsetTable().get(mq);
//                log.info("load consumer's offset, {} {} {}",//
//                        this.groupName,//
//                        mq,//
//                        offset.get());
//            }
//        }
//    }
//
//    public void removeOffset() {
//        OffsetSerializeWrapper offsetSerializeWrapper = new OffsetSerializeWrapper();
//        String jsonString = offsetSerializeWrapper.toJson(true);
//        try {
//            string2File(jsonString, this.storePath);
//        } catch (IOException e) {
//            log.error("remove consumer offset Exception, " + this.storePath, e);
//        }
//    }
//
//    private OffsetSerializeWrapper readLocalOffset() throws MQClientException, IOException {
//        String content = MixAll.file2String(this.storePath);
//        if (null == content || content.length() == 0) {
//            return this.readLocalOffsetBak();
//        } else {
//            OffsetSerializeWrapper offsetSerializeWrapper = null;
//            try {
//                offsetSerializeWrapper =
//                        OffsetSerializeWrapper.fromJson(content, OffsetSerializeWrapper.class);
//            } catch (Exception e) {
//                log.warn("readLocalOffset Exception, and try to correct", e);
//                return this.readLocalOffsetBak();
//            }
//
//            return offsetSerializeWrapper;
//        }
//    }
//
//    private OffsetSerializeWrapper readLocalOffsetBak() throws MQClientException, IOException {
//        String content = MixAll.file2String(this.storePath + ".bak");
//        if (content != null && content.length() > 0) {
//            OffsetSerializeWrapper offsetSerializeWrapper = null;
//            try {
//                offsetSerializeWrapper =
//                        OffsetSerializeWrapper.fromJson(content, OffsetSerializeWrapper.class);
//            } catch (Exception e) {
//                log.warn("readLocalOffset Exception", e);
//                throw new MQClientException("readLocalOffset Exception, maybe fastjson version too low" //
//                        + FAQUrl.suggestTodo(FAQUrl.LOAD_JSON_EXCEPTION), //
//                        e);
//            }
//            return offsetSerializeWrapper;
//        }
//
//        return null;
//    }
//
//    public static String file2String(final String fileName) throws IOException {
//        File file = new File(fileName);
//        return file2String(file);
//    }
//
//    public static String file2String(final File file) throws IOException {
//        if (file.exists()) {
//            byte[] data = new byte[(int) file.length()];
//            boolean result;
//
//            FileInputStream inputStream = null;
//            try {
//                inputStream = new FileInputStream(file);
//                int len = inputStream.read(data);
//                result = len == data.length;
//            } finally {
//                if (inputStream != null) {
//                    inputStream.close();
//                }
//            }
//
//            if (result) {
//                return new String(data);
//            }
//        }
//        return null;
//    }
//
//    public static void string2File(final String str, final String fileName) throws IOException {
//
//        String tmpFile = fileName + ".tmp";
//        string2FileNotSafe(str, tmpFile);
//
//        String bakFile = fileName + ".bak";
//        String prevContent = file2String(fileName);
//        if (prevContent != null) {
//            string2FileNotSafe(prevContent, bakFile);
//        }
//
//        File file = new File(fileName);
//        file.delete();
//
//        file = new File(tmpFile);
//        file.renameTo(new File(fileName));
//    }
//
//    public static void string2FileNotSafe(final String str, final String fileName) throws IOException {
//        File file = new File(fileName);
//        File fileParent = file.getParentFile();
//        if (fileParent != null) {
//            fileParent.mkdirs();
//        }
//        FileWriter fileWriter = null;
//
//        try {
//            fileWriter = new FileWriter(file);
//            fileWriter.write(str);
//        } catch (IOException e) {
//            throw e;
//        } finally {
//            if (fileWriter != null) {
//                fileWriter.close();
//            }
//        }
//    }
//
//}
