/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets;

import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.ProtocolVersion;

public enum ProtocolGroups {
    PRE_COMBAT(ProtocolVersion.V1_7, ProtocolVersion.V1_7_10, ProtocolVersion.V1_8, ProtocolVersion.V1_8_5, ProtocolVersion.V1_8_9),
    POST_COMBAT(ProtocolVersion.V1_9, ProtocolVersion.V1_9_1, ProtocolVersion.V1_9_2, ProtocolVersion.V1_9_4, ProtocolVersion.V1_10, ProtocolVersion.V1_10_2, ProtocolVersion.V1_11, ProtocolVersion.V1_12, ProtocolVersion.V1_12_1, ProtocolVersion.V1_12_2, ProtocolVersion.V1_13, ProtocolVersion.V1_13_1, ProtocolVersion.V1_13_2, ProtocolVersion.V1_14),
    POST_FUCK_UP(ProtocolVersion.V1_13, ProtocolVersion.V1_13_1, ProtocolVersion.V1_13_2, ProtocolVersion.V1_14);

    private final ProtocolVersion[] versions;

    private ProtocolGroups(ProtocolVersion ... protocolVersions) {
        this.versions = protocolVersions;
    }

    public ProtocolVersion[] getVersions() {
        return this.versions;
    }
}

