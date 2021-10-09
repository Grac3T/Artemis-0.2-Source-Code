/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.api;

import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.Reflection;

public enum ProtocolVersion {
    V1_7(4, "v1_7_R3"),
    V1_7_10(5, "v1_7_R4"),
    V1_8(45, "v1_8_R1"),
    V1_8_5(46, "v1_8_R2"),
    V1_8_9(47, "v1_8_R3"),
    V1_9(107, "v1_9_R1"),
    V1_9_1(108, null),
    V1_9_2(109, "v1_9_R2"),
    V1_9_4(110, "v1_9_R2"),
    V1_10(210, "v1_10_R1"),
    V1_10_2(210, "v1_10_R1"),
    V1_11(316, "v1_11_R1"),
    V1_12(335, "v1_12_R1"),
    V1_12_1(338, null),
    V1_12_2(340, "v1_12_R1"),
    V1_13(350, "v1_13_R1"),
    V1_13_1(351, "v1_13_R2"),
    V1_13_2(352, "v1_13_R2"),
    V1_14(477, "v1_14_R1"),
    UNKNOWN(-1, "UNKNOWN");

    private static ProtocolVersion gameVersion;
    private int version;
    private String serverVersion;

    private static ProtocolVersion fetchGameVersion() {
        for (ProtocolVersion version : ProtocolVersion.values()) {
            if (version.getServerVersion() == null || !version.getServerVersion().equals(Reflection.VERSION)) continue;
            return version;
        }
        return UNKNOWN;
    }

    public static ProtocolVersion getVersion(int versionId) {
        for (ProtocolVersion version : ProtocolVersion.values()) {
            if (version.getVersion() != versionId) continue;
            return version;
        }
        return UNKNOWN;
    }

    public boolean isBelow(ProtocolVersion version) {
        return this.getVersion() < version.getVersion();
    }

    public boolean isAbove(ProtocolVersion version) {
        return this.getVersion() > version.getVersion();
    }

    public boolean isOrAbove(ProtocolVersion version) {
        return this.getVersion() >= version.getVersion();
    }

    public int getVersion() {
        return this.version;
    }

    public String getServerVersion() {
        return this.serverVersion;
    }

    private ProtocolVersion(int version, String serverVersion) {
        this.version = version;
        this.serverVersion = serverVersion;
    }

    public static ProtocolVersion getGameVersion() {
        return gameVersion;
    }

    static {
        gameVersion = ProtocolVersion.fetchGameVersion();
    }
}

