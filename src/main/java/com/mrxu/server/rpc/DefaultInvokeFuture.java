package com.mrxu.server.rpc;

import com.mrxu.remote.domain.RemotingCommand;
import com.mrxu.remote.domain.response.ImResponseCommand;
import com.mrxu.server.protocol.CommandHandler;
import com.mrxu.server.protocol.Protocol;
import com.mrxu.server.protocol.ProtocolManager;
import io.netty.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultInvokeFuture implements InvokeFuture {

	private static final Logger logger = LoggerFactory.getLogger("RpcRemoting");

	private long invokeId;

	private InvokeCallbackListener callbackListener;

	private volatile ImResponseCommand responseCommand;

	private final CountDownLatch countDownLatch = new CountDownLatch(1);

	private final AtomicBoolean executeCallbackOnlyOnce = new AtomicBoolean(false);

	private Timeout timeout;

	private Throwable cause;

	private ClassLoader classLoader;

	private byte protocol;

	private AtomicInteger retryTimes = new AtomicInteger(0);

	/**
	 * Constructor.
	 *
	 * @param invokeId invoke id
	 * @param callbackListener callback listener
	 * @param protocol protocol code
	 */
	public DefaultInvokeFuture(long invokeId, InvokeCallbackListener callbackListener,
			byte protocol) {
		this.invokeId = invokeId;
		this.callbackListener = callbackListener;
		this.classLoader = Thread.currentThread().getContextClassLoader();
		this.protocol = protocol;
	}


	@Override
	public ImResponseCommand waitResponse(long timeoutMillis) throws InterruptedException {
		this.countDownLatch.await(timeoutMillis, TimeUnit.MILLISECONDS);
		return this.responseCommand;
	}

	@Override
	public ImResponseCommand waitResponse() throws InterruptedException {
		this.countDownLatch.await();
		return this.responseCommand;
	}

	@Override
	public void putResponse(RemotingCommand response) {
		this.responseCommand = (ImResponseCommand) response;
		this.countDownLatch.countDown();
	}

	@Override
	public boolean isDone() {
		return this.countDownLatch.getCount() <= 0;
	}

	@Override
	public ClassLoader getAppClassLoader() {
		return this.classLoader;
	}

	@Override
	public long invokeId() {
		return this.invokeId;
	}

	@Override
	public int increRetryTimes() {
		return this.retryTimes.incrementAndGet();
	}

	@Override
	public void executeInvokeCallback() {
		if (callbackListener != null) {
			if (this.executeCallbackOnlyOnce.compareAndSet(false, true)) {
				callbackListener.onResponse(this);
			}
		}
	}

	@Override
	public void setCause(Throwable cause) {
		this.cause = cause;
	}

	@Override
	public Throwable getCause() {
		return this.cause;
	}

	@Override
	public byte getProtocolCode() {
		return this.protocol;
	}

	@Override
	public void tryAsyncExecuteInvokeCallbackAbnormally() {
		try {
			Protocol protocol = ProtocolManager.getProtocol(this.protocol);
			if (null != protocol) {
				CommandHandler commandHandler = protocol.getCommandHandler();
				if (null != commandHandler) {
					ExecutorService executor = commandHandler.getDefaultExecutor();
					if (null != executor) {
						executor.execute(new Runnable() {
							@Override
							public void run() {
								ClassLoader oldClassLoader = null;
								try {
									if (DefaultInvokeFuture.this.getAppClassLoader() != null) {
										oldClassLoader = Thread.currentThread().getContextClassLoader();
										Thread.currentThread()
												.setContextClassLoader(DefaultInvokeFuture.this.getAppClassLoader());
									}
									DefaultInvokeFuture.this.executeInvokeCallback();
								} finally {
									if (null != oldClassLoader) {
										Thread.currentThread().setContextClassLoader(oldClassLoader);
									}
								}
							}
						});
					}
				} else {
					logger.error("Executor null in commandHandler of protocolCode [{}].", this.protocol);
				}
			} else {
				logger.error("protocolCode [{}] not registered!", this.protocol);
			}
		} catch (Exception e) {
			logger.error("Exception caught when executing invoke callback abnormally.", e);
		}
	}

}
