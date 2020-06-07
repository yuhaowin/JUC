package com.yuhaowin.disruptor.v1;

import com.lmax.disruptor.EventHandler;

public class LongEventHandler implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent longEvent, long l, boolean b) {
        System.out.println(longEvent.getValue());
    }
}
