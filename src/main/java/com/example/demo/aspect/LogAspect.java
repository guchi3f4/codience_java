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
	
	//Controllerクラスのログ
	
	@Before("execution(* com.example.demo.app.*..*Controller*.*(..))")
	public void startControllerLog(JoinPoint jp) {
		System.out.println("メソッド開始：" + jp.getSignature());
		System.out.print(" クラス名：" + jp.getSignature().getDeclaringType().getSimpleName());
        System.out.print("  メソッド名：" + jp.getSignature().getName());
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
		System.out.print(" クラス名：" + jp.getSignature().getDeclaringType().getSimpleName());
        System.out.print("  メソッド名：" + jp.getSignature().getName());
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
		System.out.print(" クラス名：" + jp.getSignature().getDeclaringType().getSimpleName());
        System.out.print("  メソッド名：" + jp.getSignature().getName());
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
