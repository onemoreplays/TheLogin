package me.inao.plugin.thelogin.communication.packets;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
public class Keyexchange {
    private final String name = this.getClass().getSimpleName();
    private final String stage;
    private final String data;
}
