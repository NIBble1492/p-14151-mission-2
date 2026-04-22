package com.back;

import java.util.List;

public class Calc1 {
    public static int run(String expr) {
        // 0. 앞뒤 공백 제거
        expr = expr.trim();

        // 1. 쪼개는 기준점 찾기
        int index = -1;
        String selectedOp = "";
        // 1-1. 우선순위 적용됨, 음수값을 구별하기 위해서 연산자 양쪽에 공백 추가
        for (String op : List.of(" + ", " - ", " * ")) {
            index = expr.lastIndexOf(op);
            if(index != -1) {
                selectedOp = op;
                break;
            }
        }

        // 2. 재귀 탈출 조건, 연산자가 없는 경우
        if (index == -1) {
            return Integer.parseInt(expr);
        }

        // 3. 수식 쪼개기
        String left = expr.substring(0, index).trim();
        // 3-1. 연산자 공백 추가 때문에 selectedOp.length()를 더함
        String right = expr.substring(index + selectedOp.length()).trim();

        // 4. 재귀 호출
        // 4-1. 연산자 공백제거
        selectedOp = selectedOp.trim();

        if(selectedOp.equals("+")) {
            return run(left) + run(right);
        }
        if(selectedOp.equals("-")) {
            return run(left) - run(right);
        }
        if(selectedOp.equals("*")) {
            return run(left) * run(right);
        }
        return 0;
    }

}
