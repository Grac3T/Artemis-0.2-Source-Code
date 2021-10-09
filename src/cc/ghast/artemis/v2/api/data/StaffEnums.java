/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.api.data;

public class StaffEnums {

    public static enum StaffAlerts {
        EXPERIMENTAL_VERBOSE(3),
        VERBOSE(2),
        ALERTS(1),
        NONE(0);

        private int importance;

        private StaffAlerts(int importance) {
        }

        public boolean isHighEnough(int i) {
            return i >= this.importance;
        }

        public boolean isHighEnough(StaffAlerts i) {
            return i.getImportance() <= this.importance;
        }

        public int getImportance() {
            return this.importance;
        }
    }

}

