//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mrxu.server.processor;


import org.apache.logging.log4j.message.SimpleMessage;

@FunctionalInterface
public interface CompleteMessageListener {
    void complete(SimpleMessage var1);
}
