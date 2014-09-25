package com.companyname.projectname;




/**
 * @author weinianjie
 * @date 2014年7月24日
 * @time 下午5:28:37
 */
public class Test {
	public static void main(String[] args) {
		new Out().new In().say();
	}
}

class Out {
	void show(){
		System.out.println("say out");
	}
	class In {
		public void say() {
			System.out.println("say in");
		}
	}
}
