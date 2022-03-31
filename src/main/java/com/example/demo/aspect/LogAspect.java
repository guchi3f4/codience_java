package com.example.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
	
	final String RED    = "\u001b[00;31m";
	final String GREEN  = "\u001b[00;32m";
	final String YELLOW = "\u001b[00;33m";
	final String PURPLE = "\u001b[00;34m";
	final String PINK   = "\u001b[00;35m";
	final String CYAN   = "\u001b[00;36m";   
	final String END    = "\u001b[00m";
	
	//Controllerクラスのログ
	
	@Before("execution(* com.example.demo.app.*..*Controller*.*(..))")
	public void startControllerLog(JoinPoint jp) {
		System.out.println("メソッド開始：" + jp.getSignature());
		System.out.print(" クラス名：" + PINK + jp.getSignature().getDeclaringType().getSimpleName() + END);
        System.out.print("  メソッド名：" + PINK + jp.getSignature().getName() + END);
        System.out.println("  (" + jp.getSignature().getDeclaringType().getSimpleName() + ".java:" + 1 + ")");
        printArgStr(jp);
	}
	
	@AfterReturning(value ="execution(* com.example.demo.app.*..*Controller*.*(..))", returning = "returnValue")
    public void endControllerLog(JoinPoint jp, Object returnValue) {
        System.out.println(" 戻り値：" + returnValue);
        System.out.println("メソッド正常終了：" + jp.getSignature());
        System.out.println("");
    }
	
	@AfterThrowing(value = "execution(* com.example.demo.app.*..*Controller*.*(..))", throwing="e")
	public void errorControllerLog(JoinPoint jp, Exception e) {
		System.out.println("メソッド異常終了：" + jp.getSignature());
		e.printStackTrace();
        System.out.println("");
	}
	
	//Serviceクラスのログ
	
	@Before("execution(* com.example.demo.domain.*..*Service*.*(..))")
	public void startServiceLog(JoinPoint jp) {
		System.out.println("");
		System.out.println("メソッド開始：" + jp.getSignature());
		System.out.print(" クラス名：" + PINK +jp.getSignature().getDeclaringType().getSimpleName() + END);
        System.out.print("  メソッド名：" + PINK + jp.getSignature().getName() + END);
        System.out.println("  (" + jp.getSignature().getDeclaringType().getSimpleName() + ".java:" + 1 + ")");
        printArgStr(jp);
	}
	
	@AfterReturning(value ="execution(* com.example.demo.domain.*..*Service*.*(..))", returning = "returnValue")
    public void endServiceLog(JoinPoint jp, Object returnValue) {
        System.out.println(" 戻り値：" + returnValue);
        System.out.println("メソッド正常終了：" + jp.getSignature());
        System.out.println("");
    }
	
	@AfterThrowing(value = "execution(* com.example.demo.app.*..*Service*.*(..))", throwing="e")
	public void errorServiceLog(JoinPoint jp, Exception e) {
		System.out.println("メソッド異常終了：" + jp.getSignature());
		e.printStackTrace();
        System.out.println("");
	}
	
	//Daoクラスのログ
	
	@Before("execution(* com.example.demo.domain.*..*Dao*.*(..))")
	public void startDaoLog(JoinPoint jp) {
		System.out.println("");
		System.out.println("メソッド開始：" + jp.getSignature());
		System.out.print(" クラス名：" + PINK + jp.getSignature().getDeclaringType().getSimpleName() + END);
        System.out.print("  メソッド名：" + PINK + jp.getSignature().getName() + END);
        System.out.println("  (" + jp.getSignature().getDeclaringType().getSimpleName() + ".java:" + 1 + ")");
        printArgStr(jp);
	}
	
	@AfterReturning(value ="execution(* com.example.demo.domain.*..*Dao*.*(..))", returning = "returnValue")
    public void endDaoLog(JoinPoint jp, Object returnValue) {
        System.out.println(" 戻り値：" + returnValue);
        System.out.println("メソッド正常終了：" + jp.getSignature());
        System.out.println("");
    }
	
	@AfterThrowing(value = "execution(* com.example.demo.app.*..*Dao*.*(..))", throwing="e")
	public void errorDaoLog(JoinPoint jp, Exception e) {
		System.out.println("メソッド異常終了：" + jp.getSignature());
		e.printStackTrace();
        System.out.println("");
	}
	
	//privateメソッド
	
	private void printArgStr(JoinPoint jp) {
		
        String[] argNames = ((CodeSignature)jp.getSignature()).getParameterNames();
        Object[] argValues = jp.getArgs();
        if(argNames.length > 0) {
        	for(int i = 0; i < argNames.length; i++) {
            	System.out.println(" 引数：" + argNames[i] + " = "  + argValues[i]);
            }
        } else {
        	System.out.println(" 引数：なし");
        }
	}
}
