package com.sensebling.system.init;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.sensebling.common.util.DebugOut;
/**
 * spring初始化完成后执行的操作
 * @author  
 *
 */
public class InstantiationTracingBeanPostProcessor implements
		ApplicationListener<ContextRefreshedEvent> {
	protected DebugOut log=new DebugOut(this.getClass());
	/**
	 * spring容器初始化完成后的执行操作
	 */
	public void onApplicationEvent(ContextRefreshedEvent event) {
		/*
		 * 在web 项目中（spring mvc），系统会存在两个容器，一个是root application context ,
		 * 另一个就是我们自己的 projectName-servlet  context（作为root application context的子容器）。
		 * 这种情况下，就会造成onApplicationEvent方法被执行两次。
		 * 为了避免上面提到的问题，我们可以只在root application context初始化完成后调用逻辑代码，
		 * 其他的容器的初始化完成，则不做任何处理 
		 */
		//root application context 没有parent，他就是老大.
		if(event.getApplicationContext().getParent() == null){

	    }
	}

}
