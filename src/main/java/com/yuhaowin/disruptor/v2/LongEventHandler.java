package com.yuhaowin.disruptor.v2;

import com.lmax.disruptor.EventHandler;

public class LongEventHandler implements EventHandler<LongEvent> {
    public void onEvent(LongEvent longEvent, long l, boolean b) {
        System.out.println(longEvent.getValue());
    }
}
