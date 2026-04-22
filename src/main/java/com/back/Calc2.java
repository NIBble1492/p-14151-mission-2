package com.back;

import java.util.List;

public class Calc2 {
    public static int run(String expr) {
        //System.out.println("expr = " + expr);
        // 1. 앞뒤 공백 제거
        expr = expr.trim();

        // 2. 최외곽 괄호 처리
        int bracketCount = 0;
        boolean isSplit = false;
        if(expr.charAt(0) == '(' && expr.charAt(expr.length()-1) == ')') {
            for (int i = 0; i < expr.length(); i++) {
                if(expr.charAt(i) == '(') {
                    bracketCount++;
                } else if (expr.charAt(i) == ')') {
                    bracketCount--;
                }
                if(bracketCount == 0 && i < expr.length() -1) {
                    isSplit = true;
                    break;
                }
            }
            if(!isSplit) {
                expr = expr.substring(1, expr.length()-1).trim();
                return run(expr);
            }
        }

        // 3. 쪼개는 기준점 찾기
        int index = -1;
        String selectedOp = "";
        // 3-1. 연산 우선순위가 낮은 순서대로 탐색 (재귀 호출 시 나중에 처리되는 연산자가 상위 노드가 됨)
        // 연산자 양쪽 공백은 -10 같은 음수(단항 연산자)와의 혼동을 방지하기 위함
        for (String op : List.of(" + ", " - ", " * ")) {
            bracketCount = 0;
            for(int i = expr.length()-1; i >= 0; i--) {
                if(expr.charAt(i) == ')') {
                    bracketCount++;
                }
                else if (expr.charAt(i) == '(') {
                    bracketCount--;
                }
                // 3-2. 괄호 안이 아닐때만 체크(bracketCount == 0)
                if (bracketCount == 0) {
                    // 3-3. 현재 i 위치부터 시작하는 문자열이 연산자(ex." + ") 와 정확히 일치하는지?
                    if (expr.startsWith(op, i)) {
                        index = i;
                        break;
                    }
                }
            }
            // 3-4. 쪼개는 기준점을 찾았을 때 탈출
            if(index != -1) {
                selectedOp = op;
                break;
            }
        }

        // 3-5. 단항 마이너스(-)로 시작하는 경우, 0에서 빼는 식(0 - X)으로 간주하여 처리
        if (index == -1 && expr.startsWith("-")) {
            return run("0") - run(expr.substring(1).trim());
        }

        // 4. 재귀 탈출 조건, 연산자가 없는 경우
        if (index == -1) {
            return Integer.parseInt(expr);
        }

        // 5. 수식 쪼개기
        String left = expr.substring(0, index).trim();
        // 3-1. 연산자 공백 추가 때문에 selectedOp.length()를 더함
        String right = expr.substring(index + selectedOp.length()).trim();

        // 6. 재귀 호출
        // 6-1. 연산자 공백제거
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
