package vn.vnpay.response;

public enum RespCode {
    SUCCESS("00") {
        @Override
        public String description() {
            return "SUCCESS";
        }
    },
    INVALID_DATA("84"){
        @Override
        public String description() {
            return "INVALID_DATA";
        }
    };
    private final String i;
    RespCode(String i) {
        this.i = i;
    }

    public String code() {
        return i;
    }

    public abstract String description();
}
