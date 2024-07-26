package com.mrxu.server;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description: netty 配置类
 * @author: ztowh
 * @Date: 2019-02-28 17:21
 */
@Component
@ConfigurationProperties(prefix = "netty")
public class ConfigMananger {

    private int netty_io_ratio = 70;

    private boolean tcp_so_keepalive = true;

    private boolean tcp_nodelay = true;

    private boolean netty_buffer_pooled = true;

    private int netty_buffer_low_watermark = 32 * 1024;

    private int netty_buffer_high_watermark = 64 * 1024;

    private int tcp_so_backlog = 1024;

    private boolean tcp_so_reuseaddr = true;

    private boolean writableControlSwitch;

    private boolean enableSSL = true;

    public boolean isNetty_buffer_pooled() {
        return netty_buffer_pooled;
    }

    public void setNetty_buffer_pooled(boolean netty_buffer_pooled) {
        this.netty_buffer_pooled = netty_buffer_pooled;
    }

    public int getNetty_buffer_high_watermark() {
        return netty_buffer_high_watermark;
    }

    public void setNetty_buffer_high_watermark(int netty_buffer_high_watermark) {
        this.netty_buffer_high_watermark = netty_buffer_high_watermark;
    }

    public int getNetty_io_ratio() {
        return netty_io_ratio;
    }

    public void setNetty_io_ratio(int netty_io_ratio) {
        this.netty_io_ratio = netty_io_ratio;
    }

    public boolean isTcp_so_keepalive() {
        return tcp_so_keepalive;
    }

    public void setTcp_so_keepalive(boolean tcp_so_keepalive) {
        this.tcp_so_keepalive = tcp_so_keepalive;
    }

    public boolean isTcp_nodelay() {
        return tcp_nodelay;
    }

    public void setTcp_nodelay(boolean tcp_nodelay) {
        this.tcp_nodelay = tcp_nodelay;
    }

    public int getNetty_buffer_low_watermark() {
        return netty_buffer_low_watermark;
    }

    public void setNetty_buffer_low_watermark(int netty_buffer_low_watermark) {
        this.netty_buffer_low_watermark = netty_buffer_low_watermark;
    }

    public int getTcp_so_backlog() {
        return tcp_so_backlog;
    }

    public void setTcp_so_backlog(int tcp_so_backlog) {
        this.tcp_so_backlog = tcp_so_backlog;
    }

    public boolean isTcp_so_reuseaddr() {
        return tcp_so_reuseaddr;
    }

    public void setTcp_so_reuseaddr(boolean tcp_so_reuseaddr) {
        this.tcp_so_reuseaddr = tcp_so_reuseaddr;
    }

    public boolean isWritableControlSwitch() {
        return writableControlSwitch;
    }

    public void setWritableControlSwitch(boolean writableControlSwitch) {
        this.writableControlSwitch = writableControlSwitch;
    }

    public boolean isEnableSSL() {
        return enableSSL;
    }

    public void setEnableSSL(boolean enableSSL) {
        this.enableSSL = enableSSL;
    }
}
