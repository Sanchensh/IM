package com.mrxu.server.processor;

import com.alibaba.fastjson.JSON;
import com.mrxu.common.ResponseStatus;
import com.mrxu.common.exception.DeserializationException;
import com.mrxu.common.utils.RemotingUtil;
import com.mrxu.remote.domain.RemotingCommand;
import com.mrxu.remote.domain.request.ImRequestCommand;
import com.mrxu.server.processor.command.CommandProcessorManager;
import com.mrxu.server.rpc.CommandFactory;
import com.mrxu.server.rpc.RemotingContext;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

/**
 * @Description:
 * @author: ztowh
 * @Date: 2018/11/30 15:54
 */
public class ImRequestProcessor extends AbstractRemotingProcessor<ImRequestCommand> {
	private Logger logger = LoggerFactory.getLogger(ImRequestProcessor.class);

	/**
	 * Constructor.
	 */
	public ImRequestProcessor(CommandFactory commandFactory) {
		super(commandFactory);
	}

	/**
	 * Constructor.
	 */
	public ImRequestProcessor(ExecutorService executor) {
		super(executor);
	}

	@Override
	public void doProcess(RemotingContext ctx, ImRequestCommand cmd) throws Exception {
		long currentTimestamp = System.currentTimeMillis();

		preProcessRemotingContext(ctx, cmd, currentTimestamp);
		debugLog(ctx, cmd, currentTimestamp);
		// decode request all
		if (!deserializeRequestCommand(ctx, cmd)) {
			return;
		}
		dispatchToUserProcessor(ctx, cmd);
	}

	private void dispatchToUserProcessor(RemotingContext ctx, ImRequestCommand cmd) {
		final long id = cmd.getId();
		final byte type = cmd.getType();
		// processor here must not be null, for it have been checked before
		CommandProcessor processor = CommandProcessorManager.getCommandProcessor(cmd.getCmdCode());
		try {
			Object responseObject = processor.handle(ctx, cmd.getRequestObject(), cmd);

			if (responseObject == null) {
				return;
			}
			sendResponseIfNecessary(ctx, type, this.getCommandFactory().createResponse(responseObject, cmd));
		} catch (RejectedExecutionException e) {
			logger.warn("RejectedExecutionException occurred when do SYNC process in RpcRequestProcessor");
			sendResponseIfNecessary(ctx, type,
					this.getCommandFactory().createExceptionResponse(id, ResponseStatus.SERVER_THREADPOOL_BUSY));
		} catch (Throwable t) {
			String errMsg = "SYNC process rpc request failed in RpcRequestProcessor, id=" + id;
			logger.error(errMsg, t);
			sendResponseIfNecessary(ctx, type, this.getCommandFactory().createExceptionResponse(id, t, errMsg));
		}
	}

	private void preProcessRemotingContext(RemotingContext ctx, ImRequestCommand cmd, long currentTimestamp) {
		cmd.setArriveTime(currentTimestamp);
		ctx.setArriveTimestamp(cmd.getArriveTime());
		ctx.setRpcCommandType(cmd.getType());
	}



	/**
	 * print some debug log when receive request
	 */
	private void debugLog(RemotingContext ctx, ImRequestCommand cmd, long currentTimestamp) {
		if (logger.isDebugEnabled()) {
			logger.debug("Rpc request received! requestId={}, from {}", cmd.getId(),
					RemotingUtil.parseRemoteAddress(ctx.getChannelContext().channel()));
			logger.debug("request id {} currenTimestamp {} - arriveTime {} = server cost {}.", cmd.getId(),
					currentTimestamp, cmd.getArriveTime(), (currentTimestamp - cmd.getArriveTime()));
		}
	}

	@Override
	public void process(RemotingContext ctx, ImRequestCommand cmd, ExecutorService defaultExecutor) throws Exception {

		CommandProcessor commandProcessor = CommandProcessorManager.getCommandProcessor(cmd.getCmdCode());
		if (commandProcessor == null) {
			String errMsg = "No user processor found for request: " + cmd.getCmdCode();
			logger.error(errMsg);
			sendResponseIfNecessary(ctx, cmd.getType(),
					this.getCommandFactory().createExceptionResponse(cmd.getId(), errMsg));
			return;// must end process
		}


		Executor executor = (this.getExecutor() == null ? defaultExecutor : this.getExecutor());
		// use the final executor dispatch process task
		executor.execute(new ProcessTask(ctx, cmd));
	}

	/**
	 * deserialize request command
	 *
	 * @return true if deserialize success; false if exception catched
	 */
	private boolean deserializeRequestCommand(RemotingContext ctx, ImRequestCommand cmd) {
		boolean result;
		try {
			cmd.deserialize();
			result = true;
		} catch (DeserializationException e) {
			logger.error("DeserializationException occurred when process in RpcRequestProcessor, id={}", cmd.getId(),
					e);
			sendResponseIfNecessary(ctx, cmd.getType(), this.getCommandFactory()
					.createExceptionResponse(cmd.getId(), ResponseStatus.SERVER_DESERIAL_EXCEPTION, e));
			result = false;
		} catch (Throwable t) {
			String errMsg = "Deserialize RpcRequestCommand failed in RpcRequestProcessor, id=" + cmd.getId();
			logger.error(errMsg, t);
			sendResponseIfNecessary(ctx, cmd.getType(),
					this.getCommandFactory().createExceptionResponse(cmd.getId(), t, errMsg));
			result = false;
		}
		return result;
	}


	public void sendResponseIfNecessary(final RemotingContext ctx, byte type, final RemotingCommand response) {
		final long id = response.getId();
		ctx.writeAndFlush(response).addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if (logger.isDebugEnabled()) {
					logger.debug("Rpc response sent! requestId=" + id + ". The address is " + RemotingUtil
							.parseRemoteAddress(ctx.getChannelContext().channel()));
				}
				if (!future.isSuccess()) {
					logger.error("Rpc response send failed! id=" + id + ". The address is " + RemotingUtil
							.parseRemoteAddress(ctx.getChannelContext().channel()), future.cause());
				}
			}
		});
	}

	/**
	 * Inner process task
	 *
	 * @author xiaomin.cxm
	 * @version $Id: RpcRequestProcessor.java, v 0.1 May 19, 2016 4:01:28 PM xiaomin.cxm Exp $
	 */
	class ProcessTask implements Runnable {

		RemotingContext ctx;
		ImRequestCommand msg;

		public ProcessTask(RemotingContext ctx, ImRequestCommand msg) {
			this.ctx = ctx;
			this.msg = msg;
		}

		/**
		 * @see Runnable#run()
		 */
		@Override
		public void run() {
			long start = System.currentTimeMillis();
			try {
				ImRequestProcessor.this.doProcess(ctx, msg);
			} catch (Throwable e) {
				//protect the thread running this task
				String remotingAddress = RemotingUtil.parseRemoteAddress(ctx.getChannelContext().channel());
				logger.error(
						"Exception caught when process rpc request command in RpcRequestProcessor, Id=" + msg.getId()
								+ "! Invoke source address is [" + remotingAddress + "].", e);
			}
			logger.info("command {} process cost {}ms ", JSON.toJSON(msg),System.currentTimeMillis() - start);
		}

	}

}
