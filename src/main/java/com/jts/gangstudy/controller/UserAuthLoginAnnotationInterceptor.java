package com.jts.gangstudy.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jts.gangstudy.domain.User;;

/*
��HandlerInterceptor �������̽�
��HandlerInterceptorAdapter �߻�Ŭ����
	- public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler)
     	Controller ��û �� ����
	- public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler, ModelAndVeiw modelAndVeiw)
     	Controller ó���� ������ ȭ�鿡 ����ִ� ó�� ������ ����
	- afterCompletion() : ��� ó���� ���� �� ȣ��
 */

public class UserAuthLoginAnnotationInterceptor extends HandlerInterceptorAdapter {
	public UserAuthLoginAnnotationInterceptor() {
		System.out.println("### UserAuthLoginAnnotationInterceptor()������");
	}

	// preHandle() : ��Ʈ�ѷ����� ���� ����Ǵ� �޼���
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("### UserAuthLoginAnnotationInterceptor.preHandle()�޽��");
		// 1. handler ���� Ȯ��
		// �츮�� ���� �ִ� ���� Controller�� �ִ� �޼����̹Ƿ� HandlerMethod Ÿ������ üũ
		if (handler instanceof HandlerMethod == false) {
			// return true�̸� Controller�� �ִ� �޼��尡 �ƴϹǷ�, �״�� ��Ʈ�ѷ��� ����
			return true;
		}
		// 2.�� ��ȯ
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		// 3. @UserLoginCheck �޾ƿ���
		UserLoginCheck loginCheck = handlerMethod.getMethodAnnotation(UserLoginCheck.class);

		// 4. method�� @UserLoginCheck�� ���� ���, �� ������ �ʿ� ���� ��û
		if (loginCheck == null) {
			System.out.println("### AuthLoginAnnotationInterceptor.preHandle() @UserLoginCheck ���� ���");
			return true;
		}
		// 5. @UserLoginCheck�� �ִ� ����̹Ƿ�, ������ �ִ��� üũ
		System.out.println("### AuthLoginAnnotationInterceptor.preHandle() @UserLoginCheck �ִ� ���");
		
		// session ��ü�� ������
		HttpSession session = request.getSession();
		// loginó���� ����ϴ� ����� ������ ��� �ִ� ��ü�� ������

		session.setMaxInactiveInterval(31536000); // 1day ms
		
		User sUserId = (User) session.getAttribute("sUserId");
		if (sUserId == null) {
			// �α����� �ȵǾ� �ִ� ���������� �α��� ������ �ٽ� ��������(redirect)
			response.sendRedirect(request.getContextPath()+"/signin");
			return false; // ���̻� ��Ʈ�ѷ� ��û���� ���� �ʵ��� false�� ��ȯ��
		}

		// preHandle�� return�� ��Ʈ�ѷ� ��û uri�� ���� �ǳ� �ȵǳĸ� �㰡�ϴ� �ǹ���
		// ���� true���ϸ� ��Ʈ�ѷ� uri�� ���� ��.
		return true;
	}

	// ��Ʈ�ѷ��� ����ǰ� ȭ���� �������� ������ ����Ǵ� �޼���
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}
}