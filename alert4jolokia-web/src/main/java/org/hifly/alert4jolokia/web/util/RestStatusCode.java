package org.hifly.alert4jolokia.web.util;


public enum RestStatusCode {

    CODE_200(200),
    CODE_204(204),
    CODE_600(600),
    CODE_602(602),
    CODE_603(603),
    CODE_604(604),
    CODE_605(605),
    CODE_606(606),
    CODE_607(607),
    CODE_608(608),
    CODE_609(609),
    CODE_610(610),
    CODE_611(611);

    private final int code;

    RestStatusCode(int code) {
        this.code = code;

    }

    public int code() { return code; }

}
