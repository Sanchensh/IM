package com.mrxu.server.processor.callback;

import com.mrxu.common.utils.NamedThreadFactory;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;
import io.netty.util.internal.PlatformDependent;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class TimerHolder {

    private final static long defaultTickDuration = 10;

	private final static ConcurrentMap<String, Timeout> scheduledFutures = PlatformDependent.newConcurrentHashMap();

    private static class DefaultInstance {
        static final Timer INSTANCE = new HashedWheelTimer(new NamedThreadFactory(
                "DefaultTimer" + defaultTickDuration, true),
                defaultTickDuration, TimeUnit.MILLISECONDS);
    }

    private TimerHolder() {
    }

    /**
     * Get a singleton instance of {@link Timer}. <br>
     * The tick duration is {@link #defaultTickDuration}.
     *
     * @return Timer
     */
    public static Timer getTimer() {
        return DefaultInstance.INSTANCE;
    }


	public static void schedule(final String key, final Runnable runnable, long delay, TimeUnit unit) {
		Timeout timeout = getTimer().newTimeout(new TimerTask() {
			@Override
			public void run(Timeout timeout) throws Exception {
				try {
					runnable.run();
				} finally {
					scheduledFutures.remove(key);
				}
			}
		}, delay, unit);

		replaceScheduledFuture(key, timeout);
	}


	private static void replaceScheduledFuture(final String key, final Timeout newTimeout) {
		final Timeout oldTimeout;

		if (newTimeout.isExpired()) {
			// no need to put already expired timeout to scheduledFutures map.
			// simply remove old timeout
			oldTimeout = scheduledFutures.remove(key);
		} else {
			oldTimeout = scheduledFutures.put(key, newTimeout);
		}

		// if there was old timeout, cancel it
		if (oldTimeout != null) {
			oldTimeout.cancel();
		}
	}
}