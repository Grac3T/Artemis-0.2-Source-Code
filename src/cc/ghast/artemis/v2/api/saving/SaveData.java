/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.api.saving;

import cc.ghast.artemis.v2.api.data.Violation;
import java.util.List;

public class SaveData {
    private final String uuid;
    private final String username;
    private final String ip;
    private final List<Violation> violations;

    public SaveData(String uuid, String username, String ip, List<Violation> violations) {
        this.uuid = uuid;
        this.username = username;
        this.ip = ip;
        this.violations = violations;
    }

    public String getUuid() {
        return this.uuid;
    }

    public String getUsername() {
        return this.username;
    }

    public String getIp() {
        return this.ip;
    }

    public List<Violation> getViolations() {
        return this.violations;
    }
}

